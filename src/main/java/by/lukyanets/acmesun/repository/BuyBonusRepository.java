package by.lukyanets.acmesun.repository;

import by.lukyanets.acmesun.entity.BuyBonusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyBonusRepository extends JpaRepository<BuyBonusEntity, Long> {
    List<BuyBonusEntity> findBuyBonusEntitiesByUserEmail(String userEmail);
}
