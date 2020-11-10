package com.emilie.ClimbingWebApp.controller;

import com.emilie.ClimbingWebApp.domain.Commentaire;
import com.emilie.ClimbingWebApp.domain.Secteur;
import com.emilie.ClimbingWebApp.domain.Spot;
import com.emilie.ClimbingWebApp.domain.User;
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

@Controller
public class NewSpotController {

    @Autowired
    SpotRepository spotRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SecteurRepository secteurRepository;
    @Autowired
    VoieRepository voieRepository;
    @Autowired
    LongueurRepository longueurRepository;
    @Autowired
    CommentaireRepository commentaireRepository;
    // private SpotService commentaireRepository;
    //private Spot spot


    @GetMapping(path="/newspot")
    public String getNewspot() {
        return "spot/newspot";
    }

    @GetMapping(path="/spotDetails/{id}")
    public String getSpotDetails(@PathVariable("id") Long id, HttpSession httpSession, Model model) {
        //grace a l'id dans le path, en recupere en bdd le spot par son id
        Optional<Spot> spot=this.spotRepository.findById( id );
        List<Commentaire> commentaire=this.commentaireRepository.findBySpot( spot.get() );
        System.out.println( commentaire );
        //et on place ce spot dans la session
        //httpSession.setAttribute( "spot", spot );
        model.addAttribute( "spot", spot.get() );
        model.addAttribute( "commentaire", commentaire);
        //on redirige ensuite vers la page qui doit afficher ce spot
        return "spot/spotDetails";
    }

    @GetMapping(path="/spot/{id}/add/secteur")
    public String addSecteur(@PathVariable("id")Long id, HttpSession httpSession, Model model){
        return "secteur";
    }
    @PostMapping("/spot/{id}/add/secteur")
    public String saveSecteur(@ModelAttribute("secteur") Secteur secteur, @PathVariable("id") Long id, Spot spot, HttpSession httpSession, Model model){
    Optional<Spot> spot1=this.spotRepository.findById( id );
    secteur.setSpot( spot1.get() );
    User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
    secteur.setUser(user);
    secteur=this.secteurRepository.save(secteur);
        return "redirect:/secteurDetails/" +secteur.getId();

    }
    @GetMapping(path="/spot/{id}/add/commentaire")
    public String addCommentaire(@PathVariable("id")Long id, HttpSession httpSession, Model model){
        return "commentaire";
    }

    @PostMapping("/spot/{id}/add/commentaire")
    public String saveCommentaire(@ModelAttribute("commentaire") Commentaire commentaire, @PathVariable("id") Long id, Spot spot, HttpSession httpSession, Model model){
        Optional<Spot> spot1=this.spotRepository.findById( id );
        commentaire.setSpot( spot1.get() );
        User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
        commentaire.setUser( user );
        commentaire=this.commentaireRepository.save(commentaire);
        return "redirect:/spotDetails/" +spot.getId();

    }
  /* @GetMapping("/commentaireDetails/{id}")
    public String showCommentaireDetails(@PathVariable("id") Long id, HttpSession httpSession, Model model){
        Optional<Commentaire> commentaire=this.commentaireRepository.findById( id );
        model.addAttribute( "commentaire", commentaire.get() );
        return "commentaireDetails" ;
    }*/


    //todo methode qui va envoyer vers le formulaire de création de spot et traiter le formulaire
    //todo cette methode ne doit ête utilisable que part un utilisateur connecté
    //todo si utilisateur non connécté, en l'envoie vers la pager de login
    //todo si la méthode ne recoit pas de formulaire à traiter elle doit renvoyer vers le formulaire de cration de spot
    //todo si la methode recoit un formulaire à traiter, elle le traite (persistance en bdd), recupère l'id crée et renvoie vers l'affichage du spot avec son id.
    @PostMapping("/newspot")
    public String newSpot(@ModelAttribute("spot") Spot spot, HttpSession httpSession, Model model) {
        if (httpSession.getAttribute( "email" ) != null) {
            // si user existe en session, il est connecté, ok!
            if (spot != null) { //si spot existe, il vient du formulaire, on le persiste en bdd.
                //recup du user présent dans la session
                User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
                // User user = (User)httpSession.getAttribute("user");
                spot.setUser( user );//lie l'user au spot
                System.out.println( spot );
                //on fait la relation entre le user et le nouveau spot
                // user.getSpot().add(spot); //retourne un spot lié à l'utilisateur
                //on persist le spot en bdd
                this.spotRepository.save( spot );
                //todo recuperer id du nouveau
                //todo redirige sur spotdetailavec id du spot
                // le chemin de redirection ne fonctionne pas avec l'id du spot...
                // mais je ne sais pas comment faire le lien entre le controler spotDetails et l'id du spot
                // return "/spotDetails/"+ spot1.getId();
                return "redirect:/spotDetails/" + spot.getId(); //redirection qui fonctionne mais sans l'id à transmettre (il faut changer le path de la méthode GetMapping ci-dessus)
            } else {//si pas d'informations venant du formulaire newspot a traiter, direction formulaire de newspot
                return "spot/newspot";
            }

        }
        return "login";//si pas connecté, redirection page de login
    }



   /* @GetMapping(path="/commentaireDetails/{id}")
    public String showCommentaire(@)*/


/*
   @GetMapping("/updateForm")
    public String showupdateSpotForm(@ModelAttribute("spot") Spot spot, HttpSession httpSession, Model model) {
        if (httpSession.getAttribute( "email" ) != null) {
            if (spot != null) {
                this.spotRepository.save( spot );
            }
        }
        return "redirect:/spotDetails/" + spot.getId();
    }

    @PostMapping("/updateSpotDetails")
    public String updateSpotDetails(@)*/

}






  /*  //@RequestMapping(path="newspot/{id}")//(path="spotdetails/{id}")
    @GetMapping("spotDetails/{id}")
    public String spotDetails(@PathVariable("id") Long id, ModelMap model*//*, HttpSession session*//*) { //public String spotdetails...

        Optional<Spot> spot=spotRepository.findById( id );
        model.addAttribute( "spot", spot.get() );
        model.addAttribute( "secteur", spot.get().getSecteurs() );

        return "newspot";
    }

    @RequestMapping(path="/newspot")
    @GetMapping
    public String saveNewSpot(Model model) {
        return "newspot";
    }


    @PostMapping("/spotDetails")
    public String showSpotDetails(@ModelAttribute Spot spot, Model model, HttpSession httpSession) {

        String email = (String)httpSession.getAttribute( "email" );
        Optional<User> userConnected = userRepository.findByEmail( email );
        spot.setUser( userConnected.get() );  //liaison utilisateur connecté au spot
        model.addAttribute( "spot", spot );
        spotRepository.save( spot );

       // return "redirect:/newspot";
        return "spotDetails";
    }

    @PostMapping(path="/saveSecteur")
    public String saveSecteur(@ModelAttribute Secteur secteur, Model model){
        model.addAttribute( "secteur", secteur );
        secteurRepository.save(secteur);
        return "secteur";
    }

    @PostMapping(path="/saveVoie")
    public String saveVoie(@ModelAttribute Voie voie, Model model){
        model.addAttribute( "voie", voie );
        voieRepository.save(voie);
        return "voie";
    }

    @PostMapping(path="/saveLongueur")
    public String saveLongueur(@ModelAttribute Longueur longueur, Model model){
        model.addAttribute( "longueur", longueur );
        longueurRepository.save(longueur);
        return "longueur";
    }*/









   /* @RequestMapping(path="/updateSpot")
    @GetMapping
    public String updateSpot(Model model) {
        model.addAttribute( "spot", spot );
        spotRepository.update( spot );
        return "updateSpot";
    }

    @PostMapping("/updateSpot")
    public String updateSpot(@ModelAttribute Spot spot, Model model) {
        model.addAttribute( "spot", spot );
        spotRepository.update( spot );
        return "updateSpot";
    }*/

   /* @RequestMapping("/deleteSpot")
    @GetMapping
    public String deleteSpot(Model model){
        model.addAttribute( "spot", spot );
        spotRepository.delete(spot);
        return "deleteSpot";
    }*/


    /* public ModelAndView newspot(){
         ModelAndView modelAndView= new ModelAndView("newSpotAdd");
         modelAndView.addObject( "spot", new Spot() );
         return modelAndView;
     }

     @RequestMapping("/saveSpot")
     public ModelAndView save(@ModelAttribute("spot") Spot spot){
     ModelAndView modelAndView= new ModelAndView("spotList");
     spotRepository.save(spot);
         List<Spot> list= SpotService.get();
         modelAndView.addObject( "list", list);
         return modelAndView;
     }

     @RequestMapping("/newspot/{id}")
     public ModelAndView get(@PathVariable("id") Long id){
         ModelAndView modelAndView= new ModelAndView("spotAdd");
         Spot spot= spotService.get(id);
         if(spot == null){
             throw new RuntimeException("Spot not found" + id);
         }
         modelAndView.addObject( "spot", spot );
         return modelAndView;
     }

 */