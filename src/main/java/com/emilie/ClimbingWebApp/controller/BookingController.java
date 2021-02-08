package com.emilie.ClimbingWebApp.controller;

import com.emilie.ClimbingWebApp.domain.TopoBooking;
import com.emilie.ClimbingWebApp.domain.Topo;
import com.emilie.ClimbingWebApp.domain.User;
import com.emilie.ClimbingWebApp.repositories.TopoBookingRepository;
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
public class BookingController {


    @Autowired
    UserRepository userRepository;
    @Autowired
    TopoRepository topoRepository;
    @Autowired
    TopoBookingRepository topoBookingRepository;

    @GetMapping(path="/booking")
    public String getBooking() {
        return "/booking";
    }


    @GetMapping(path="/add/booking/{id}")
    public String addBooking(@PathVariable("id") Long id,
                             HttpSession httpSession,
                             Model model) {
        return "booking";
    }

    @PostMapping(path="/add/booking/{id}") //todo reitrer les sout pour livraison
    public String booking(@PathVariable("id") Long id,
                          HttpSession httpSession) {
        if (httpSession.getAttribute( "email" ) != null) {

            Optional<Topo> topoData=this.topoRepository.findById( id );
            if (topoData.isPresent()) {
                Topo topo=topoData.get();
                User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();

                TopoBooking topoBooking=new TopoBooking();
                topoBooking.setTopo( topo );
                LocalDateTime date=LocalDateTime.now();
                topoBooking.setBookingDate( date );
                topoBooking.setUser( user );


                topoBooking.setBookingStatus( null );
                this.topoBookingRepository.save( topoBooking );
            }
            return "redirect:/topoDetails/" + id;

        } else {
            return "login";
        }
    }


    @PostMapping(path="/bookingRequestValidation/{id}/{bookingStatus}") //todo reitrer les sout pour livraison
    public String bookingRequestValidation(@PathVariable("id") Long id,
                                           @PathVariable("bookingStatus") Boolean bookingStatus,
                                           HttpSession httpSession) {
        Optional<TopoBooking> topoBooking=this.topoBookingRepository.findById( id );
        if (topoBooking.isPresent()) {
            TopoBooking rsv=topoBooking.get();
            Topo topo=rsv.getTopo();
            topo.setIsBooked( bookingStatus );
            rsv.setBookingStatus( bookingStatus );
            this.topoBookingRepository.save( rsv );
            this.topoRepository.save( topo );
        }
        Long userId=(Long) httpSession.getAttribute( "currentUserId" );
        return "redirect:/userAccount/" + userId;
    }

    @GetMapping(path="/delete/booking/{id}")
    public String deleteBooking(@PathVariable("id") Long id,
                                HttpSession httpSession) {
        Optional<TopoBooking> rsvData=this.topoBookingRepository.findById( id );
        if (rsvData.isPresent()) {
            TopoBooking rsv=rsvData.get();
            Topo topo=rsv.getTopo();
            topo.setIsBooked( false );
            topo.getBooking().remove( rsv );
            this.topoBookingRepository.deleteById( id );
            this.topoRepository.save( topo );
        }
        Long userId=(Long) httpSession.getAttribute( "currentUserId" );
        return "redirect:/userAccount/" + userId;
    }


    @GetMapping(path="/bookingList")
    public String getBookingList(Model model) {
        List<TopoBooking> topoBookings=this.topoBookingRepository.findAll();
        System.out.println( topoBookings );
        model.addAttribute( "topoBookings", topoBookings );
        return "bookingList";
    }

}
