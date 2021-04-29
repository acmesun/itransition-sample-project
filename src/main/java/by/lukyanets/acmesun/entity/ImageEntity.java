package by.lukyanets.acmesun.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class ImageEntity {
    @Id
    @GeneratedValue
    private Long imageId;
    @Column
    private String title;
    @Column
    private String url;
}
