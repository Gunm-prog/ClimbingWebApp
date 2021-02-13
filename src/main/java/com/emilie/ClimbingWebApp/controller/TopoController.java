package com.emilie.ClimbingWebApp.controller;


import com.emilie.ClimbingWebApp.domain.Topo;
import com.emilie.ClimbingWebApp.domain.TopoBooking;
import com.emilie.ClimbingWebApp.domain.User;
import com.emilie.ClimbingWebApp.repositories.SpotRepository;
import com.emilie.ClimbingWebApp.repositories.TopoBookingRepository;
import com.emilie.ClimbingWebApp.repositories.TopoRepository;
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
public class TopoController {

    @Autowired
    TopoRepository topoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SpotRepository spotRepository;
    @Autowired
    TopoBookingRepository topoBookingRepository;


    @GetMapping(path="/topo")
    public String getTopo() {

        return "topo";
    }


    @PostMapping("/topo")
    public String saveNewTopo(@ModelAttribute("topo") Topo topo,
                              HttpSession httpSession) {
        if (httpSession.getAttribute( "email" ) != null) {
            if (topo != null) {
                User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
                topo.setUser( user );//lie l'user au topo
                this.topoRepository.save( topo );
                return "redirect:/topoDetails/" + topo.getId();
            }
        }
        return "login";
    }


    @GetMapping(path="/topoDetails/{id}")
    public String getTopoDetails(@ModelAttribute("topoDetails") Topo topo,
                                 @PathVariable("id") Long id,
                                 HttpSession httpSession,
                                 Model model) {

        if (httpSession.getAttribute( "email" ) != null) {
            User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
            Optional<Topo> dataTopo=this.topoRepository.findById( id );
            if (dataTopo.isPresent()) {
                Topo theTopo=dataTopo.get();
                boolean isOwner=false;
                if (theTopo.getUser().getId() == user.getId()) {
                    isOwner=true;
                }
                Set<TopoBooking> topoBooking=this.topoBookingRepository.findByTopo( theTopo );
                boolean topoAvailabity=true;
                boolean userHasAlreadyLoaned=false;
                boolean bookingAccepted=false;
                for (TopoBooking value : topoBooking) {
                    if (value.getBookingStatus() != null && value.getBookingStatus()) {
                        topoAvailabity=false;
                        if (value.getUser().getId() == user.getId()) {
                            bookingAccepted=true;
                        }
                    }
                     if (value.getUser().getId() == user.getId()) {
                        userHasAlreadyLoaned=true;
                    }

                }


                model.addAttribute( "topo", theTopo );
                model.addAttribute( "user", user );
                model.addAttribute( "isOwner", isOwner );
                model.addAttribute( "topoAvailability", topoAvailabity );
                model.addAttribute( "userHasAlreadyLoaned", userHasAlreadyLoaned );
                model.addAttribute( "bookingAccepted", bookingAccepted );
                model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
                model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );
            }
            return "topoDetails";
        } else {
            return "login";
        }
    }


    //Todo n'oublies pas d'expliquer ce que font tes méthodes, ici renvoie une liste de Topo filtrée par un keyword, ou non.
    @GetMapping(path="/topoList")
    public String getTopoList(@ModelAttribute("keyword") String keyword,
                              Model model,
                              HttpSession httpSession) {
        List<Topo> topoData=null;
        if (keyword != null) {
            topoData=this.topoRepository.searchTopo( keyword );
        } else {
            topoData=this.topoRepository.findAll();
        }

        //traitement uniquement si utilisateur connecté
        if (httpSession.getAttribute( "email" ) != null) {
            //Pour chaque topo va récupérer que la reservation qui conserne l'utilisateur connecté si elle existe.
            for (Topo topo : topoData) {
                Set<TopoBooking> rsvList=new HashSet<>();
                for (TopoBooking rsv : topo.getBooking()) {
                    //  System.out.println(rsv);
                    if (rsv.getUser().getId().equals( (Long) httpSession.getAttribute( "currentUserId" ) )) {
                        //   System.out.println(rsv.getUser());
                        rsvList.add( rsv );
                    }
                }
                topo.setBooking( rsvList );
            }
            // System.out.println(rsvList);
        }

        System.out.println( topoData );
//        topoData.clear();
        model.addAttribute( "topoList", topoData );
        model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
        model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );
        // System.out.println( topoData );
        //on redirige ensuite vers la page qui doit afficher ce topo
        return "/topoList";
    }

}











