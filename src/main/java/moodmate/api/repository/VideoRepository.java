package moodmate.api.repository;

import moodmate.api.domain.User;
import moodmate.api.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {

}
