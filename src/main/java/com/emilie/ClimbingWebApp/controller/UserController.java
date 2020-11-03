package com.emilie.ClimbingWebApp.controller;


import com.emilie.ClimbingWebApp.domain.User;
import com.emilie.ClimbingWebApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
//@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/userAccount")
    public String getUserAccount(@PathVariable("id") Long id , Model model, HttpSession httpSession) {
        //model.addAttribute( "user", user );
       // String email=(String) httpSession.getAttribute( "email" );
        //String name=(String) httpSession.getAttribute( "name" );
        User user = (User) httpSession.getAttribute( "user" );

        if(user.getEmail() != null) {
         //   Optional<User> userConnected=this.userRepository.findByEmail( user.getEmail() );
          //  if (userConnected.isPresent()){
          //  User present=userConnected.get();//ajout if present
          //  if (present.getPassword().equals( user.getPassword() )) {
              //  this.userRepository.findByPassword( user.getPassword() );
              //  httpSession.getAttribute( "email", email);// non
              //  user.setName( name );
                model.addAttribute( "user", user );
             //   model.addAttribute( "email", user.getEmail() );
             //   model.addAttribute( "name", user.getName() );


                //TODO ajout de tous les attributs de l'utilisateur pour les afficher
                return "userAccount";
            }
          //  }
      //  }

       // return "redirect:/login";
        return "login";
    }
}
//todo rendre les utilisateurs uniques