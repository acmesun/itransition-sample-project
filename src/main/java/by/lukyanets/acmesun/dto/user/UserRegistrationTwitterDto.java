package by.lukyanets.acmesun.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegistrationTwitterDto {
    private String name;
    private String email;
    private String error;
    private String message;
}