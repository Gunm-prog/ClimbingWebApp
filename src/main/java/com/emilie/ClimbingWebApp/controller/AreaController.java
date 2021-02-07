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

/**
 * @author Emilie Balsen
 */
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


    /**
     * @return the area form that allows the user to add a new climbing area
     */
    @GetMapping(path="/area")   //TODO
    public String getArea() {
        return "area";
    }

    /**
     * method to post a newArea
     * @param area instance of a area from ModelAttribute
     * @param httpSession instance of session
     * @return the area details form containing the information of the new area that has
     * just been saved in database
     */
    @PostMapping("/area")
    public String newArea(@ModelAttribute("area") Area area, HttpSession httpSession) {
        if (httpSession.getAttribute( "email" ) != null) {
            // si user existe en session, il est connecté, ok!
            if (area != null) { //si spot existe, il vient du formulaire, on le persiste en bdd.
                //recup du user présent dans la session

                User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
                area.setUser( user );//lie l'user au area
                System.out.println( area );
                this.areaRepository.save( area );

                return "redirect:/areaDetails/" + area.getId(); //redirection qui fonctionne mais sans l'id à transmettre (il faut changer le path de la méthode GetMapping ci-dessus)
            } else {//si pas d'informations venant du formulaire newspot a traiter, direction formulaire de newspot
                return "area";
            }

        }
        return "login";//si pas connecté, redirection page de login
    }


    //todo accès meme si pas connecté?
    /**
     * @param id
     * @param httpSession instance of session
     * @param model
     * @return the view of the details of an area
     */
    @GetMapping(path="/areaDetails/{id}")
    public String getAreaDetails(@PathVariable("id") Long id, HttpSession httpSession, Model model) {
        //grace a l'id dans le path, en recupere en bdd le spot par son id
        Optional<Area> areaData=this.areaRepository.findById( id );
        Area area=new Area();
        if (areaData.isPresent()) {
            area=areaData.get();
            System.out.println( "test" );
            System.out.println( area );
        }
        /* List<Route> voys=this.routeRepository.findByArea(area.get());*/
    //    List<Route> route=this.routeRepository.findByArea( area );

   //     System.out.println( route ); //TODO route à mettre au pluriel
        model.addAttribute( "area", area );
    //    model.addAttribute( "route", route );
        model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
        model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );

        //on redirige ensuite vers la page qui doit afficher ce area
        return "areaDetails";
    }

    /**
     * Allow the user to add a new route by filling the route form
     *
     * @param route instance of route from ModelAttribute
     * @param id
     * @param httpSession instance of session
     * @param model
     * @return the route form in order to be able to add a new route
     */
    @GetMapping(path="/{id}/add/route")
    public String addNewRoute(@ModelAttribute("route") Route route,
                              @PathVariable("id") Long id,
                              HttpSession httpSession,
                              Model model) {

        if (httpSession.getAttribute( "email" ) != null) {
            System.out.println( route );
            model.addAttribute( "route", route );
            model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
            model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );

            return "route";
        } else {
            return "redirect:/login";
        }
    }

    /**
     * @param route instance of route from ModelAttribute
     * @param id
     * @param httpSession instance of session
     * @return the details of the new route that has just been saved
     */
    @PostMapping("/{id}/add/route")
    public String saveNewVoie(@ModelAttribute("route") Route route,
                              @PathVariable("id") Long id,
                              HttpSession httpSession) {

        if (httpSession.getAttribute( "email" ) != null) {

            System.out.println( route );
            Route newRoute=new Route();
            newRoute.setName( route.getName() );
            newRoute.setHauteur( route.getHauteur() );
            newRoute.setNombre( route.getNombre() );
            Optional<Area> area1=this.areaRepository.findById( id );
            System.out.println( area1 );
            newRoute.setArea( area1.get() );
            User user=this.userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
            System.out.println( user );
            newRoute.setUser( user );
            System.out.println( newRoute );
            route=this.routeRepository.save( newRoute );

            return "redirect:/routeDetails/" + route.getId();

        } else {
            return "redirect:/login";
        }
    }

    /**
     * @param keyword instance of keyword from ModelAttribute
     * @param model
     * @param httpSession instance of session
     * @return the area list that contains all the areas saved in database (linked to their spots)
     */
    @GetMapping(path="/areaList")
    public String getAreaList(@ModelAttribute("keyword") String keyword, Model model, HttpSession httpSession) {
        List<Area> areaData=null;
        if (keyword != null) {
            areaData=this.areaRepository.searchArea( keyword );
        } else {
            areaData=this.areaRepository.findAll();
        }

        //tableau des spot list pour pouvoir afficher les areas par spot, plus simplement
        Set<Spot> spotList = new HashSet<>();
        for(Area a : areaData){
            if(!spotList.contains(a.getSpot())){
                spotList.add(a.getSpot());
            }
        }

        model.addAttribute( "spotList" , spotList);
        model.addAttribute( "areaList", areaData );
        model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
        model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );
        System.out.println( areaData );
        //on redirige ensuite vers la page qui doit afficher ce spot
        return "areaList";
    }

}



