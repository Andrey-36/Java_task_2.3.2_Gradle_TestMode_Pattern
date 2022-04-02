package ru.netology.Settings;

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