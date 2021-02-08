package com.emilie.ClimbingWebApp.controller;

import com.emilie.ClimbingWebApp.domain.Area;
import com.emilie.ClimbingWebApp.domain.Route;
import com.emilie.ClimbingWebApp.domain.Spot;
import com.emilie.ClimbingWebApp.domain.User;
import com.emilie.ClimbingWebApp.repositories.AreaRepository;
import com.emilie.ClimbingWebApp.repositories.RouteRepository;
import com.emilie.ClimbingWebApp.repositories.SpotRepository;
import com.emilie.ClimbingWebApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
public class AreaController {


    @Autowired
    SpotRepository spotRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AreaRepository areaRepository;
    @Autowired
    RouteRepository routeRepository;


    @GetMapping(path="/area")
    public String getArea() {
        return "area";
    }


    @PostMapping("/area")
    public String newArea(@ModelAttribute("area") Area area,
                          HttpSession httpSession) {
        if (httpSession.getAttribute( "email" ) != null) {
            if (area != null) {

                User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
                area.setUser( user );
                this.areaRepository.save( area );

                return "redirect:/areaDetails/" + area.getId();
            } else {
                return "area";
            }

        }
        return "login";
    }


    @GetMapping(path="/areaDetails/{id}")
    public String getAreaDetails(@PathVariable("id") Long id,
                                 HttpSession httpSession,
                                 Model model) {

        Optional<Area> areaData=this.areaRepository.findById( id );
        Area area=new Area();
        if (areaData.isPresent()) {
            area=areaData.get();
        }

        model.addAttribute( "area", area );
        model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
        model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );

        return "areaDetails";
    }


    @GetMapping(path="/{id}/add/route")
    public String addNewRoute(@ModelAttribute("route") Route route,
                              @PathVariable("id") Long id,
                              HttpSession httpSession,
                              Model model) {

        if (httpSession.getAttribute( "email" ) != null) {
            model.addAttribute( "route", route );
            model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
            model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );

            return "route";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/{id}/add/route")
    public String saveNewVoie(@ModelAttribute("route") Route route,
                              @PathVariable("id") Long id,
                              HttpSession httpSession) {

        if (httpSession.getAttribute( "email" ) != null) {

            Route newRoute=new Route();
            newRoute.setName( route.getName() );
            newRoute.setHeight( route.getHeight() );
            newRoute.setNumber( route.getNumber() );
            Optional<Area> area1=this.areaRepository.findById( id );
            newRoute.setArea( area1.get() );
            User user=this.userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
            newRoute.setUser( user );
            route=this.routeRepository.save( newRoute );

            return "redirect:/routeDetails/" + route.getId();

        } else {
            return "redirect:/login";
        }
    }


    @GetMapping(path="/areaList")
    public String getAreaList(@ModelAttribute("keyword") String keyword,
                              Model model,
                              HttpSession httpSession) {
        List<Area> areaData=null;
        if (keyword != null) {
            areaData=this.areaRepository.searchArea( keyword );
        } else {
            areaData=this.areaRepository.findAll();
        }

        Set<Spot> spotList=new HashSet<>();
        for (Area a : areaData) {
            if (!spotList.contains( a.getSpot() )) {
                spotList.add( a.getSpot() );
            }
        }

        model.addAttribute( "spotList", spotList );
        model.addAttribute( "areaList", areaData );
        model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
        model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );

        return "areaList";
    }

}



