package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.Voie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoieRepository extends JpaRepository<Voie, Long> {

    @Override
    Optional<Voie> findById(Long id);
}
