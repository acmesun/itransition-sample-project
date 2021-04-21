package by.lukyanets.acmesun.entity;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Roles {
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String CLIENT = "ROLE_CLIENT";
}
