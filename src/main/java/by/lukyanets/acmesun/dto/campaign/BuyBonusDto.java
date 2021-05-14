package by.lukyanets.acmesun.dto.campaign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyBonusDto {
    private String bonusName;
    private String userName;
    private Integer quantity;
    private String campaignName;
    private String bonusDescription;
    private Integer investmentAmount;

}
