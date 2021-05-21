package com.hy.springbootstudy.controller;

import com.hy.springbootstudy.service.StudentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @Autowired
    @Qualifier("studentServiceImpl")
    private StudentService studentService;

    @RequestMapping("login")
    public String login(String username, String pwd, Model model){
        UsernamePasswordToken upt = new UsernamePasswordToken(username,pwd);
        //得到当前登录的对象
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(upt);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            model.addAttribute("msg","此用户不存在");
            return "/login.jsp";
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            model.addAttribute("msg","密码错误");
        } catch (AuthenticationException e){
            e.printStackTrace();
            model.addAttribute("msg","认证失败");
            return "/login.jsp";
        }
        return "page";
    }

}
