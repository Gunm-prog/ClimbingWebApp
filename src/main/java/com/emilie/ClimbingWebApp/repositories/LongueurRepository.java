package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.Longueur;
import com.emilie.ClimbingWebApp.domain.Voie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LongueurRepository extends JpaRepository<Longueur, Long> {

    /* @Query
     public List<Longueur> searchLongueur(@Param("keyword") String keyword);*/
    @Override
    Optional<Longueur> findById(Long id);

    List<Longueur> findByVoie(Voie voie);


}

