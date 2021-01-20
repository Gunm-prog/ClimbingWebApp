package com.emilie.ClimbingWebApp.controller;


import com.emilie.ClimbingWebApp.domain.ReservationTopo;
import com.emilie.ClimbingWebApp.domain.Topo;
import com.emilie.ClimbingWebApp.domain.User;
import com.emilie.ClimbingWebApp.repositories.ReservationTopoRepository;
import com.emilie.ClimbingWebApp.repositories.SpotRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * @author Emilie Balsen
 */
@Controller
public class TopoController {

    @Autowired
    TopoRepository topoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SpotRepository spotRepository;
    @Autowired
    ReservationTopoRepository reservationTopoRepository;


    /**
     *
     * @return topo form to add a new topo
     */
    @GetMapping(path="/topo") //TODO pas necessaire? check par rapport au @PostMapping en bas !!!
    public String getTopo() {
        return "topo";
    }

    /**
     *
     * @param topo
     * @param httpSession
     * @return
     */
    @PostMapping("/topo")
    public String saveNewTopo(@ModelAttribute("topo") Topo topo, HttpSession httpSession) {
        if (httpSession.getAttribute( "email" ) != null) {
            // si user existe en session, il est connecté, ok!
            if (topo != null) { //si spot existe, il vient du formulaire, on le persiste en bdd.
                //recup du user présent dans la session

                User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
                topo.setUser( user );//lie l'user au topo
                System.out.println( topo );
                this.topoRepository.save( topo );
                return "redirect:/topoDetails/" + topo.getId(); //redirection qui fonctionne mais sans l'id à transmettre (il faut changer le path de la méthode GetMapping ci-dessus)
            } else {//si pas d'informations venant du formulaire newspot a traiter, direction formulaire de newspot
                return "topo"; //TODO ??????????????????????
            }

        }
        return "login";//si pas connecté, redirection page de login
    }


    /**
     * This method picks up a specific topo (thanks to its id) with its details
     * also showing its status (available to be borrowed or not)
     *
     * @param id
     * @param httpSession
     * @param model
     * @return topo details of one in particular (thanks to its id)
     * @return login page if user is not connected or if connexion has been lost
     */
    @GetMapping(path="/topoDetails/{id}")
    public String getTopoDetails(@PathVariable("id") Long id, HttpSession httpSession, Model model) {
        //grace a l'id dans le path, en recupere en bdd le topo par son id
        if (httpSession.getAttribute( "email" ) != null) {
            User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
            System.out.println( user );
            Optional<Topo> dataTopo=this.topoRepository.findById( id );
            if (dataTopo.isPresent()) {
                Topo theTopo=dataTopo.get();
                boolean isOwner=false;
                if (theTopo.getUser().getId() == user.getId()) { // si le proprietaire du topo == l'utilisateur connecté
                    isOwner=true;
                }
                Set<ReservationTopo> reservationTopo=this.reservationTopoRepository.findByTopo( theTopo );
                boolean topoAvailabity=true;
                boolean userHasAlreadyLoaned=false;
                boolean reservationAccepted=false;
                for (ReservationTopo value : reservationTopo) {
                    if (value.getReservationStatus() != null && value.getReservationStatus()) {
                        topoAvailabity=false;
                        if (value.getUser().getId() == user.getId()) {
                            reservationAccepted=true;
                        }
                    }
                    if (value.getUser().getId() == user.getId()) { //si l'utilisateur connecté a déjà fait une demande de réservation
                        userHasAlreadyLoaned=true;
                    }

                }

                System.out.println( reservationAccepted );
                System.out.println( theTopo );
                System.out.println( isOwner );
                System.out.println( userHasAlreadyLoaned );
                System.out.println( topoAvailabity );
                model.addAttribute( "topo", theTopo );
                model.addAttribute( "user", user );
                model.addAttribute( "isOwner", isOwner );
                model.addAttribute( "topoAvailability", topoAvailabity );
                model.addAttribute( "userHasAlreadyLoaned", userHasAlreadyLoaned );
                model.addAttribute( "reservationAccepted", reservationAccepted );
                // model.addAttribute( "reservationTopo", reservationTopo );
            }
            return "topoDetails";
        } else {
            return "login";
        }
    }


    /**
     *
     * @param keyword
     * @param model
     * @param httpSession
     * @return topo list saved in database
     */
    @GetMapping(path="/topoList")
    public String getHomeNotSignedIn(@ModelAttribute("keyword") String keyword,
                                     Model model,
                                     HttpSession httpSession) {
        List<Topo> topoData=null;
        //grace a l'id dans le path, en recupere en bdd le spot par son id
        if (keyword != null) {
            topoData=this.topoRepository.searchTopo( keyword );
        } else {
            topoData=this.topoRepository.findAll();
        }
        model.addAttribute( "topoList", topoData );
        model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
        model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );
        System.out.println( topoData );
        //on redirige ensuite vers la page qui doit afficher ce topo
        return "/topoList";
    }

}











