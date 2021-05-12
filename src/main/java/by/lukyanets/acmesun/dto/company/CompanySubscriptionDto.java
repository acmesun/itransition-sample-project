package by.lukyanets.acmesun.dto.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanySubscriptionDto {
    private String companyName;
    private Integer targetAmount;
    private Date expirationDate;
}
