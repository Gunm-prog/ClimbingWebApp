package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.Set;


@Repository
public interface ReservationTopoRepository extends JpaRepository<ReservationTopo, Long> {


    @Override
    Optional<ReservationTopo> findById(Long id);

    Set<ReservationTopo> findByUser(User user);

    Set<ReservationTopo> findByTopo(Topo topo);

    void deleteById(Long id);

}