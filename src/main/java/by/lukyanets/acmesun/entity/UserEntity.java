package by.lukyanets.acmesun.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class UserEntity {
    @Id
    private Long Id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;

}
