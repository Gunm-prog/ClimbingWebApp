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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Controller
public class NewSpotController {

    @Autowired
    SpotRepository spotRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AreaRepository areaRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    TopoRepository topoRepository;


    @GetMapping(path="/newspot")
    public String addNewspot(@ModelAttribute("newspot") Spot spot,
                             HttpSession httpSession,
                             Model model) {
        if (httpSession.getAttribute( "email" ) != null) {
            model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
            model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );

            return "spot/newspot";
        }
        return "redirect:/login";
    }


    @PostMapping("/newspot")
    public String newSpot(@ModelAttribute("spot") Spot spot,
                          HttpSession httpSession) {

        if (httpSession.getAttribute( "email" ) != null) {
            if (spot != null) {
                User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
                spot.setUser( user );
                this.spotRepository.save( spot );
                return "redirect:/spotDetails/" + spot.getId();
            } else {
                return "spot/newspot";
            }

        }
        return "login";
    }

    @GetMapping(path="/spotDetails/{id}")
    public String getSpotDetails(@PathVariable("id") Long id,   //TODO      ???
                                 HttpSession httpSession,
                                 Model model) {

        Optional<Spot> spotData=this.spotRepository.findById( id );
        Spot spot=new Spot();
        if (spotData.isPresent()) {
            spot=spotData.get();
        }

        model.addAttribute( "spot", spot );
        model.addAttribute( "userId", httpSession.getAttribute( "currentUserId" ) );
        model.addAttribute( "userRole", httpSession.getAttribute( "currentUserRole" ) );
        model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
        model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );

        return "spot/spotDetails";
    }

    @GetMapping(path="/spotDetails/spot/{id}/tag")
    public String tagSpot(@PathVariable("id") Long id,
                          HttpSession httpSession, Model model) {

        if (httpSession.getAttribute( "email" ) != null) {
            Optional<Spot> spotData=this.spotRepository.findById( id );
            if (spotData.isPresent()) {
                Spot spot=spotData.get();
                if (spot.isTag() == false) {
                    spot.setTag( true );
                } else {
                    spot.setTag( false );
                }

                this.spotRepository.save( spot );
            }
            return "redirect:/spotDetails/" + id;
        } else {
            return "redirect:/login";
        }
    }


    @GetMapping(path="/spot/{id}/add/area")
    public String addSecteur(@ModelAttribute("area") Area area,
                             @PathVariable("id") Long id,
                             HttpSession httpSession,
                             Model model) {

        if (httpSession.getAttribute( "email" ) != null) {
            model.addAttribute( "area", area );
            model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
            model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );
            return "area";
        } else {
            return "redirect:/login";
        }
    }


    @PostMapping("/spot/{id}/add/area")
    public String saveSecteur(@ModelAttribute("area") Area area,
                              @PathVariable("id") Long id,
                              Spot spot,
                              HttpSession httpSession) {

        if (httpSession.getAttribute( "email" ) != null) {
            Area newArea=new Area();
            newArea.setName( area.getName() );
            newArea.setDescription( area.getDescription() );
            newArea.setNumberOfRoutes( area.getNumberOfRoutes() );
            newArea.setSpot( spot );
            Optional<Spot> spot1=this.spotRepository.findById( id );
            area.setSpot( spot1.get() );
            User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
            newArea.setUser( user );
            area=this.areaRepository.save( newArea );
            return "redirect:/areaDetails/" + area.getId();
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(path="/spot/{id}/add/comment")
    public String addComment(HttpSession httpSession,
                             Model model) {

        if (httpSession.getAttribute( "email" ) != null) {
            model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
            model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );
            return "comment";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/spot/{id}/add/comment")
    public String saveComment(@ModelAttribute("comment") Comment comment,
                              Spot spot,
                              HttpSession httpSession) {

        if (httpSession.getAttribute( "email" ) != null) {
            Comment newCom=new Comment();
            newCom.setName( comment.getName() );
            newCom.setContent( comment.getContent() );
            newCom.setSpot( spot );

            User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
            newCom.setUser( user );

            LocalDateTime date=LocalDateTime.now();
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );
            String stringDate=date.format( formatter );
            newCom.setDate( stringDate );

            this.commentRepository.save( newCom );
            return "redirect:/spotDetails/" + spot.getId();
        } else {
            return "redirect:/login";
        }

    }

    @GetMapping(path="/spot/{id}/add/topo")
    public String addTopo(@ModelAttribute("spot") Spot spot,
                          HttpSession httpSession,
                          Model model) {

        if (httpSession.getAttribute( "email" ) != null) {
            model.addAttribute( "spot", spot );
            model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
            model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );
            return "topo";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/spot/{id}/add/topo")
    public String saveTopo(@ModelAttribute("topo") Topo topo,
                           @PathVariable("id") Long id,
                           HttpSession httpSession) {

        if (httpSession.getAttribute( "email" ) != null) {

            Topo newTopo=new Topo();
            newTopo.setTitle( topo.getTitle() );
            newTopo.setAuthor( topo.getAuthor() );
            newTopo.setPublicationDate( topo.getPublicationDate() );
            Optional<Spot> spotData=this.spotRepository.findById( id );
            newTopo.setSpot( spotData.get() );

            String userEmail=(String) httpSession.getAttribute( "email" );
            Optional<User> userData=userRepository.findByEmail( userEmail );
            newTopo.setUser( userData.get() );
            newTopo=this.topoRepository.save( newTopo );
            return "redirect:/topoDetails/" + newTopo.getId();
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(path="/spot/spotList")
    public String getSpotList(@ModelAttribute("keyword") String keyword,
                              @ModelAttribute("quotation") String quotation,
                              Model model,
                              HttpSession httpSession) {

        List<Spot> spotData=null;
        if (quotation.isEmpty() && keyword.isEmpty()) {
            spotData=this.spotRepository.findAll();
        } else if (!keyword.isEmpty() && !quotation.isEmpty()) {
            spotData=this.spotRepository.searchByQuotationAndKeyword( quotation, keyword );
        } else if (!keyword.isEmpty()) {
            spotData=this.spotRepository.searchSpot( keyword );
        } else {
            spotData=this.spotRepository.searchByQuotation( quotation );
        }


        model.addAttribute( "spotList", spotData );
        model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
        model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );
        return "spot/spotList";
    }

}


