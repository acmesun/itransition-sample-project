package by.lukyanets.acmesun.dto.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegistrationDto {
    private String name;
    private String email;
    private String password;
    private String error;
    private String message;
}
