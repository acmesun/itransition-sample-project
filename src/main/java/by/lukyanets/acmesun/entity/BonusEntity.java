package by.lukyanets.acmesun.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bonus_entity")
public class BonusEntity {
    @Id
    @GeneratedValue
    private Long bonusId;
    @Column
    private String bonusName;
    @Column
    private Integer amount;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private CompanyEntity company;
    @OneToMany(mappedBy = "bonus")
    private Set<BuyBonusEntity> boughtBonuses;
}
