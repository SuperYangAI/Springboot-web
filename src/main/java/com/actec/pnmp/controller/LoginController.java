package com.actec.pnmp.controller;

import com.actec.pnmp.entity.sys.User;
import com.actec.pnmp.service.UserService;
import com.actec.pnmp.utils.MD5Util;
import com.github.pagehelper.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by Yangchao on 2018-4-10.
 */
@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login() {
        return "login";
    }


    @RequestMapping(value = { "/login" }, method = RequestMethod.POST)
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,ModelMap model
    ) {
        try {

            List<User> users = userService.findAll();
            Page<User> userPage = userService.findPage(new User());
            Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated()){
                UsernamePasswordToken token = new UsernamePasswordToken(userName, MD5Util.encode(password));
                subject.login(token);
                Session session = subject.getSession();
                session.setAttribute("username", userName);
                System.out.println("password:"+password);
            }
            return redirect("index");
        } catch (AuthenticationException e) {
            model.put("message", e.getMessage());
        }
        return "login";
    }

    @RequestMapping(value = { "/index" }, method = RequestMethod.GET)
//    @RequiresPermissions(value = { "有登录权限" })
    public String index() {
        return "index";
    }



    @RequestMapping(value = { "my/logout" })
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return redirect("login");
    }

    protected String redirect(String path) {
        return "redirect:" + path;
    }
}
