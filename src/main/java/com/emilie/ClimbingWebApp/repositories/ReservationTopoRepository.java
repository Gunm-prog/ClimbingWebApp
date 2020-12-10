package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.ReservationTopo;
import com.emilie.ClimbingWebApp.domain.Topo;
import com.emilie.ClimbingWebApp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ReservationTopoRepository extends JpaRepository<ReservationTopo,Long>{


    @Override
    Optional<ReservationTopo> findById(Long id);

    Set<ReservationTopo> findByUser(User user);

    Set<ReservationTopo> findByTopo(Topo topo);

    Optional<ReservationTopo> findByUser(String name);

    Optional<ReservationTopo> findByUser(ReservationTopo reservationTopo);

    void deleteById(Long id);


    // public List<ReservationTopo> findUserById(Long userId);
}