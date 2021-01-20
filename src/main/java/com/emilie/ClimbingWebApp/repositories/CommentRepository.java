package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Override
    Optional<Comment> findById(Long id);

    List<Comment> findAll();

    List<Comment> findBySpot(Spot spot);

}
