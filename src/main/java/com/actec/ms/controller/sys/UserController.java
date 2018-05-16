package com.actec.ms.controller.sys;

import com.actec.ms.controller.AbstractController;
import com.actec.ms.service.sys.UserService;
import com.actec.ms.vo.RestResult;
import com.actec.ms.vo.sys.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by donglin.wang on 2018/4/14.
 */
@RestController
@RequestMapping("/user")
public class UserController extends AbstractController<User> {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    protected void setService(UserService userService) {
        super.setService(userService);
        this.userService = userService;
    }

    @PostMapping("/login")
    public RestResult<Map<String, Object>> handleLogin(@RequestParam(name="userName") String userName, @RequestParam(name = "password")  String password) {
        return userService.login(userName, password);
    }
}
