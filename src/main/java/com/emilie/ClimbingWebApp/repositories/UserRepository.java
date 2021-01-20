package com.emilie.ClimbingWebApp.repositories;

import com.emilie.ClimbingWebApp.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
/*@Transactional*/
public interface UserRepository extends JpaRepository<User, Long> {

   /* @Query(value="SELECT u FROM User u WHERE u.name LIKE '%' || :keyword || '%'"
            + "OR u.email LIKE '%' || :keyword || '%'"
            + "OR u.pseudo LIKE '%' || :keyword || '%'")
    public List<User> search(@Param("keyword") String keyword);*/

    @Override
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByComment(Comment comment);
}

