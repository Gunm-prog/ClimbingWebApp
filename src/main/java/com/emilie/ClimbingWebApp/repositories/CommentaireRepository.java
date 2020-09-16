package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    @Override
    Optional<Commentaire> findById(Long id);
}
