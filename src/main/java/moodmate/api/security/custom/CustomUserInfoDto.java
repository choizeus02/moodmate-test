package moodmate.api.security.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomUserInfoDto {
    private Long userId;

    private String email;

    private String password;

    private String nickname;
}