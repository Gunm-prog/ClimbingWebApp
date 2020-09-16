package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.Topo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TopoRepository extends JpaRepository<Topo, Long> {

    @Override
    Optional<Topo> findById(Long id);
}
