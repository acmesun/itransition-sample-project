package by.lukyanets.acmesun.dto.campaign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignSubscriptionDto {
    private String campaignName;
    private Integer targetAmount;
    private Date expirationDate;
}
