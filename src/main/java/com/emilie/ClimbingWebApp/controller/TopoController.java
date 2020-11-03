package com.emilie.ClimbingWebApp.controller;


import com.emilie.ClimbingWebApp.domain.Topo;
import com.emilie.ClimbingWebApp.domain.User;
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

@Controller
public class TopoController {

    @Autowired
    TopoRepository topoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SpotRepository spotRepository;


    @GetMapping(path="/topo")
    public String getTopo(){
        return "topo";
    }

    @GetMapping(path="/topoDetails/{id}")
    public String getTopoDetails(@PathVariable("id") Long id, HttpSession httpSession, Model model){
        //grace a l'id dans le path, en recupere en bdd le spot par son id
        Optional<Topo> topo = this.topoRepository.findById( id );
        model.addAttribute( "topo", topo.get() );


        //on redirige ensuite vers la page qui doit afficher ce secteur
        return "topoDetails";
    }

    @PostMapping("/topo")
    public String newTopo(@ModelAttribute("topo") Topo topo, HttpSession httpSession, Model model){
        if(httpSession.getAttribute( "email" ) != null){
            // si user existe en session, il est connecté, ok!
            if(topo != null){ //si spot existe, il vient du formulaire, on le persiste en bdd.
                //recup du user présent dans la session

                User user= userRepository.findByEmail( (String)httpSession.getAttribute( "email" ) ).get();
                topo.setUser( (List<User>) user );//lie l'user au topo
                System.out.println(topo);
                this.topoRepository.save(topo);
                return "redirect:/topoDetails/" + topo.getId(); //redirection qui fonctionne mais sans l'id à transmettre (il faut changer le path de la méthode GetMapping ci-dessus)
            }
            else{//si pas d'informations venant du formulaire newspot a traiter, direction formulaire de newspot
                return "topo";
            }

        }
        return "login" ;//si pas connecté, redirection page de login
    }


}




/*
    @RequestMapping(path="topo/{id}")
    @GetMapping
    public String showTopoForm(@PathVariable("id") Long id, Model model){
        Optional<Topo> topo= topoRepository.findById( id );
        model.addAttribute( "topo", topo.get() );
        return "topo";
    }

    @PostMapping("/topoDetails")
    public String topodetails(@ModelAttribute Topo topo, Model model){
        model.addAttribute( "topo", topo );
        topoRepository.save(topo);
        return "topoDetails";
    }

    @RequestMapping(path="/topo")
    @GetMapping
    public String saveNewTopo(@ModelAttribute Topo topo, Model model){
        return "topo";
    }


    @PostMapping("/topo")
    public String saveTopo(@ModelAttribute Topo topo, Model model){

        model.addAttribute( "topo", topo );
        topoRepository.save(topo);
        return "topo";
    }
*/







