package com.emilie.ClimbingWebApp.controller;


import com.emilie.ClimbingWebApp.domain.*;
import com.emilie.ClimbingWebApp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopoRepository topoRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private SpotRepository spotRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private PitchRepository pitchRepository;
    @Autowired
    private TopoBookingRepository topoBookingRepository;


    @GetMapping(path="/userAccount/{id}")
    public String getUserAccount(Model model,
                                 HttpSession httpSession) {
        if (httpSession.getAttribute( "email" ) != null) {
            Optional<User> user=this.userRepository.findById( (Long) httpSession.getAttribute( "currentUserId" ) );
            List<Topo> topos=this.topoRepository.findByUser( user.get() );
            for (Topo topo : topos) {
                Set<TopoBooking> topoBookings=this.topoBookingRepository.findByTopo( topo );
                topo.setBooking( topoBookings );
            }

            Set<TopoBooking> currentUserRsv=this.topoBookingRepository.findByUser( user.get() );

            List<Spot> spots=this.spotRepository.findByUser( user.get() );
            List<Area> areas=this.areaRepository.findByUser( user.get() );
            List<Route> route=this.routeRepository.findByUser(user.get());
            List<Pitch> pitch=this.pitchRepository.findByUser(user.get());
            model.addAttribute( "user", user.get() );
            model.addAttribute( "topos", topos );
            model.addAttribute( "currentUserRsv", currentUserRsv );
            model.addAttribute( "spots", spots );
            model.addAttribute( "areas", areas );
            model.addAttribute("route", route);
            model.addAttribute( "pitch", pitch );
            model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
            model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );


            return "userAccount";
        } else {
            return "login";
        }

    }

}