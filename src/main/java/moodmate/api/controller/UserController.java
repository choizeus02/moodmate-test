package moodmate.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodmate.api.dto.UserLoginDTO;
import moodmate.api.dto.UserRegisterDTO;
import moodmate.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "회원 로그인 기능", description = "이메일(email), 패스워드로(password)를 이용하여 로그인 시도")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "로그인 실패", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "email", description = "아이디(이메일)", example = "test@naver.com"),
            @Parameter(name = "password", description = "비밀번호", example = "1234"),
    })
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userDTO) {

        log.info("[User Controller] login");
        String status = userService.login(userDTO);

        if(status.equals("user not found") || status.equals("password error")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(status);
        }

        return ResponseEntity.status(HttpStatus.OK).body(status);
    }


    @PostMapping("/register")
    @Operation(summary = "회원 가입 기능", description = "이메일(email), 별칭(nickname), 비밀번포(password)를 이용하여 회원가입")
    @ApiResponse(responseCode = "200", description = "회원 가입 성공", content = @Content(mediaType = "application/json"))
    @Parameters({
            @Parameter(name = "email", description = "아이디(이메일)", example = "user2@naver.com"),
            @Parameter(name = "nickname", description = "별칭", example = "수몽이"),
            @Parameter(name = "password", description = "비밀번호", example = "1234"),
    })
    public String register(@RequestBody UserRegisterDTO dto) {
        log.info("[User Controller] register");

        userService.register(dto);

        return userService.login(new UserLoginDTO(dto.getEmail(), dto.getPassword()));
    }

}
