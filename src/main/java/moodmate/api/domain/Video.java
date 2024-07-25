package moodmate.api.domain;


import jakarta.persistence.*;
import lombok.*;
import moodmate.api.Enum.KeyWord;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long id;

    private String title;

    private String name;

    private String url;


    @Builder
    public Video(Long id, String title, String name, String url) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.url = url;
    }
}

