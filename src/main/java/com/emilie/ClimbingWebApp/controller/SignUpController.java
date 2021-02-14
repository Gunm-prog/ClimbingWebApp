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


    @GetMapping(path="/signup")
    public String getSignUpForm(HttpSession httpSession,
                                Model model) {

        if (httpSession != null) {
            model.addAttribute( "message", httpSession.getAttribute( "message" ) );
            httpSession.removeAttribute( "message" );
        }
        return "signup-form";
    }


    @PostMapping("/signup-submit")
    public String submitSignUp(@ModelAttribute("user") User user,
                               HttpSession httpSession,
                               Model model) {

        String hashedPass=BCrypt.withDefaults().hashToString( 12, user.getPassword().toCharArray() );
        user.setPassword( hashedPass );
        user.setRole( "member" );
        try {
            userRepository.save( user );
        } catch (Exception exception) {
            httpSession.setAttribute( "message", "This email already exists" );
            return "redirect:/signup";
        }

        model.addAttribute( "user", user );
        return "signup-success";
    }

    @GetMapping(path="/login")
    public String Login(HttpSession httpSession, Model model) {
        if (httpSession.getAttribute( "email" ) == null) {
            model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
            model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );

            if (httpSession.getAttribute( "error" ) != "") {
                model.addAttribute( "error", httpSession.getAttribute( "error" ) );
            } else {
                model.addAttribute( "error", "" );
            }
            return "login";
        }
        return "redirect:/";
    }


    @PostMapping(path="/login")
    public String showUserAccount(@ModelAttribute("user") User user,
                                  Model model,
                                  HttpSession httpSession) {
        //model.addAttribute( "user", user );
        if (httpSession.getAttribute( "email" ) == null) {
            Optional<User> dataFound=this.userRepository.findByEmail( user.getEmail() );
            if (dataFound.isPresent()) {
                User userData=dataFound.get();

                BCrypt.Result result=BCrypt.verifyer().verify( user.getPassword().toCharArray(), userData.getPassword() );

                if (result.verified) {
                    httpSession.setAttribute( "email", userData.getEmail() );
                    httpSession.setAttribute( "pseudo", userData.getPseudo() );
                    httpSession.setAttribute( "currentUserId", userData.getId() );
                    httpSession.setAttribute( "currentUserRole", userData.getRole() );
                    return "redirect:/userAccount/" + userData.getId();
                } else {
                    httpSession.setAttribute( "error", "bad password." );
                    return "redirect:/login";
                }
            } else {
                httpSession.setAttribute( "error", "email non reconnu." );
                model.addAttribute( "error", "email non reconnu." );
                return "redirect:/login";
            }
        }
        return "redirect:/";
    }


    @GetMapping(path="/logout")
    public String showUserAccountLogOut(@ModelAttribute User user, HttpSession httpSession) {

        httpSession.invalidate();
        return "index";
    }

}


