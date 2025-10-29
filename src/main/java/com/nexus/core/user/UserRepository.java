package com.nexus.core.user;

import com.nexus.core.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.authList WHERE u.userId = :userId")
    Optional<User> findByUserIdWithAuthList(@Param("userId") String userId);
}
