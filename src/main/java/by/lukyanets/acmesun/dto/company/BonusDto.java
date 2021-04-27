package by.lukyanets.acmesun.dto.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BonusDto {
    private String bonusName;
    private Integer amount;
    private String description;
}
