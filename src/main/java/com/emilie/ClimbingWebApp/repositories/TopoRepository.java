package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.ReservationTopo;
import com.emilie.ClimbingWebApp.domain.Topo;
import com.emilie.ClimbingWebApp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TopoRepository extends JpaRepository<Topo, Long> {

    @Override
    Optional<Topo> findById(Long id);


    Object findAllById(Long id);

    List<Topo> findByUser(User user);

    Optional<Topo> findByReservation(ReservationTopo reservationTopo);

   // List<Topo> findById(ReservationTopo reservationTopo);
}
