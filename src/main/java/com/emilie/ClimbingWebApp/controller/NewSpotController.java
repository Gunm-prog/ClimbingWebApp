package com.emilie.ClimbingWebApp.controller;

import com.emilie.ClimbingWebApp.domain.Spot;
import com.emilie.ClimbingWebApp.repositories.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller

public class NewSpotController {

    @Autowired
    SpotRepository spotRepository;

    @RequestMapping(path="spotdetails/{id}")
    @GetMapping
    public String spotdetails(@PathVariable ("id") Long id, Model model){
         Optional<Spot> spot= spotRepository.findById( id );
        model.addAttribute( "spot", spot.get() );
        return "newspot";
    }

    @PostMapping("/spotdetails")
    public String spotdetails(@ModelAttribute Spot spot, Model model){
        model.addAttribute("spot", spot);
        spotRepository.save(spot);
        return"newspot";
    }

    @RequestMapping(path="/newspot")
    @GetMapping
    public String saveNewSpot(  Model model){
        return "newspot";
    }

    @PostMapping("/newspot")
    public String saveSpot(@ModelAttribute Spot spot, Model model){

        model.addAttribute( "spot", spot );
        spotRepository.save( spot );

        return "newspot";
    }



   /* public ModelAndView newspot(){
        ModelAndView modelAndView= new ModelAndView("newSpotAdd");
        modelAndView.addObject( "spot", new Spot() );
        return modelAndView;
    }

    @RequestMapping("/saveSpot")
    public ModelAndView save(@ModelAttribute("spot") Spot spot){
    ModelAndView modelAndView= new ModelAndView("spotList");
    spotRepository.save(spot);
        List<Spot> list= SpotService.get();
        modelAndView.addObject( "list", list);
        return modelAndView;
    }

    @RequestMapping("/newspot/{id}")
    public ModelAndView get(@PathVariable("id") Long id){
        ModelAndView modelAndView= new ModelAndView("spotAdd");
        Spot spot= spotService.get(id);
        if(spot == null){
            throw new RuntimeException("Spot not found" + id);
        }
        modelAndView.addObject( "spot", spot );
        return modelAndView;
    }

*/


}
