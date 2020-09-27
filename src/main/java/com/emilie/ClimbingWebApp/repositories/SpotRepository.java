package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Repository
@Transactional
public interface SpotRepository extends JpaRepository<Spot, Long> {

    @Override
    Optional<Spot> findById(Long id);

    void update(Spot spot);

    void deleteById(Long id);

}