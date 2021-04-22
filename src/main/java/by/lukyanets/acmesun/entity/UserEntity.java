package by.lukyanets.acmesun.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table
public class UserEntity {
    @Id
    @GeneratedValue
    private long Id;
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



}
