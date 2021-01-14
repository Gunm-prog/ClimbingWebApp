package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.Commentaire;
import com.emilie.ClimbingWebApp.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    @Override
    Optional<Commentaire> findById(Long id);

    List<Commentaire> findAll();

    List<Commentaire> findBySpot(Spot spot);

}
