package com.emilie.ClimbingWebApp.controller;


import com.emilie.ClimbingWebApp.domain.Comment;
import com.emilie.ClimbingWebApp.domain.Spot;
import com.emilie.ClimbingWebApp.repositories.CommentRepository;
import com.emilie.ClimbingWebApp.repositories.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @author Emilie BALSEN
 */

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    SpotRepository spotRepository;


    /**
     *
     * @param id
     * @param spotId
     * @param httpSession
     * @param model
     * @return le formulaire de mise à jour d'un commentaire sous la forme de
     * @return la page de login si l'utilisateur n'est pas connecté
     */
    @GetMapping(path="/spot/{spotId}/comment/{id}/updateComment") //todo reitrer les sout pour livraison
    public String updateComment(@PathVariable("id") Long id,
                                @PathVariable("spotId") Long spotId,
                                HttpSession httpSession,
                                Model model) {

        if (httpSession.getAttribute( "email" ) != null) {
            //recuperer le commentaire par son id
            //model attribute
            Optional<Comment> commentData=this.commentRepository.findById( id );
            Optional<Spot> spotData=this.spotRepository.findById( spotId );

            if (commentData.isPresent() && spotData.isPresent()) {
                Comment comment=commentData.get();
                comment.setSpot( spotData.get() );
                model.addAttribute( "comment", comment );
            } else {
                model.addAttribute( "error", "comment not found" );
            }
            return "updateCommentForm";
        }else {
            return "redirect:/login";
        }

    }

    /**
     *
     * @param comment
     * @param httpSession
     * @return the view of spot details page where the updated comment has just been saved
     * @return the login page if user is not connected
     */
    @PostMapping(path="/spot/{spotId}/updateComment/{comId}/success") //todo reitrer les sout pour livraison
    public String submitUpdateComment(@ModelAttribute("comment") Comment comment,
                                      @PathVariable("spotId") Long spotId,
                                      @PathVariable("comId") Long comId,
                                      HttpSession httpSession) {

        System.out.println(spotId);
        System.out.println(comment.getId());
        if (httpSession.getAttribute( "email" ) != null) {
            Optional<Comment> commentData=this.commentRepository.findById( comId );
            if (commentData.isPresent()) {
                Comment oldComment=commentData.get();
                oldComment.setName( comment.getName() );
                oldComment.setContent( comment.getContent() );
                this.commentRepository.save( oldComment );
            }
            return "redirect:/spotDetails/" + spotId;
        }else {
            return "redirect:/login";
        }
    }

    /**
     *
     * @param id
     * @param spotId
     * @param model
     * @param httpSession
     * @return the delete comment form
     * @return the login page if the user is not connected
     */
   /* @GetMapping(path="/spot/{spotId}/comment/{id}/deleteComment") //todo reitrer les sout pour livraison
    public String deleteComment(@PathVariable("id") Long id,
                                @PathVariable("spotId") Long spotId,
                                Model model,
                                HttpSession httpSession) {

        if (httpSession.getAttribute( "email" ) != null) {
            Optional<Comment> commentData=this.commentRepository.findById( id );
            Optional<Spot> spotData=this.spotRepository.findById( spotId );
            System.out.println( "test recup commentaire deleteCom" );
            System.out.println( commentData );
            if (commentData.isPresent() && spotData.isPresent()) {
                Comment comment=commentData.get();
                comment.setSpot( spotData.get() );
                model.addAttribute( "comment", comment );
            } else {
                model.addAttribute( "error", "comment not found" );
            }
            return "deleteCommentForm";
        }else {
            return "redirect:/login";
        }
    }*/

    /**
     *
     * @param comment
     * @param id
     * @param httpSession
     * @return  spot details page which was linked to the deleted spot (delete has been updated)
     * @return login page if user is not connected
     */
    @PostMapping(path="/spot/{spotId}/deleteComment-success/{id}") //todo retirer les sout pour livraison
    public String submitDeleteComment(@ModelAttribute("comment") Comment comment,
                                      @PathVariable("spotId") Long spotId,
                                      @PathVariable("id") Long id,
                                      HttpSession httpSession) {

        if (httpSession.getAttribute( "email" ) != null) {
            /*Optional<Comment> commentData=this.commentRepository.findById( id );
            System.out.println( "com dans methode save delete" );
            System.out.println( commentData );
            if (commentData.isPresent()) {
                Comment oldComment=commentData.get();
                oldComment.setName( (comment.getName()) );
                oldComment.setContent( comment.getContent() );
                System.out.println( oldComment );
                this.commentRepository.delete( oldComment );
            }*/
            this.commentRepository.delete( comment );
            return "redirect:/spotDetails/" + spotId;
        }else {
            return "redirect:/login";
        }
    }


}