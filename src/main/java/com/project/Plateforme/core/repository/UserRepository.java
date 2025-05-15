package com.project.Plateforme.core.repository;
import com.project.Plateforme.core.bo.User;
import com.project.Plateforme.core.bo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
    List<User> findByRole(Role role);

}
