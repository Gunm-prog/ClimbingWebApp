package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
/*@Transactional*/
public interface SpotRepository extends JpaRepository<Spot, Long> {

    @Query(value="SELECT s FROM Spot s WHERE s.name LIKE '%' || :keyword || '%'"
            + "OR s.description LIKE '%' || :keyword || '%'")
    public List<Spot> searchSpot(@Param("keyword") String keyword);

    @Query(value="SELECT DISTINCT s FROM Spot s JOIN Area area ON s.id = area.spot.id JOIN Route r ON area.id = r.area.id JOIN Pitch p ON r.id = p.route.id WHERE p.quotation = :quotation")
    public List<Spot> searchByQuotation(@Param("quotation") String quotation);

    @Override
    Optional<Spot> findById(Long id);

    void deleteById(Long id);

    List<Spot> findAllById(Long id);

    List<Spot> findByUser(User user);

    @Query(value="SELECT DISTINCT s FROM Spot s " +
            "JOIN Area area ON s.id = area.spot.id " +
            "JOIN Route r ON area.id = r.area.id " +
            "JOIN Pitch p ON r.id = p.route.id " +
            "WHERE p.quotation = :quotation " +
            "AND " +
            " (s.name LIKE '%' || :keyword || '%' OR s.description LIKE '%' || :keyword || '%')")
    public List<Spot> searchByQuotationAndKeyword(@Param("quotation") String quotation, @Param("keyword") String keyword);
}