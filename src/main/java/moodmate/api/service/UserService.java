package moodmate.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodmate.api.domain.User;
import moodmate.api.dto.UserLoginDTO;
import moodmate.api.dto.UserRegisterDTO;
import moodmate.api.repository.UserRepository;
import moodmate.api.security.custom.CustomUserInfoDto;
import moodmate.api.security.jwt.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    @Transactional
    public void register(UserRegisterDTO userDTO) {

        log.info("[User Service] register");

        User user = new User(userDTO.getEmail(),userDTO.getNickname(), encoder.encode(userDTO.getPassword()));

        userRepository.save(user);
    }
    public String login(UserLoginDTO userDTO) {
        log.info("[User Service] login");

        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow();

        if(user == null) {
            return "user not found";
        }

        if(!encoder.matches(userDTO.getPassword(), user.getPassword())) {
            return "password error";
        }

        CustomUserInfoDto customUserInfoDto = new CustomUserInfoDto(user.getId(), user.getEmail(), user.getPassword(),user.getNickname());
        return jwtUtil.createAccessToken(customUserInfoDto);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }
}
