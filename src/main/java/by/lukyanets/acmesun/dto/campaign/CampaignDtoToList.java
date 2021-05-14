package by.lukyanets.acmesun.dto.campaign;

import by.lukyanets.acmesun.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDtoToList {
    private Long id;
    private String campaignName;
    private Subject subject;
    private Integer targetAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;
    private String owner;
}
