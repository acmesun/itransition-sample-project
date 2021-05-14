package by.lukyanets.acmesun.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Transient
    private String matchingPassword;
    @Column
    private String role;
    @Column
    private boolean activity;
    @OneToMany(mappedBy = "owner")
    private List<CampaignEntity> campaigns;
    @OneToMany(mappedBy = "user")
    private Set<BuyBonusEntity> boughtBonuses;
    @OneToMany(mappedBy = "user")
    private Set<CampaignSubscriptionEntity> subscriptions;
}
