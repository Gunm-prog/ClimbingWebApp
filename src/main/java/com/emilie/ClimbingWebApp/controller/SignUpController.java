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
        userRepository.save( user );

        return "signup-success";
    }

    @GetMapping(path="/login")
    public String Login() {
        return "login";
    }

    @PostMapping(path="/login")
    public String showUserAccount(@ModelAttribute User user, Model model, HttpSession httpSession) {

        
      model.addAttribute( "user", user );
        Optional<User> userFound = this.userRepository.findByEmail( user.getEmail() );
        if(userFound.isPresent()){
            User present = userFound.get();
            if (present.getPassword().equals(user.getPassword())){
                httpSession.setAttribute( "email", user.getEmail() );
                httpSession.setAttribute( "name", user.getName() );
                return "userAccount";
            }
            return "login";
        }

        return "login";
   }

    }


    /*@GetMapping(path="/homeNotSignedIn")
    public String homeNotSignedIn(){
        return "login";
    }
    @PostMapping(path="homeNotSignedIn")
    public String showLogin(@ModelAttribute User user, Model model){
        model.addAttribute( "user", user );
        userRepository.save(user);
        return "login";
    }*/

   /* @GetMapping("/logout")
    public String logOutPage(HttpServletRequest, HttpServletResponse response) {
        Authentification auth=SecurityContextHolder.getContext().getAuthentification();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout( request, response, auth );
        }
        return "redirect:/login?lougout";
    }
*/
