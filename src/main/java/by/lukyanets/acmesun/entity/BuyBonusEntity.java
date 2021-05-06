package by.lukyanets.acmesun.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class BuyBonusEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String companyName;
    @Column
    private String bonusName;
    @Column
    private Integer amount;

}
