package moodmate.api.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moodmate.api.Enum.ReviewType;
import moodmate.api.domain.Program;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRegisterRequestDTO {

    private Program program;

    private String reviewText;

    private float rating;

    ReviewType reviewType;

}
