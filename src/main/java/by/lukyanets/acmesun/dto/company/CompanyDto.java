package by.lukyanets.acmesun.dto.company;

import by.lukyanets.acmesun.dto.image.ImageDto;
import by.lukyanets.acmesun.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    private String companyName;
    private String companyDescription;
    private Subject subject;
    private List<BonusDto> bonusList;
    private Integer targetAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;
    private List<ImageDto> imageList;
}
