package moodmate.api.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodmate.api.domain.Program;
import moodmate.api.domain.Review;
import moodmate.api.domain.Video;
import moodmate.api.dto.ReviewRegisterRequest2DTO;
import moodmate.api.dto.ReviewRegisterRequestDTO;
import moodmate.api.repository.ProgramRepository;
import moodmate.api.repository.ReviewRepository;
import moodmate.api.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ProgramRepository programRepository;
    private final ReviewRepository reviewRepository;
    private final VideoRepository videoRepository;

    @Transactional
    public Review addReview(Long programId, ReviewRegisterRequestDTO requestDTO) {

        log.info("[Review Service] add program Review");

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found"));


        Review review = Review.builder()
                .program(program)
                .reviewText(requestDTO.getReviewText())
                .rating(requestDTO.getRating())
                .createdAt(LocalDateTime.now())
                .reviewType(requestDTO.getReviewType())
                .build();

        return reviewRepository.save(review);
    }

    @Transactional
    public Review addReview2(Long videoId, ReviewRegisterRequest2DTO requestDTO) {

        log.info("[Review Service] add video Review");

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        Review review = Review.builder()
                .video(video)
                .reviewText(requestDTO.getReviewText())
                .rating(requestDTO.getRating())
                .createdAt(LocalDateTime.now())
                .reviewType(requestDTO.getReviewType())
                .build();

        return reviewRepository.save(review);
    }

}
