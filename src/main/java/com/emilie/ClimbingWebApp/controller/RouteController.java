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


/**
 * @author Emilie Balsen
 */
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


    /**
     *
     * @return route form allowing a connected user to add a route
     */
    @GetMapping(path="/route") //TODO
    public String getNewRoute() {

        return "route";
    }

    /**
     *
     * @param route
     * @param httpSession
     * @return
     */
    @PostMapping("/route")// TODO modif B de la feuille des modifs
    public String newRoute(@ModelAttribute("route") Route route,
                           HttpSession httpSession) {

        if (httpSession.getAttribute( "email" ) != null) {
            // si user existe en session, il est connecté, ok!
            if (route != null) { //si route existe, il vient du formulaire, on le persiste en bdd.
                //recup du user présent dans la session

                User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
                route.setUser( user );//lie l'user a la route
                System.out.println( route );
                this.routeRepository.save( route );
                return "redirect:/routeDetails/" + route.getId(); //redirection qui fonctionne mais sans l'id à transmettre
                // (il faut changer le path de la méthode GetMapping ci-dessus)
            }

        }
        return "login";//si pas connecté, redirection page de login //TODO why pas "redirect:/login" ???
    }


    /**
     *
     * @param id
     * @param httpSession
     * @param model
     * @return route details picked up in database thanks to the id
     */
    @GetMapping(path="/routeDetails/{id}") //todo reitrer les sout pour livraison
    public String getRouteDetails(@PathVariable("id") Long id, HttpSession httpSession, Model model) {
        //grace a l'id dans le path, en recupere en bdd le spot par son id
        Optional<Route> routeData=this.routeRepository.findById( id );
        Route route=new Route();
        if (routeData.isPresent()) {
            route=routeData.get();
            System.out.println( "test" );
            System.out.println( route );
        }
        List<Pitch> pitches=this.pitchRepository.findByRoute( route );
        //todo retrait sout pour livraison ;)
        System.out.println( route);
        model.addAttribute( "route", route );
        model.addAttribute( "pitch", pitches );
        model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
        model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );

        //on redirige ensuite vers la page qui doit afficher ce secteur
        return "routeDetails";
    }


    /**
     *
     * @param pitch
     * @param id
     * @param httpSession
     * @param model
     * @return pitch form in order to add a new pitch to a specific route
     * @return login page if user is not connected
     */
    @GetMapping(path="/{id}/add/pitch") //todo reitrer les sout pour livraison
    public String addNewPitch(@ModelAttribute("pitch") Pitch pitch,
                                 @PathVariable("id") Long id,
                                 HttpSession httpSession,
                                 Model model) {
        if (httpSession.getAttribute( "email" ) != null) {
            System.out.println( pitch );
            model.addAttribute( "pitch", pitch );
            model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
            model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );
            return "pitch";

        } else {
            return "redirect:/login";
        }
    }


    /**
     *
     * @param pitch
     * @param id
     * @param httpSession
     * @return pitch details that has just been saved in database linked to a specific route thanks to its id
     * @return login details if user is not connected
     */
    @PostMapping("/{id}/add/pitch")
    public String saveNewPitch(@ModelAttribute("pitch") Pitch pitch,
                                  @PathVariable("id") Long id,
                                  HttpSession httpSession) {

        if (httpSession.getAttribute( "email" ) != null) {
            System.out.println( pitch );
            Pitch newPitch=new Pitch();
            newPitch.setDistance( pitch.getDistance() );
            newPitch.setPoints( pitch.getPoints() );
            newPitch.setQuotation( pitch.getQuotation() );
            Optional<Route> routeData=this.routeRepository.findById( id );
            System.out.println( routeData );
            newPitch.setRoute( routeData.get() );
            User user=this.userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
            System.out.println( user );
            newPitch.setUser( user );
            System.out.println( newPitch);
            pitch=this.pitchRepository.save( newPitch );
            return "redirect:/pitchDetails/" + pitch.getId();
        } else {
            return "redirect:/login"; //TODO Why necessary just if connexion was lost while user submitted the new pitch????????
        }
    }

    /**
     *
     * @param keyword
     * @param model
     * @param httpSession
     * @return route list containing all routes saved in database
     */
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
        System.out.println( routeData );
        //on redirige ensuite vers la page qui doit afficher ce spot
        return "routeList";
    }
}



