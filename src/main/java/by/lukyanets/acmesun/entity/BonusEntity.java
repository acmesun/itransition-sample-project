package by.lukyanets.acmesun.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
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
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    private CampaignEntity campaign;
    @OneToMany(mappedBy = "bonus")
    private Set<BuyBonusEntity> boughtBonuses;
}
