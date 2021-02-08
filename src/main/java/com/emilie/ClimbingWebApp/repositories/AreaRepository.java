package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {


    @Query(value="SELECT area FROM Area area WHERE area.name LIKE '%' || :keyword || '%'"
            + "OR area.description LIKE '%' || :keyword || '%'")
    public List<Area> searchArea(@Param("keyword") String keyword);

    @Query(value="SELECT DISTINCT s FROM Spot s, Area area, Route r, Pitch p WHERE p.quotation LIKE :quotation")
    public List<Spot> searchByQuotation(@Param("quotation") String quotation);

    @Override
    Optional<Area> findById(Long id);

    List<Area> findByUser(User user);

    List<Area> findBySpot(Spot spot);


}
