package com.actec.ms.service.sys;

import com.actec.ms.dao.sys.ResourceDao;
import com.actec.ms.dao.sys.UserDao;
import com.actec.ms.service.AbstractService;
import com.actec.ms.utils.JWTUtil;
import com.actec.ms.vo.RestResult;
import com.actec.ms.vo.sys.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends AbstractService<User> {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final long ONE_DAY_MILLS = 24 * 60 * 60 * 1000;

    private UserDao userDao;

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    protected void setDao(UserDao dao) {
        this.userDao = dao;
        super.setDao(dao);
    }

    public RestResult<Map<String, Object>> login(String userName, String password) {
        RestResult<Map<String, Object>> restResult;
        Map<String, Object> dataResult = new HashMap<>();
        User user = userDao.login(userName, password);
        if(user == null) {
            restResult = RestResult.buildErrorResult(RestResult.Status.NOT_FOUND);
        } else {
            Map<String, Object> claims = new HashMap<>();
            List<String> licensedNames;
            claims.put("userId", user.getId());
            try {
                String claimsStr = JWTUtil.createJWT("jwt", claims, ONE_DAY_MILLS);
                licensedNames = getLicensedNames(user.getId());
                dataResult.put("token", claimsStr);
                dataResult.put("licensed", licensedNames);
                dataResult.put("userId", user.getId());
                dataResult.put("userName", user.getName());
                restResult = RestResult.buildSuccessResult(dataResult);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                restResult = RestResult.buildErrorResult(RestResult.Status.INTERNAL_SERVER_ERROR);
            }
        }
        return restResult;
    }

    private List<String> getLicensedNames(int userId) {
        if(User.isAdmin(userId))
            return resourceDao.listAllName(userId);
        else
            return resourceDao.listName(userId);
    }

}
