package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.Secteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecteurRepository extends JpaRepository<Secteur, Long> {

    @Override
    Optional<Secteur> findById(Long id);
}
