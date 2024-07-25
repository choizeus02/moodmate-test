package moodmate.api.security.custom;

import lombok.RequiredArgsConstructor;
import moodmate.api.domain.User;
import moodmate.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * JwtAuthFilter에서 JWT의 유효성을 검증한 이후,
     * JWT에서 추출한 유저 식별자(user 기본키)와 일치하는 User가 데이터베이스에 존재하는지의
     * 여부를 판단하고 존재하면 Spring Security에서 내부적으로 사용되는
     * Auth 객체(UserPasswordAuthenticationToken)를 만들 때 필요한 UserDetails 객체로 반환하는 역할을 한다.
     * @param email the username identifying the user whose data is required.
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저가 없습니다."));

        CustomUserInfoDto customUserInfoDto = new CustomUserInfoDto(user.getId(),user.getNickname(), user.getEmail(), user.getPassword());

        return new CustomUserDetails(customUserInfoDto);
    }
}