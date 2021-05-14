package by.lukyanets.acmesun.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class CampaignSubscriptionEntity {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    private CampaignEntity campaignEntity;

    public CampaignSubscriptionEntity(UserEntity user, CampaignEntity campaignEntity) {
        this.user = user;
        this.campaignEntity = campaignEntity;
    }
}
