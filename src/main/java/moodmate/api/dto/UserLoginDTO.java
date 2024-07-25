package moodmate.api.dto;


import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginDTO {

    private String email;

    private String password;


    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

}