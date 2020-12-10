package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.Secteur;
import com.emilie.ClimbingWebApp.domain.Spot;
import com.emilie.ClimbingWebApp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecteurRepository extends JpaRepository<Secteur, Long> {

    @Override
    Optional<Secteur> findById(Long id);


    List<Secteur> findByUser(User user);

    List<Secteur> findBySpot(Spot spot);
}
