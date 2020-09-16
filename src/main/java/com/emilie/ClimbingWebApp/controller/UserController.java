package com.emilie.ClimbingWebApp.controller;


import com.emilie.ClimbingWebApp.domain.User;
import com.emilie.ClimbingWebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserService userService;




}

/*
    @RequestMapping("/")
    public ModelAndView user(){
        List<User> listUser = userService.listAll();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject( "listUser", listUser );
        return modelAndView;
    }
    @RequestMapping("/new")
    public String newUserForm(Map<String, Object> model){
        User user = new User();
        model.put("user", user);
        return "new_user";
    }
    @RequestMapping(value="/save", method= RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user){
        userService.save(user);
        return "redirect:/";
    }
    @RequestMapping("/edit")
    public ModelAndView editUserForm(@RequestParam long id){
        ModelAndView modelAndView = new ModelAndView("edit_user");
        User user = userService.get( id );
        modelAndView.addObject( "user", user );

        return modelAndView;
    }
    @RequestMapping("/delete")
    public String deleteUserForm(@RequestParam long id){
        userService.delete( id );
        return "redirect:/";
    }
    @RequestMapping("/search")
    public ModelAndView search(@RequestParam String keyword){
        List<User> result = userService.search( keyword );
        ModelAndView modelAndView = new ModelAndView("search");
        modelAndView.addObject( "result", result );
        return modelAndView;
    }

}
*/