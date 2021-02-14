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

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    SpotRepository spotRepository;


    @GetMapping(path="/spot/{spotId}/comment/{id}/updateComment")
    public String updateComment(@PathVariable("id") Long id,
                                @PathVariable("spotId") Long spotId,
                                HttpSession httpSession,
                                Model model) {

        if (httpSession.getAttribute( "email" ) != null) {

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
        } else {
            return "redirect:/login";
        }

    }


    @PostMapping(path="/spot/{spotId}/updateComment/{comId}/success")
    public String submitUpdateComment(@ModelAttribute("comment") Comment comment,
                                      @PathVariable("spotId") Long spotId,
                                      @PathVariable("comId") Long comId,
                                      HttpSession httpSession) {


        if (httpSession.getAttribute( "email" ) != null) {
            Optional<Comment> commentData=this.commentRepository.findById( comId );
            if (commentData.isPresent()) {
                Comment oldComment=commentData.get();
                oldComment.setName( comment.getName() );
                oldComment.setContent( comment.getContent() );
                this.commentRepository.save( oldComment );
            }
            return "redirect:/spotDetails/" + spotId;
        } else {
            return "redirect:/login";
        }
    }


    @PostMapping(path="/spot/{spotId}/deleteComment-success/{id}")
    public String submitDeleteComment(@ModelAttribute("comment") Comment comment,
                                      @PathVariable("spotId") Long spotId,
                                      @PathVariable("id") Long id,
                                      HttpSession httpSession) {

        if (httpSession.getAttribute( "email" ) != null) {
            this.commentRepository.delete( comment );
            return "redirect:/spotDetails/" + spotId;
        } else {
            return "redirect:/login";
        }
    }
}