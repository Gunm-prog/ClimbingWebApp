package com.emilie.ClimbingWebApp.controller;


import com.emilie.ClimbingWebApp.domain.*;
import com.emilie.ClimbingWebApp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
//@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopoRepository topoRepository;
    @Autowired
    private SecteurRepository secteurRepository;
    @Autowired
    private SpotRepository spotRepository;
    @Autowired
    private ReservationTopoRepository reservationTopoRepository;



    @GetMapping(path="/userAccount")
    public String getUserAccount(@PathVariable("id") Long id, Model model, HttpSession httpSession) {
        //model.addAttribute( "user", user );
        // String email=(String) httpSession.getAttribute( "email" );
        //String name=(String) httpSession.getAttribute( "name" );
        User user=(User) httpSession.getAttribute( "user" );

        if (user.getEmail() != null) {
            //   Optional<User> userConnected=this.userRepository.findByEmail( user.getEmail() );
            //  if (userConnected.isPresent()){
            //  User present=userConnected.get();//ajout if present
            //  if (present.getPassword().equals( user.getPassword() )) {
            //  this.userRepository.findByPassword( user.getPassword() );
            //  httpSession.getAttribute( "email", email);// non
            //  user.setName( name );
            model.addAttribute( "user", user );
            //   model.addAttribute( "email", user.getEmail() );
            //   model.addAttribute( "name", user.getName() );


            //TODO ajout de tous les attributs de l'utilisateur pour les afficher
            return "userAccount";
        }
        //  }
        //  }

        // return "redirect:/login";
        return "login";
    }

    @GetMapping(path="/userAccount/{id}")
    public String getUserAccount(@PathVariable("id") Long id, Model model) {
        Optional<User> user=this.userRepository.findById( id );
        System.out.println(user);

        /*
        Recupération de la liste topos appartenant à l'utilisateur connecté.
        Puis on parcourt la liste our récupérer les listes de reservation de chaque topo
        * */
        List<Topo> topos=this.topoRepository.findByUser( user.get() );
        for ( Topo value : topos ){
            Set<ReservationTopo> reservationTopos = this.reservationTopoRepository.findByTopo( value); //value étant un objet Topo
                value.setReservation( reservationTopos );
        }

        /*
        On instancie une list vide, topoRequestList, destinée à recevoir les topo que l'utilisateur connecté à réservé ou voulu réservé.
        Recupération des demande de réservation réalisé par l'utilisateur connecté
        Puis on parcourt cette liste pour récupèrer le topo associé à chaque reservation
         Une fois le topo associé récupèré, on hydrate son attribut reservation avec la reservation qui nous a permis de le recupérer
         Puis on place ce topo dans la liste topoRequestList
         */
        List<Topo> topoRequestList = new ArrayList<>();
        Set<ReservationTopo>reservationTopos=this.reservationTopoRepository.findByUser( user.get() );
        for (ReservationTopo value: reservationTopos){
            Optional<Topo> dataTopo = this.topoRepository.findByReservation(value);
            if(dataTopo.isPresent()){
                Topo theTopo = dataTopo.get();
                Set<ReservationTopo> listRsvs = new HashSet<>();
                listRsvs.add(value);
                theTopo.setReservation(listRsvs);
                topoRequestList.add(theTopo);
            }
        }

        System.out.println( topos );
        List<Spot>spots=this.spotRepository.findByUser(user.get());
        List<Secteur>secteurs=this.secteurRepository.findByUser(user.get());
        model.addAttribute( "user", user.get() );
        model.addAttribute( "topos", topos );
        model.addAttribute( "topoRequestList", topoRequestList );
        model.addAttribute( "spots",spots );
        model.addAttribute( "secteurs", secteurs );



        return "userAccount";


//todo rendre les utilisateurs uniques

    }

}