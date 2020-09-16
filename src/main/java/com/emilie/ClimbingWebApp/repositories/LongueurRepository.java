package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.Longueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LongueurRepository extends JpaRepository<Longueur, Long> {

    @Override
    Optional<Longueur> findById(Long id);
}
