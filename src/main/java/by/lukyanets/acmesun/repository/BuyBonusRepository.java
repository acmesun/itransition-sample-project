package by.lukyanets.acmesun.repository;

import by.lukyanets.acmesun.entity.BuyBonusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuyBonusRepository extends JpaRepository<BuyBonusEntity, Long> {
    List<BuyBonusEntity> findBuyBonusEntitiesByUserEmail(String userEmail);
}
