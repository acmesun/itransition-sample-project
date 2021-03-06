package by.lukyanets.acmesun.dto.campaign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BonusDto {
    private String bonusName;
    private Integer amount;
    private String description;
}
