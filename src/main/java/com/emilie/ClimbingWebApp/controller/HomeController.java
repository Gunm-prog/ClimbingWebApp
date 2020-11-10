package com.emilie.ClimbingWebApp.controller;


import com.emilie.ClimbingWebApp.domain.User;
import com.emilie.ClimbingWebApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class HomeController{

    @Autowired
    UserRepository userRepository;
    @RequestMapping(path="/homeNotSignedIn")
    @GetMapping
    String index(@ModelAttribute User user) {

        return "homeNotSignedIn";
    }

    @PostMapping("/signup")
    public String getSignUp(@ModelAttribute User user, Model model){

        model.addAttribute( "user", user );
        userRepository.save(user);

        return "signup";
    }
}

/*import com.emilie.ClimbingWebApp.domain.User;
import com.emilie.ClimbingWebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/")
    public ModelAndView listUser(ModelAndView modelAndView) throws IOException{
        List<User> listUser = userService.listAll();
        modelAndView.addObject( "listUser", listUser);
        modelAndView.setViewName( "home ");

        return modelAndView;
    }

    @RequestMapping(value="/newUser",method=RequestMethod.GET)
    public ModelAndView newUser(ModelAndView modelAndView){
        User newUser = new User();
        modelAndView.addObject( "user", newUser );
        modelAndView.setViewName("ContactForm");

        return modelAndView;
    }

    @RequestMapping(value="/saveUser", method=RequestMethod.POST)
    public ModelAndView saveUser(@ModelAttribute User user){
        userService.save( user );

        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value="/editUser", method=RequestMethod.GET)
    public ModelAndView editUser(HttpServletRequest request){
        long id = Long.parseLong( request.getParameter( "id" ) );
        User user = userService.get(id);
        ModelAndView modelAndView = new ModelAndView("ContactForm");
        modelAndView.addObject( "user", user );

        return modelAndView;
    }
}
*/