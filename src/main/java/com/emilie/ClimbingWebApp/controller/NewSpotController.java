package com.emilie.ClimbingWebApp.controller;

import com.emilie.ClimbingWebApp.domain.Longueur;
import com.emilie.ClimbingWebApp.domain.Secteur;
import com.emilie.ClimbingWebApp.domain.Spot;
import com.emilie.ClimbingWebApp.domain.Voie;
import com.emilie.ClimbingWebApp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@SessionAttributes("name, description, userMemberInSessionId")
public class NewSpotController {

    @Autowired
    SpotRepository spotRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SecteurRepository secteurRepository;
    @Autowired
    VoieRepository voieRepository;
    @Autowired
    LongueurRepository longueurRepository;
    private Spot spot;


    @RequestMapping(path="newspot/{id}")//(path="spotdetails/{id}")
    @GetMapping
    public String newspot(@PathVariable("id") Long id, Model model/*, HttpSession session*/) { //public String spotdetails...

        Optional<Spot> spot=spotRepository.findById( id );
        model.addAttribute( "spot", spot.get() );
        Optional<Secteur> secteur = secteurRepository.findById( id );
        model.addAttribute( "secteur", secteur.get() );
        Optional<Voie> voie =voieRepository.findById( id );
        model.addAttribute( "voie", voie.get() );
        Optional<Longueur> longueur=longueurRepository.findById( id );
        model.addAttribute( "longueur", longueur.get());
        return "newspot";
    }

    @RequestMapping(path="/newspot")
    @GetMapping
    public String saveNewSpot(Model model) {
        return "newspot";
    }

    @PostMapping("/newspot")
    public String saveSpot(@ModelAttribute Spot spot, Model model) {

        model.addAttribute( "spot", spot );
        spotRepository.save( spot );

        return "newspot";
    }

    @RequestMapping(path="/updateSpot")
    @GetMapping
    public String updateSpot(Model model) {
        model.addAttribute( "spot", spot );
        spotRepository.update( spot );
        return "updateSpot";
    }

    @PostMapping("/updateSpot")
    public String updateSpot(@ModelAttribute Spot spot, Model model) {
        model.addAttribute( "spot", spot );
        spotRepository.update( spot );
        return "updateSpot";
    }

    @RequestMapping("/deleteSpot")
    @GetMapping
    public String deleteSpot(Model model){
        model.addAttribute( "spot", spot );
        spotRepository.delete(spot);
        return "deleteSpot";
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
