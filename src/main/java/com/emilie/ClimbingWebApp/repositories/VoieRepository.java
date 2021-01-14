package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.Secteur;
import com.emilie.ClimbingWebApp.domain.Voie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoieRepository extends JpaRepository<Voie, Long> {

    @Query(value="SELECT v FROM Voie v WHERE v.name LIKE '%' || :keyword || '%'"
            + "OR v.hauteur LIKE '%' || :keyword || '%'"
            + "OR v.nombre LIKE '%' || :keyword || '%'")
    List<Voie> searchVoie(@Param("keyword") String keyword);

    @Override
    Optional<Voie> findById(Long id);

    List<Voie> findBySecteur(Secteur secteur);
}
