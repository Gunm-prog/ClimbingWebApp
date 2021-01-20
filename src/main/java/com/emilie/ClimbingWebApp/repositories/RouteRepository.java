package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query(value="SELECT r FROM Route r WHERE r.name LIKE '%' || :keyword || '%'"
            + "OR r.hauteur LIKE '%' || :keyword || '%'"
            + "OR r.nombre LIKE '%' || :keyword || '%'")
    List<Route> searchRoute(@Param("keyword") String keyword);

    @Override
    Optional<Route> findById(Long id);

    List<Route> findByArea(Area area);


}
