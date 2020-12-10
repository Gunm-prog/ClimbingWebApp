package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.Spot;
import com.emilie.ClimbingWebApp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface SpotRepository extends JpaRepository<Spot, Long> {

    @Override
    Optional<Spot> findById(Long id);

    /*@Override
    public List<Spot> findAllSpots() {return null;}*/



   // void updateById(Long id);

    //void update(Spot spot);

    void deleteById(Long id);

    List<Spot> findAllById(Long id);

    List<Spot> findByUser(User user);
}