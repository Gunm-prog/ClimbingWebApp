package com.emilie.ClimbingWebApp.controller;


import com.emilie.ClimbingWebApp.domain.User;
import com.emilie.ClimbingWebApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class SignUpController {

    @Autowired
    UserRepository userRepository;


    //method to get signup form
    @GetMapping(path="/signup")
    public String getSignUpForm(@ModelAttribute User user) {

        return "signup-form";
    }

    //method to get signupform details
    @PostMapping("/signup-submit")
    public String submitSignUp(@ModelAttribute User user, Model model) {

      /*  //creation d'un utilisateur:
        User newUser = new User(user.getName(), user.getEmail(), user.getPseudo(), user.getPassword());*/

        //sauvegarde de l'utilisateur dans la table User
        model.addAttribute( "user", user );
        user.setRole( "member");
        userRepository.save( user );

        return "signup-success";
    }

    @GetMapping(path="/login")
    public String Login() {
        return "login";
    }

    @PostMapping(path="/login")
    public String showUserAccount(@ModelAttribute User user, Model model, HttpSession httpSession) {
      //model.addAttribute( "user", user );
        Optional<User> dataFound = this.userRepository.findByEmail( user.getEmail() );
        if(dataFound.isPresent()){
            User present = dataFound.get();
            if (present.getPassword().equals(user.getPassword())){
                user.setId(present.getId());
                user.setName( present.getName() );
                user.setEmail( present.getEmail() );
                user.setPseudo( present.getPseudo() );
              //  model.addAttribute( "user", user );
                httpSession.setAttribute( "email", user.getEmail());
                httpSession.setAttribute( "currentUserId", present.getId() );
                return "userAccount";
            }
            return "login";
        }

        return "login";
   }

}


