package com.example.servletfiltertrain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginController {

    @RequestMapping("toLogin")
    public String gologin()
    {
        return "login";
    }

    @RequestMapping("main")
    public String main()
    {
        return "main";
    }

    @PostMapping("loginIn")
    public String loginIn(@RequestParam("userName") String userName, @RequestParam("pwd") String pwd, HttpSession session){

        //验证用户名和密码，输入正确，跳转到dashboard
        if(!StringUtils.isEmpty(userName) && "123".equals(pwd)){
            session.setAttribute("userName",userName);
            System.out.println("----" + userName);
            return "redirect:/main";

        }
        else  //输入错误，清空session，提示用户名密码错误
        {
            session.invalidate();
            return "login";
        }
    }
}
