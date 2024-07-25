package moodmate.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moodmate.api.domain.Profile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CounselorRegisterRequestDTO {

    private String name;

    private String comment;

    private Profile profile;


    @Builder
    public CounselorRegisterRequestDTO(String name, String comment, Profile profile) {
        this.name = name;
        this.comment = comment;
        this.profile = profile;
    }
}
