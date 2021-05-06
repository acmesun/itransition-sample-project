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
@Table
public class ImageEntity {
    @Id
    @GeneratedValue
    private Long imageId;
    @Column
    private String title;
    @Column
    private String url;

    public ImageEntity(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
