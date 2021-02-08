package com.emilie.ClimbingWebApp.controller;

import com.emilie.ClimbingWebApp.domain.Pitch;
import com.emilie.ClimbingWebApp.domain.Route;
import com.emilie.ClimbingWebApp.domain.User;
import com.emilie.ClimbingWebApp.repositories.PitchRepository;
import com.emilie.ClimbingWebApp.repositories.RouteRepository;
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
public class PitchController {


    @Autowired
    RouteRepository routeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PitchRepository pitchRepository;


    @GetMapping(path="/pitch")
    public String getPitch() {
        return "pitch";
    }


    @PostMapping("/pitch")
    public String newPitch(@ModelAttribute("pitch") Pitch pitch,
                           HttpSession httpSession,
                           Model model) {
        if (httpSession.getAttribute( "email" ) != null) {
            if (pitch != null) {
                User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
                pitch.setUser( user );//lie l'user à la longueur
                this.pitchRepository.save( pitch );

                return "redirect:/pitchDetails/" + pitch.getId(); //redirection qui fonctionne mais sans l'id à transmettre (il faut changer le path de la méthode GetMapping ci-dessus)
            }
        }
        return "login";
    }


    @GetMapping(path="/pitchDetails/{id}")
    public String getPitchDetails(@PathVariable("id") Long id,
                                  HttpSession httpSession,
                                  Model model) {

        Optional<Pitch> pitch=this.pitchRepository.findById( id );
        model.addAttribute( "pitch", pitch.get() );
        model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
        model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );

        return "pitchDetails";
    }


    @GetMapping(path="/pitchList")
    public String getPitchList(@ModelAttribute("keyword") String keyword,
                               Model model,
                               HttpSession httpSession) {
        List<Pitch> pitchData=null;
        if (keyword != null) {
            pitchData=this.pitchRepository.searchPitch( keyword );
        } else {
            pitchData=this.pitchRepository.findAll();
        }

        Set<Route> routeList=new HashSet<>();
        for (Pitch p : pitchData) {
            if (!routeList.contains( p.getRoute() )) {
                routeList.add( p.getRoute() );
            }
        }

        model.addAttribute( "routeList", routeList );
        model.addAttribute( "pitchList", pitchData );
        model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
        model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );
        return "/pitchList";
    }


}



