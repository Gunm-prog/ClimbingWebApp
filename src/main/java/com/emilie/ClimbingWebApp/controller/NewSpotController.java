package com.emilie.ClimbingWebApp.controller;

import com.emilie.ClimbingWebApp.domain.*;
import com.emilie.ClimbingWebApp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
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
    //private Spot spot;




    //@RequestMapping(path="newspot/{id}")//(path="spotdetails/{id}")
    @GetMapping("spotDetails/{id}")
    public String spotDetails(@PathVariable("id") Long id, ModelMap model/*, HttpSession session*/) { //public String spotdetails...

        Optional<Spot> spot=spotRepository.findById( id );
        model.addAttribute( "spot", spot.get() );
        model.addAttribute( "secteur", spot.get().getSecteurs() );

        return "spotDetails";
    }

    @RequestMapping(path="/newspot")
    @GetMapping
    public String saveNewSpot(Model model) {
        return "newspot";
    }


    @PostMapping("/newspot")
    public String showSpotDetails(@ModelAttribute Spot spot, Model model, HttpSession httpSession) {

        String email = (String)httpSession.getAttribute( "email" );
        Optional<User> userConnected = userRepository.findByEmail( email );
        spot.setUser( userConnected.get() );  //liaison utilisateur connecté au spot
        model.addAttribute( "spot", spot );
        spotRepository.save( spot );

       // return "redirect:/newspot";
        return "spotDetails";
    }

    @PostMapping(path="/saveSecteur")
    public String saveSecteur(@ModelAttribute Secteur secteur, Model model){
        model.addAttribute( "secteur", secteur );
        secteurRepository.save(secteur);
        return "secteur";
    }

    @PostMapping(path="/saveVoie")
    public String saveVoie(@ModelAttribute Voie voie, Model model){
        model.addAttribute( "voie", voie );
        voieRepository.save(voie);
        return "voie";
    }

    @PostMapping(path="/saveLongueur")
    public String saveLongueur(@ModelAttribute Longueur longueur, Model model){
        model.addAttribute( "longueur", longueur );
        longueurRepository.save(longueur);
        return "longueur";
    }









   /* @RequestMapping(path="/updateSpot")
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
    }*/

   /* @RequestMapping("/deleteSpot")
    @GetMapping
    public String deleteSpot(Model model){
        model.addAttribute( "spot", spot );
        spotRepository.delete(spot);
        return "deleteSpot";
    }*/



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
