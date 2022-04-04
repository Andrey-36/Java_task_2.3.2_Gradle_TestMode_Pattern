package ru.netology.settings;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class RegistrationInfo {
    private String login;
    private String password;
    private String status;
}