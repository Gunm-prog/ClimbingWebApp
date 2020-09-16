package com.emilie.ClimbingWebApp.controller;


import com.emilie.ClimbingWebApp.domain.SignUpForm;
import com.emilie.ClimbingWebApp.domain.User;
import com.emilie.ClimbingWebApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class SignUpController {

    @Autowired
    UserRepository userRepository;

    //method to get signup form
    @GetMapping(path="/signup")
    public String getSignUpForm(@ModelAttribute User user){

        return "signup-form";
    }

    //method to get signupform details
    @PostMapping("/signup-submit")
    public String submitSignUp(@ModelAttribute User user, Model model){

      /*  //creation d'un utilisateur:
        User newUser = new User(user.getName(), user.getEmail(), user.getPseudo(), user.getPassword());*/

        //sauvegarde de l'utilisateur dans la table User
        model.addAttribute("user", user );
        userRepository.save(user);

        return"signup-success";

    }

}
