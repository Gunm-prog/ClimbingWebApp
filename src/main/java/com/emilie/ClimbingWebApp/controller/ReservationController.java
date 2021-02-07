package com.emilie.ClimbingWebApp.controller;

import com.emilie.ClimbingWebApp.domain.ReservationTopo;
import com.emilie.ClimbingWebApp.domain.Topo;
import com.emilie.ClimbingWebApp.domain.User;
import com.emilie.ClimbingWebApp.repositories.ReservationTopoRepository;
import com.emilie.ClimbingWebApp.repositories.TopoRepository;
import com.emilie.ClimbingWebApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ReservationController {


    @Autowired
    UserRepository userRepository;
    @Autowired
    TopoRepository topoRepository;
    @Autowired
    ReservationTopoRepository reservationTopoRepository;

    @GetMapping(path="/reservation")
    public String getReservation() {
        return "/reservation";
    }

   /* @GetMapping(path="/reservationDetails/{id}")
    public String getReservationDetails(@PathVariable("id") Long id, HttpSession httpSession, Model model) {
        Optional<User> user=this.userRepository.findById( id );
        System.out.println( user );
        Set<ReservationTopo> reservationTopos=this.reservationTopoRepository.findByUser( user.get() );
        model.addAttribute( "user", user.get() );
        model.addAttribute( "reservation", reservationTopos );
        System.out.println( reservationTopos );
        return "reservationDetails";

    }*/

    @GetMapping(path="/add/reservation/{id}")
    public String addReservation(@PathVariable("id") Long id, HttpSession httpSession, Model model) {
        return "reservation";
    }

    @PostMapping(path="/add/reservation/{id}") //todo reitrer les sout pour livraison
    public String reservation(@PathVariable("id") Long id, HttpSession httpSession) {
        if (httpSession.getAttribute( "email" ) != null) {
            // si user existe en session, il est connecté, ok!
            Optional<Topo> topoData=this.topoRepository.findById( id );
            if (topoData.isPresent()) {
                Topo topo=topoData.get();
                //recup de l'user connecté
                User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();

                //instanciation d'un nouvel objet reservation (vide)
                ReservationTopo reservationTopo=new ReservationTopo();

                //on associe la reservation avec les données necessaire
                reservationTopo.setTopo( topo );
                LocalDateTime date=LocalDateTime.now();
                reservationTopo.setDateReservation( date );
                reservationTopo.setUser( user );

                //c'est une demande de reservation, mise en pending donc
                reservationTopo.setReservationStatus( null );

                // ecriture de la reservation en bdd
                System.out.println( reservationTopo );
                this.reservationTopoRepository.save( reservationTopo );
            }
            return "redirect:/topoDetails/" + id;
            //return "reservation";
        } else {
            return "login";//si pas connecté, redirection page de login
        }
    }

    @PostMapping(path="/reservationRequestValidation/{id}/{reservationStatus}") //todo reitrer les sout pour livraison
    public String reservationRequestValidation(@PathVariable("id") Long id, @PathVariable("reservationStatus") Boolean reservationStatus, HttpSession httpSession) {
        Optional<ReservationTopo> reservationTopo=this.reservationTopoRepository.findById( id );
        if (reservationTopo.isPresent()) {
            ReservationTopo rsv=reservationTopo.get();
            Topo topo=rsv.getTopo();
            //  System.out.println("test topo dans rsvReqVal");
            //  System.out.println(topo);
            topo.setReserved( reservationStatus );
            rsv.setReservationStatus( reservationStatus );
            this.reservationTopoRepository.save( rsv );
            this.topoRepository.save( topo );
        }
        Long userId=(Long) httpSession.getAttribute( "currentUserId" );
        return "redirect:/userAccount/" + userId;
    }

    @GetMapping(path="/delete/reservation/{id}")
    public String deleteReservation(@PathVariable("id") Long id, HttpSession httpSession) {
        Optional<ReservationTopo> rsvData=this.reservationTopoRepository.findById( id );
        if (rsvData.isPresent()) {
            ReservationTopo rsv=rsvData.get();
            Topo topo=rsv.getTopo();
            topo.setReserved( false );//on met le statut du topo en libre à nouveau (non reservé)
            topo.getReservation().remove( rsv );//on retire de la liste des demandes de reservations la reservation qui vient de se terminer.
            this.reservationTopoRepository.deleteById( id ); // suppression en bdd de la resevation qui vient de se terminer
            this.topoRepository.save( topo );
        }
        Long userId=(Long) httpSession.getAttribute( "currentUserId" );
        return "redirect:/userAccount/" + userId;
    }


    @GetMapping(path="/reservationList")
    public String getHomeNotSignedIn(Model model) {
        List<ReservationTopo> reservationTopos=this.reservationTopoRepository.findAll();
        System.out.println( reservationTopos );
        model.addAttribute( "reservationTopos", reservationTopos );
        return "reservationList";
    }

}
