package com.emilie.ClimbingWebApp.controller;

import com.emilie.ClimbingWebApp.domain.*;
import com.emilie.ClimbingWebApp.repositories.*;
/*import org.apache.catalina.User;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
class HomeController {

    @Autowired
    UserRepository userRepository;


    @GetMapping(path="/")
    String index(HttpSession httpSession, Model model) {
        if (httpSession.getAttribute( "email" ) != null) {
            model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
            model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );
        }
        return "index";
    }


    @PostMapping("/signup")
    public String getSignUp(@ModelAttribute("user") User user, Model model) {

        model.addAttribute( "user", user );
        this.userRepository.save( user );

        return "signup";
    }

}





