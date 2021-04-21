package by.lukyanets.acmesun.repository;

import by.lukyanets.acmesun.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByEmail(String email);

    boolean existsByEmail(String email);
}
