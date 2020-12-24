package com.emilie.ClimbingWebApp.controller;


import at.favre.lib.crypto.bcrypt.BCrypt;
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

        String hashedPass = BCrypt.withDefaults().hashToString( 12, user.getPassword().toCharArray() );
        user.setPassword( hashedPass );
        user.setRole( "member");
        userRepository.save( user );

        model.addAttribute( "user", user );
        return "signup-success";
    }

    @GetMapping(path="/login")
    public String Login() {
        return "login";
    }

    @PostMapping(path="/login")
    public String showUserAccount(@ModelAttribute User user, Model model, HttpSession httpSession){
        //model.addAttribute( "user", user );
        Optional<User> dataFound = this.userRepository.findByEmail( user.getEmail() );
        if(dataFound.isPresent()){
            User userData = dataFound.get();

            BCrypt.Result result = BCrypt.verifyer().verify(user.getPassword().toCharArray(), userData.getPassword());


            if(result.verified){
                user.setId(userData.getId());
                user.setName( userData.getName() );
                user.setEmail( userData.getEmail() );
                user.setPseudo( userData.getPseudo() );
                //  model.addAttribute( "user", user );
                httpSession.setAttribute( "email", user.getEmail());
                httpSession.setAttribute( "currentUserId", userData.getId() );
                httpSession.setAttribute( "currentUserRole", userData.getRole() );
                return "userAccount";
            }
            return "login";
        }

        return "login";
    }

    @GetMapping(path="/logout")
    public String showUserAccountLogOut(@ModelAttribute User user, HttpSession httpSession){
        httpSession.invalidate();
        //httpSession.removeAttribute( "email" );
        return "login";
    }

}


