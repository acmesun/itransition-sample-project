package by.lukyanets.acmesun.repository;

import by.lukyanets.acmesun.dto.UserAdminDto;
import by.lukyanets.acmesun.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByEmail(String email);

    boolean existsByEmail(String email);

    List<UserEntity> findAllByOrderByNameAsc();
}
