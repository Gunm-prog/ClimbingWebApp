package com.emilie.ClimbingWebApp.controller;

import com.emilie.ClimbingWebApp.domain.*;
import com.emilie.ClimbingWebApp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


@Controller
public class RouteController {


    @Autowired
    RouteRepository routeRepository;
    @Autowired
    SpotRepository spotRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AreaRepository areaRepository;
    @Autowired
    PitchRepository pitchRepository;


    @GetMapping(path="/route")
    public String getNewRoute() {

        return "route";
    }


    @PostMapping("/route")
    public String newRoute(@ModelAttribute("route") Route route,
                           HttpSession httpSession) {

        if (httpSession.getAttribute( "email" ) != null) {
            if (route != null) {
                User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
                route.setUser( user );
                this.routeRepository.save( route );
                return "redirect:/routeDetails/" + route.getId();
            }

        }
        return "login";
    }


    @GetMapping(path="/routeDetails/{id}")
    public String getRouteDetails(@PathVariable("id") Long id,
                                  HttpSession httpSession,
                                  Model model) {

        Optional<Route> routeData=this.routeRepository.findById( id );
        Route route=new Route();
        if (routeData.isPresent()) {
            route=routeData.get();
        }
        List<Pitch> pitches=this.pitchRepository.findByRoute( route );
        model.addAttribute( "route", route );
        model.addAttribute( "pitch", pitches );
        model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
        model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );

        return "routeDetails";
    }


    @GetMapping(path="/{id}/add/pitch")
    public String addNewPitch(@ModelAttribute("pitch") Pitch pitch,
                              @PathVariable("id") Long id,
                              HttpSession httpSession,
                              Model model) {
        if (httpSession.getAttribute( "email" ) != null) {
            model.addAttribute( "pitch", pitch );
            model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
            model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );
            return "pitch";

        } else {
            return "redirect:/login";
        }
    }


    @PostMapping("/{id}/add/pitch")
    public String saveNewPitch(@ModelAttribute("pitch") Pitch pitch,
                               @PathVariable("id") Long id,
                               HttpSession httpSession) {

        if (httpSession.getAttribute( "email" ) != null) {
            Pitch newPitch=new Pitch();
            newPitch.setDistance( pitch.getDistance() );
            newPitch.setPoints( pitch.getPoints() );
            newPitch.setQuotation( pitch.getQuotation() );
            Optional<Route> routeData=this.routeRepository.findById( id );
            System.out.println( routeData );
            newPitch.setRoute( routeData.get() );
            User user=this.userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
            newPitch.setUser( user );
            pitch=this.pitchRepository.save( newPitch );
            return "redirect:/pitchDetails/" + pitch.getId();
        } else {
            return "redirect:/login";
        }
    }


    @GetMapping(path="/routeList")
    public String getRouteList(@ModelAttribute("keyword") String keyword,
                               Model model,
                               HttpSession httpSession) {
        List<Route> routeData=null;
        if (keyword != null) {
            routeData=this.routeRepository.searchRoute( keyword );
        } else {
            routeData=this.routeRepository.findAll();
        }

        model.addAttribute( "routeList", routeData );
        model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
        model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );
        return "routeList";
    }
}



