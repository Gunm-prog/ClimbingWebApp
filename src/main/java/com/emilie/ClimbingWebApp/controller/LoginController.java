/*package com.emilie.ClimbingWebApp.controller;


import com.emilie.ClimbingWebApp.domain.Login;
import com.emilie.ClimbingWebApp.domain.User;
import com.emilie.ClimbingWebApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
public class LoginController {
    @Autowired
    UserRepository userRepository;

    private void addUserInSession(User user, HttpSession httpSession){
        httpSession.setAttribute( "userInSessionId", user.getId() );
        httpSession.setAttribute("userInSessionPseudo", user.getPseudo());
        httpSession.setAttribute( "userInSessionEmail", user.getEmail() );
    }

    private static final Logger logger = logManager.getLogger(LoginController.class){

        @GetMapping("/doLogin")
        public String showLoginForm(Model model){
            model.addAttribute("loginCommand", new Login ());
            return "login";
        }
        @PostMapping("/loginProcess")
        public String doLogin(@ModelAttribute("loginCommand")Login login, HttpSession httpSession, Model model) throws UserBlockedException{
*/








/*            logger.debug(Login.toString());
            //test session
        if(Login != null){
            User userInDB = userManager.findUserByProperty("email", login.getEmail());
            logger.debug(userInDB.toString());
            boolean checkPassword = false;

        if (userInDB == null){
            httpSession.invalidate();
            model.addAttribute("errorMessage", "Email or Password does not match");
            return "login";
        }
        checkPassword ) passwordManager.matches(login.getPassword(), userInDB.getPassword());
        logger.debug(checkPassword);
        if(checkPassword == true){
            User loggedInUser = userInDB;
            if(loggedInUser.getRole().equals( Role.ADMIN.getParam())) || loggedInUser.getRole().equals(Role.USERMEMBER.getParam()) || loggedInUser.getRole().equals(Role.USER.getParam()){
                addUserInSession( loggedInUser, httpSession );
                return "redirect:/showHomeNotSignedIn";
            }else{

            }

        }
        }
        }
)
    }
/*
    @GetMapping("userDetails/{id}")
    public String userDetails(@PathVariable("id") Long id, ModelMap model) {
        Optional<User> user=userRepository.findById( id );
        model.addAttribute( "user", user.get() );

        return "login";
    }

    @RequestMapping(path="/login")
    @GetMapping
    public String saveUser(Model model){
        return "login";
    }

    @PostMapping("/login")
    public String saveUser(@ModelAttribute User user, Model model){
        model.addAttribute( "User", user );
        userRepository.save(user);

        return "login";
    }


/*
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
    }*/


/*
}
*/