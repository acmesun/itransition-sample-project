package by.lukyanets.acmesun.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class CompanyEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String companyName;
    @Column
    private String companyDescription;
    @Column
    @Enumerated(EnumType.STRING)
    private Subject subject;
    @OneToMany(cascade = ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private List<BonusEntity> bonusList;
    @Column
    private Integer targetAmount;
    @Column
    private Date expirationDate;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UserEntity owner;
    @OneToMany(cascade = ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private List<ImageEntity> imageList;

}
