package com.gulimall.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author aqiang9  2020-09-04
 */
@Controller
public class HelloController {

    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        return "hello" ;
    }


    String ssoLoginUrl = "http://sso.com:8080/login.html" ;

    @GetMapping("/employees")
    public String employees(ModelMap modelMap , HttpSession session){

        if (session.getAttribute("loginUser" ) == null ){
            return "redirect:"+ ssoLoginUrl ;
        }


        HashMap<Integer, String> emps = new HashMap<>();

        emps.put(1,"zs") ;
        emps.put(2,"zl") ;
        emps.put(3,"ww") ;
        emps.put(4,"ls") ;

        modelMap.put("emps" , emps ) ;
        return "list" ;
    }
}
