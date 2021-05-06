package by.lukyanets.acmesun.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class BuyBonusEntity {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "bonus_id", referencedColumnName = "bonusId")
    private BonusEntity bonus;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    @Column
    private Integer quantity;

    public BuyBonusEntity(BonusEntity bonus, UserEntity currentUser) {
        this.bonus = bonus;
        this.user = currentUser;
        this.quantity = 0;
    }
}
