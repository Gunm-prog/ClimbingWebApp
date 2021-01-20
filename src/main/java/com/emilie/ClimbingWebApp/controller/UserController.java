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


/**
 * @author Emilie Balsen
 */
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
    private ReservationTopoRepository reservationTopoRepository;


    /**
     *
     * @param id
     * @param model
     * @param httpSession
     * @return user Account page containing all information about user:
     * spots,areas, routes, pitches, topos she/he added and also the topos borrowed or those waiting for
     * acceptance by the topo's owner
     * @return login page if user is not connected
     */
    @GetMapping(path="/userAccount/{id}")
    public String getUserAccount(@PathVariable("id") Long id, Model model, HttpSession httpSession) {
        //vérification utilisateur connecté avec son mail en session
        if (httpSession.getAttribute( "email" ) != null) {
            // un utilisateur connecté ne peut pas voir le compte d'un autre utilisateur (ex s'il met un id différent du sien dans l'url)
            if ((Long) httpSession.getAttribute( "currentUserId" ) != id) {
                // donc l'utilisateur est systématiquement redirigé vers son propre compte
                id=(Long) httpSession.getAttribute( "currentUserId" );
            }
            Optional<User> user=this.userRepository.findById( id );

        /*
        Recupération de la liste topos appartenant à l'utilisateur connecté.
        Puis on parcourt la liste our récupérer les listes de reservation de chaque topo
        * */

            List<Topo> topos=this.topoRepository.findByUser( user.get() );
            for (Topo topo : topos) { //pour un topo de la liste des topos
                Set<ReservationTopo> reservationTopos=this.reservationTopoRepository.findByTopo( topo ); //on va chercher la liste
                //reservations dudit topo et on lui donne (dans son attribut resevation)
                topo.setReservation( reservationTopos );
            }

        /*
        On instancie une list vide, topoRequestList, destinée à recevoir les topo que l'utilisateur connecté à réservé ou voulu réservé.
        Recupération des demande de réservation réalisé par l'utilisateur connecté
        Puis on parcourt cette liste pour récupèrer le topo associé à chaque reservation
         Une fois le topo associé récupèré, on hydrate son attribut reservation avec la reservation qui nous a permis de le recupérer
         Puis on place ce topo dans la liste topoRequestList
         */
            List<Topo> topoRequestList=new ArrayList<>();
            Set<ReservationTopo> reservationTopos=this.reservationTopoRepository.findByUser( user.get() );
            for (ReservationTopo value : reservationTopos) {
                Optional<Topo> dataTopo=this.topoRepository.findByReservation( value );
                if (dataTopo.isPresent()) {
                    Topo theTopo=dataTopo.get();
                    Set<ReservationTopo> listRsvs=new HashSet<>();
                    listRsvs.add( value );
                    theTopo.setReservation( listRsvs );
                    topoRequestList.add( theTopo );
                }
            }

            List<Spot> spots=this.spotRepository.findByUser( user.get() );
            List<Area> areas=this.areaRepository.findByUser( user.get() );
            model.addAttribute( "user", user.get() );
            model.addAttribute( "topos", topos );
            model.addAttribute( "topoRequestList", topoRequestList );
            model.addAttribute( "spots", spots );
            model.addAttribute( "areas", areas );
            model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
            model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );


            return "userAccount";
        } else {
            return "login";
        }

    }

}