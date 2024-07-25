package moodmate.api.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodmate.api.domain.Counselor;
import moodmate.api.domain.Program;
import moodmate.api.domain.Video;
import moodmate.api.dto.VideoEnrollRequestDTO;
import moodmate.api.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class VideoService {

    private final VideoRepository videoRepository;

    @Transactional
    public Video createVideo(VideoEnrollRequestDTO requestDTO) {

        log.info("[Video Service] create video");

        Video video = Video.builder()
                .title(requestDTO.getTitle())
                .name(requestDTO.getName())
                .url(requestDTO.getUrl())
                .build();

        return videoRepository.save(video);
    }

}
