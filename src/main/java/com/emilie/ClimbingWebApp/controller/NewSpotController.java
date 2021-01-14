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
    SecteurRepository secteurRepository;
    @Autowired
    CommentaireRepository commentaireRepository;
    @Autowired
    TopoRepository topoRepository;


    //TODO remettre les methodes dans l'ordre ex newspot il y en a une tout en bas!!!!!


    @GetMapping(path="/newspot")
    public String addNewspot() {
        return "spot/newspot";
    }   //TODO

    @GetMapping(path="/spotDetails/{id}")
    public String getSpotDetails(@PathVariable("id") Long id,   //TODO      ???
                                 HttpSession httpSession,
                                 Model model) {

        //grace a l'id dans le path, en recupere en bdd le spot par son id
        Optional<Spot> spotData=this.spotRepository.findById( id );
        System.out.println( "dans controller spotDetails" );
        System.out.println( spotData );
        System.out.println( "spotId :" + id );
        Spot spot=new Spot();
        if (spotData.isPresent()) {
            spot=spotData.get();
            System.out.println( "test" );
            System.out.println( spot );

        }
        List<Commentaire> commentaires=this.commentaireRepository.findBySpot( spot );
        for (Commentaire commentaire : commentaires) {
            Optional<User> userData=this.userRepository.findByCommentaire( commentaire );
            if (userData.isPresent()) {
                System.out.println( userData );
                commentaire.setUser( userData.get() );
            }
        }
        System.out.println( commentaires );

        List<Secteur> secteurs=this.secteurRepository.findBySpot( spot );

        //et on place ce spot dans la session
        //httpSession.setAttribute( "spot", spot );
        model.addAttribute( "spot", spot );
        model.addAttribute( "userId", httpSession.getAttribute( "currentUserId" ) );
        model.addAttribute( "userRole", httpSession.getAttribute( "currentUserRole" ) );
        model.addAttribute( "commentaires", commentaires );
        model.addAttribute( "secteurs", secteurs );
        //on redirige ensuite vers la page qui doit afficher ce spot
        return "spot/spotDetails";
    }

    @GetMapping(path="/spotDetails/spot/{id}/tag")
    public String tagSpot(@PathVariable("id") Long id,
                          HttpSession httpSession) {

        if (httpSession.getAttribute( "email" ) != null) {
            // System.out.println(spot);
            Optional<Spot> spotData=this.spotRepository.findById( id );
            //    System.out.println(spotData);
            if (spotData.isPresent()) {
                Spot spot=spotData.get();
                if (spot.isTag() == false) { //si le spot n'est pas tag,
                    spot.setTag( true ); //on le passe à true pour le tag
                } else { //sinon c'est qu'il est tag et donc on peut le detag
                    spot.setTag( false );
                }
                //System.out.println(spot);
                this.spotRepository.save( spot );
            }
            return "redirect:/spotDetails/" + id;
        } else {
            return "redirect:/login";
        }
    }


    @GetMapping(path="/spot/{id}/add/secteur")              //TODO to check!!!!!
    public String addSecteur(@ModelAttribute("secteur") Secteur secteur,
                             @PathVariable("id") Long id,   //TODO why id est en gris
                             HttpSession httpSession,
                             Model model) {

        if (httpSession.getAttribute( "email" ) != null) {
            System.out.println( secteur );
            model.addAttribute( "secteur", secteur );
            return "secteur";
        } else {
            return "redirect:/login";
        }
    }


    @PostMapping("/spot/{id}/add/secteur")
    public String saveSecteur(@ModelAttribute("secteur") Secteur secteur,
                              @PathVariable("id") Long id,
                              Spot spot,
                              HttpSession httpSession) {
        if (httpSession.getAttribute( "email" ) != null) {
            Secteur newSecteur=new Secteur();
            newSecteur.setName( secteur.getName() );
            newSecteur.setDescription( secteur.getDescription() );
            newSecteur.setSpot( spot );
            Optional<Spot> spot1=this.spotRepository.findById( id );
            secteur.setSpot( spot1.get() );
            User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
            newSecteur.setUser( user );
            secteur=this.secteurRepository.save( newSecteur );
            return "redirect:/secteurDetails/" + secteur.getId();
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(path="/spot/{id}/add/commentaire")   //TODO To check!!!!!!!
    public String addCommentaire(@ModelAttribute("commentaire") Commentaire commentaire,
                                 @PathVariable("id") Long id,
                                 HttpSession httpSession,
                                 Model model) {

        if (httpSession.getAttribute( "email" ) != null) {
            System.out.println( commentaire );
            model.addAttribute( "commentaire", commentaire );
            return "commentaire";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/spot/{id}/add/commentaire") //TODO To check!!!!!!
    public String saveCommentaire(@ModelAttribute("commentaire") Commentaire commentaire,
                                  Spot spot,
                                  HttpSession httpSession) { //TODO check!!!!!

        if (httpSession.getAttribute( "email" ) != null) {
            Commentaire newCom=new Commentaire();
            newCom.setName( commentaire.getName() );
            newCom.setContent( commentaire.getContent() );
            newCom.setSpot( spot );

            User user=userRepository.findByEmail( (String) httpSession.getAttribute( "email" ) ).get();
            newCom.setUser( user );

            LocalDateTime date=LocalDateTime.now();
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" ); //TODO Meme methode à mettre dans le form creation topo
            String stringDate=date.format( formatter );
            newCom.setDate( stringDate );

            this.commentaireRepository.save( newCom );
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
            System.out.println( spot );
            model.addAttribute( "spot", spot );
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
            newTopo.setDateOfPublishing( topo.getDateOfPublishing() );
            Optional<Spot> spotData=this.spotRepository.findById( id );
            newTopo.setSpot( spotData.get() );

            //Récupération de l'email (de l'user connecté) qui se trouve dans la session
            String userEmail=(String) httpSession.getAttribute( "email" );
            //On cherche l'utilisateur par le biais de son mail pour récupérer l'objet User pour
            Optional<User> userData=userRepository.findByEmail( userEmail );
            //pour pouvoir lier le topo à l'utilisateur
            newTopo.setUser( userData.get() );
            newTopo=this.topoRepository.save( newTopo );
            return "redirect:/topoDetails/" + newTopo.getId();
        } else {
            return "redirect:/login";
        }
    }

    //todo methode qui va envoyer vers le formulaire de création de spot et traiter le formulaire
    //todo cette methode ne doit ête utilisable que part un utilisateur connecté
    //todo si utilisateur non connécté, en l'envoie vers la pager de login
    //todo si la méthode ne recoit pas de formulaire à traiter elle doit renvoyer vers le formulaire de cration de spot
    //todo si la methode recoit un formulaire à traiter, elle le traite (persistance en bdd), recupère l'id crée et renvoie vers l'affichage du spot avec son id.
    @PostMapping("/newspot")
    public String newSpot(@ModelAttribute("spot") Spot spot,
                          HttpSession httpSession) {

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

    @GetMapping(path="/spot/spotList")
    public String getHomeNotSignedIn(@ModelAttribute("keyword") String keyword,
                                     @ModelAttribute("quotation") String quotation,
                                     Model model) {

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


        //et on place ce spot dans la session
        //httpSession.setAttribute( "spot", spot );
        model.addAttribute( "spotList", spotData );
        System.out.println( spotData );
        //on redirige ensuite vers la page qui doit afficher ce spot
        return "spot/spotList";
    }


    @GetMapping("/updateForm")                 //TODO je pense pas que ça soit necessaire!!!!
    public String showupdateSpotForm(@ModelAttribute("spot") Spot spot,
                                     HttpSession httpSession) {
        if (httpSession.getAttribute( "email" ) != null) {
            if (spot != null) {
                this.spotRepository.save( spot );
            }
        }
        return "redirect:/spotDetails/" + spot.getId();
    }


}


