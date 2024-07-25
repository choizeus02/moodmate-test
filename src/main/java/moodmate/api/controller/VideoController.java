package moodmate.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodmate.api.domain.Program;
import moodmate.api.domain.Video;
import moodmate.api.dto.ProgramEnrollRequestDTO;
import moodmate.api.dto.VideoEnrollRequestDTO;
import moodmate.api.service.ProgramService;
import moodmate.api.service.VideoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
@Slf4j
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/enroll")
    @Operation(summary = "비디오 등록 기능", description = "비디오 관련 API")
    @ApiResponse(responseCode = "200", description = "비디오 등록 성공", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "title", description = "제목", example = "유튜브 제목"),
            @Parameter(name = "name", description = "유튜버 이름", example = "김철수"),
            @Parameter(name = "url", description = "url", example = "."),
    })
    public String enroll(@Valid @RequestBody VideoEnrollRequestDTO requestDTO)
            throws IOException {

        log.info("[Program Controller] enroll");

        Video video = videoService.createVideo(requestDTO);

        return "success to enroll program : " + video.getName();

    }
}
