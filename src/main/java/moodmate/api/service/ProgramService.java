package moodmate.api.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodmate.api.controller.ProgramController;
import moodmate.api.domain.Counselor;
import moodmate.api.domain.Program;
import moodmate.api.domain.Review;
import moodmate.api.dto.ProgramEnrollRequestDTO;
import moodmate.api.repository.CounselorRepository;
import moodmate.api.repository.ProgramRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProgramService {


    private final CounselorRepository counselorRepository;
    private final ProgramRepository programRepository;

    public Program findProgram(Long programId) {

        log.info("[Program Service] find program");


        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        return program;
    }


    @Transactional
    public Program createProgram(ProgramEnrollRequestDTO requestDTO) throws IOException {

        log.info("[Program Service] create program");


        Counselor counselor = counselorRepository.findById(requestDTO.getCounselor_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid counselor ID"));

        Program program = Program.builder()
                .name(requestDTO.getName())
                .counselor(counselor)
                .time(requestDTO.getTime())
                .place(requestDTO.getPlace())
                .details(requestDTO.getDetails())
                .keyWord(requestDTO.getKeyWord())
                .build();


        return programRepository.save(program);
    }


    public List<Program> searchByName(String name) {
        log.info("[Program Service] find program for search");

        return programRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional
    public void updateRating(Long programId) {
        log.info("[Program Service] update rating");

        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid counselor ID"));


        List<Review> reviews = program.getReviews();
        if (reviews.isEmpty()) {
            log.warn("No reviews found for program ID: {}", programId);
            return;
        }

        double averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        float roundedRating = Math.round(averageRating * 10) / 10.0f;

        program.setRating(roundedRating);
        System.out.println(program.getRating());
        programRepository.save(program);

        log.info("Updated program rating to {}", averageRating);
    }
}
