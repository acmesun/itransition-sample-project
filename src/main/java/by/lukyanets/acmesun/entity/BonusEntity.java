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
}
