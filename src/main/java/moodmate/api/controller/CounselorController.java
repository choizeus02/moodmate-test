package moodmate.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodmate.api.domain.Counselor;
import moodmate.api.domain.Profile;
import moodmate.api.domain.Program;
import moodmate.api.dto.CounselorRegisterRequestDTO;
import moodmate.api.repository.CounselorRepository;
import moodmate.api.repository.UserRepository;
import moodmate.api.service.CounselorService;
import moodmate.api.service.ProgramService;
import moodmate.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/counselor")
@RequiredArgsConstructor
@Slf4j
public class CounselorController {

    private final ProgramService programService;
    private final CounselorService counselorService;
    private final CounselorRepository counselorRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * counselor 등록
     * @param requestDTO
     * @return
     * @throws IOException
     */

    @PostMapping("/register")
    @Operation(summary = "상담사 등록 기능", description = "상담사 관련 API")
    @ApiResponse(responseCode = "200", description = "상담사 등록 성공", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "name", description = "이름", example = "김철수"),
            @Parameter(name = "comment", description = "소개", example = "안녕하세요 1타 강사 김철수입니다.~"),
            @Parameter(name = "profile", description = "약력", example = "~~"),
    })
    public String register(@RequestBody @Valid CounselorRegisterRequestDTO requestDTO)
            throws IOException {

        log.info("[Counselor Controller] register");


        Counselor counselor = counselorService.createCounselor(requestDTO);

        return "success to register Counselor : " + counselor.getName();
    }


    /**
     * counselor 수정
     * @param counselorId
     * @param request
     * @return
     */
    @PutMapping("/{counselorId}")
    @Operation(summary = "상담사 수정 기능", description = "상담사 관련 API")
    @ApiResponse(responseCode = "200", description = "상담사 수정 성공", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "name", description = "이름", example = "김철수"),
            @Parameter(name = "comment", description = "소개", example = "안녕하세요 1타 강사 김철수입니다.~"),
            @Parameter(name = "profile", description = "약력", example = "~~"),
    })
    public UpdateCounselorResponse updateMember(@PathVariable("counselorId") Long counselorId, @RequestBody @Valid UpdateMemberRequest request) {
        log.info("[Counselor Controller] counselor update");

        counselorService.update(counselorId, request.getName(), request.getComment());
        Counselor findCounselor = counselorRepository.findById(counselorId)
                .orElseThrow(() -> new RuntimeException("Counselor not found"));
        return new UpdateCounselorResponse(findCounselor.getId(), findCounselor.getName(), findCounselor.getComment());

    }


//    /**
//     * counselor 조회
//     * @return
//     */
//    @GetMapping("/")
//    @Operation(summary = "상담사 조회 기능", description = "상담사 관련 API")
//    @ApiResponse(responseCode = "200", description = "상담사 조회 성공", content = @Content(mediaType = "application/json"))
//    @Parameters({
//            @Parameter(name = "name", description = "이름", example = "김철수"),
//            @Parameter(name = "comment", description = "소개", example = "안녕하세요 1타 강사 김철수입니다.~"),
//            @Parameter(name = "profile", description = "약력", example = "~~"),
//    })
//    public Result findCounselor() {
//
//        log.info("[Counselor Controller] findAll");
//
//
//        List<Counselor> findCounselor = counselorService.findCounselors();
//        List<CounselorDto> collect = findCounselor.stream()
//                .map(m -> new CounselorDto(m.getName()))
//                .collect(Collectors.toList());
//
//        return new Result(collect);
//
//    }

    @GetMapping("/{counselorId}")
    @Operation(summary = "상담사 상세 조회 기능", description = "상담사 관련 API")
    @ApiResponse(responseCode = "200", description = "상담사 조회 성공", content = @Content(mediaType = "application/json"))
    public Result<CounselorDetailsDto> CounselorDetails(@PathVariable("counselorId") Long counselorId) {

        log.info("[Counselor Controller] Details");


        Counselor counselor = counselorService.findCounselorsById(counselorId);
        CounselorDetailsDto counselorDetailsDto = new CounselorDetailsDto(
                counselor.getName(),
                counselor.getComment(),
                counselor.getProfile()
        );

        return new Result(counselorDetailsDto);
    }



    @GetMapping("/search")
    @Operation(summary = "상담사 검색 기능", description = "상담사 관련 API ")
    @ApiResponse(responseCode = "200", description = "상담사 검색 성공", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "name", description = "검색할 상담사 이름", example = "김철수")
    })
    public ResponseEntity<Result<List<CounselorDto>>> searchCounselor(@RequestParam(value = "name", required = false) String name) {

        log.info("[Counselot Controller] search " + name);

        List<Counselor> counselor = counselorService.searchByName(name);

        List<CounselorDto> collect = counselor.stream()
                .map(m -> new CounselorDto(m.getName(),m.getComment()))
                .collect((Collectors.toList()));


        Result<List<CounselorDto>> result = new Result<>(collect);

        return ResponseEntity.ok(result);
    }

    @Data
    @AllArgsConstructor
    static class CounselorDto {
        private String name;
        private String comment;
    }

    @Data
    @AllArgsConstructor
    static class CounselorDetailsDto {
        private String name;
        private String comment;
        private Profile profile;
    }




    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class UpdateCounselorResponse {
        private Long id;
        private String name;
        private String comment;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
        private String comment;
        private Profile profile;
    }



}
