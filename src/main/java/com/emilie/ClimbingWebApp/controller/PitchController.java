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

/**
 * @author Emilie BALSEN
 */

@Controller
public class PitchController {


    @Autowired
    RouteRepository routeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PitchRepository pitchRepository;


    /**
     *
     * @return pitch form to add a new pitch
     */
    @GetMapping(path="/pitch") //TODO
    public String getPitch() {
        return "pitch";
    }

    /**
     *
     * @param pitch
     * @param httpSession
     * @param model
     * @return
     */
    @PostMapping("/pitch") //todo reitrer les sout pour livraison
    public String newPitch(@ModelAttribute("pitch") Pitch pitch, HttpSession httpSession, Model model) {
        if (httpSession.getAttribute( "email" ) != null) {
            // si user existe en session, il est connecté, ok!
            if (pitch != null) { //si cette longueur existe, il vient du formulaire, on le persiste en bdd.
                //recup du user présent dans la session
                System.out.println( pitch );
                User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
                pitch.setUser( user );//lie l'user à la longueur

                System.out.println( pitch );
                this.pitchRepository.save( pitch );

                return "redirect:/pitchDetails/" + pitch.getId(); //redirection qui fonctionne mais sans l'id à transmettre (il faut changer le path de la méthode GetMapping ci-dessus)
            }
        }
        return "login";//si pas connecté, redirection page de login
    }

    /**
     *
     * @param id
     * @param httpSession
     * @param model
     * @return view of the pitch details picked up from database by its id
     */
    @GetMapping(path="/pitchDetails/{id}")
    public String getPitchDetails(@PathVariable("id") Long id, HttpSession httpSession, Model model) {
        //grace a l'id dans le path, en recupere en bdd la longueur par son id
        Optional<Pitch> pitch=this.pitchRepository.findById( id );
        model.addAttribute( "pitch", pitch.get() );
        model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
        model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );

        //on redirige ensuite vers la page qui doit afficher cette longueur
        return "pitchDetails";
    }


    /**
     *
     * @param keyword
     * @param model
     * @param httpSession
     * @return the list containing all pitches saved in database
     */
    @GetMapping(path="/pitchList")   //TODO why not @PostMapping???????????????????????????????????????????
    public String getPitchList(@ModelAttribute("keyword") String keyword, Model model, HttpSession httpSession) {
        List<Pitch> pitchData= null;
        if (keyword != null){
            pitchData=this.pitchRepository.searchPitch(keyword);
        }else {
            pitchData=this.pitchRepository.findAll();
        }

        //tableau des spot list pour pouvoir afficher les areas par spot, plus simplement
        Set<Route> routeList = new HashSet<>();
        for(Pitch p : pitchData){
            if(!routeList.contains(p.getRoute())){
                routeList.add(p.getRoute());
            }
        }

        model.addAttribute( "routeList", routeList );
        model.addAttribute( "pitchList", pitchData );
        model.addAttribute( "userPseudo", httpSession.getAttribute( "pseudo" ) );
        model.addAttribute( "currentUserId", httpSession.getAttribute( "currentUserId" ) );
        System.out.println( pitchData );
        return "/pitchList";
    }


}



