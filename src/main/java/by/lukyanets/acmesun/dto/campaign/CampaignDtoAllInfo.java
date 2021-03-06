package by.lukyanets.acmesun.dto.campaign;

import by.lukyanets.acmesun.dto.image.ImageDto;
import by.lukyanets.acmesun.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDtoAllInfo {
    private Long id;
    private String campaignName;
    private String campaignDescription;
    private Subject subject;
    private List<BonusDto> bonusList;
    private Integer targetAmount;
    private Date expirationDate;
    private String owner;
    private List<ImageDto> imageList;
    private Integer currentAmount;

}
