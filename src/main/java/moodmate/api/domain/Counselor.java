package moodmate.api.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Counselor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Counselor_id")
    private Long id;

    private String name;

    private String comment;

    @Embedded
    private Profile profile;


    @Builder
    public Counselor(Long id, String name, String comment, Profile profile) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.profile = profile;
    }


}
