package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.Pitch;
import com.emilie.ClimbingWebApp.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PitchRepository extends JpaRepository<Pitch, Long> {

    @Query(value="SELECT p FROM Pitch p WHERE p.distance LIKE '%' || :keyword || '%' "
            + "OR p.points LIKE '%' || :keyword || '%'"
            + "OR p.quotation LIKE '%' || :keyword || '%'")
    List<Pitch> searchPitch(@Param( "keyword" ) String keyword);

    @Override
    Optional<Pitch> findById(Long id);

    List<Pitch> findByRoute(Route route);


}

