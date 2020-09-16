package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.ReservationTopo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ReservationTopoRepository extends JpaRepository<ReservationTopo,Long>{

    @Override
    Optional<ReservationTopo> findById(Long id);

}