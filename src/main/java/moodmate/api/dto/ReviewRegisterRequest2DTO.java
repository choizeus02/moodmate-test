package moodmate.api.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moodmate.api.Enum.ReviewType;
import moodmate.api.domain.Program;
import moodmate.api.domain.Video;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRegisterRequest2DTO {


    private Video video;

    private String reviewText;

    private float rating;

    ReviewType reviewType;

}
