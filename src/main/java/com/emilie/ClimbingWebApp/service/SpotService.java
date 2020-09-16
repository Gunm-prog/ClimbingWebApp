package com.emilie.ClimbingWebApp.service;

import com.emilie.ClimbingWebApp.domain.Spot;
import com.emilie.ClimbingWebApp.repositories.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class SpotService {

    @Autowired
    SpotRepository spotRepository;

    public void save(Spot spot){
        spotRepository.save(spot);
    }


    public List<Spot> listAll(){
        return (List<Spot>) spotRepository.findAll();
    }

    public Spot get(Long id){
        return spotRepository.findById( id ).get();
    }


    public void delete(Long id){
        spotRepository.deleteById(id);}


}


