package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


@Repository
public interface TopoBookingRepository extends JpaRepository<TopoBooking, Long> {


    @Override
    Optional<TopoBooking> findById(Long id);

    Set<TopoBooking> findByUser(User user);

    Set<TopoBooking> findByTopo(Topo topo);

    void deleteById(Long id);

}

