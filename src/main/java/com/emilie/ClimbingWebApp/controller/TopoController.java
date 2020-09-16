package com.emilie.ClimbingWebApp.controller;


import com.emilie.ClimbingWebApp.domain.Topo;
import com.emilie.ClimbingWebApp.repositories.TopoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class TopoController {

    @Autowired
    TopoRepository topoRepository;

    @RequestMapping(path="topodetails/{id}")
    @GetMapping
    public String topodetails(@PathVariable("id") Long id, Model model){
        Optional<Topo> topo= topoRepository.findById( id );
        model.addAttribute( "topo", topo.get() );
        return "topo";
    }

    @PostMapping("/topodetails")
    public String topodetails(@ModelAttribute Topo topo, Model model){
        model.addAttribute( "topo", topo );
        topoRepository.save(topo);
        return "topo";
    }

    @RequestMapping(path="/topo")
    @GetMapping
    public String saveNewTopo(Model model){
        return "topo";
    }

    @PostMapping("/topo")
    public String saveTopo(@ModelAttribute Topo topo, Model model){

        model.addAttribute( "topo", topo );
        topoRepository.save(topo);
        return "topo";
    }







}
