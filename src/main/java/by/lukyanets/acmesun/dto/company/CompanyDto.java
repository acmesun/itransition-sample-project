package by.lukyanets.acmesun.dto.company;

import by.lukyanets.acmesun.entity.Bonus;
import by.lukyanets.acmesun.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<Bonus> bonusList;
    private double targetAmount;
    private Date expirationDate;
}
