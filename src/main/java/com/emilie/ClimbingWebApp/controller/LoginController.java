package com.emilie.ClimbingWebApp.controller;


import com.emilie.ClimbingWebApp.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    //to get login form page
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String getLoginForm(){

        //return html page name
        return "login";
    }
    //checking for login credentials
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(@ModelAttribute(name="user") User user, Model model){

        String name = user.getName();
        String password = user.getPassword();

        if("admin".equals(name) && "admin".equals(password)){
            //if username and password is correct, we are returning home page
            return  "home";
        }
        //of iser,aùe pr âsswprd is wrong
        model.addAttribute( "invalidCredentials", true );
        //returning again login page
        return "login";
    }

}
