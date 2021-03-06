package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TopoRepository extends JpaRepository<Topo, Long> {

    @Query(value="SELECT t FROM Topo t WHERE t.title LIKE '%' || :keyword || '%'"
            + "OR t.author LIKE '%' || :keyword || '%'")
    public List<Topo> searchTopo(@Param("keyword") String keyword);

    @Override
    Optional<Topo> findById(Long id);

    List<Topo> findByUser(User user);

    Optional<Topo> findByBooking(TopoBooking topoBooking);

}
