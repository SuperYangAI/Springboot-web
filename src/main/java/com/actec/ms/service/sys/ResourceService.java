package com.actec.ms.service.sys;

import com.actec.ms.dao.sys.ResourceDao;
import com.actec.ms.service.AbstractService;
import com.actec.ms.vo.RestResult;
import com.actec.ms.vo.sys.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResourceService extends AbstractService<Resource> {

    private static Logger logger = LoggerFactory.getLogger(ResourceService.class);

    private ResourceDao dao;

    @Autowired
    protected void setDao(ResourceDao dao) {
        this.dao = dao;
        super.setDao(dao);
    }

    public RestResult<List<Resource>> list(int userId) {
        RestResult<List<Resource>> restResult;
        try {
            List<Resource> resources =  dao.list(userId);
            restResult = RestResult.buildSuccessResult(resources);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            restResult = RestResult.buildErrorResult(RestResult.Status.INTERNAL_SERVER_ERROR);
        }
        return restResult;
    }

    @Transactional
    public RestResult auth(int userId, int resourceId, boolean value) {
        RestResult<List<Resource>> restResult;
        try {
            if(value) {
                dao.deleteAuth(userId, resourceId);
                dao.addAuth(userId, resourceId);
            } else {
                dao.deleteAuth(userId, resourceId);
            }
            restResult = RestResult.buildSuccessResult();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            restResult = RestResult.buildErrorResult(RestResult.Status.INTERNAL_SERVER_ERROR);
        }
        return restResult;
    }

    public RestResult<Boolean> hasPermission(int userId, String url, String method) {
        RestResult<Boolean> restResult;
        Integer resourceId;
        try {
            resourceId = dao.findIdByUrl(url, method);
            if(resourceId == null || resourceId < 1)
                restResult = RestResult.buildSuccessResult(Boolean.FALSE);
            else
                restResult = RestResult.buildSuccessResult(Boolean.TRUE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            restResult = RestResult.buildErrorResult(RestResult.Status.INTERNAL_SERVER_ERROR);
        }
        return restResult;
    }
}
