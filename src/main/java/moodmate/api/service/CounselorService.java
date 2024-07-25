package moodmate.api.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moodmate.api.domain.Counselor;
import moodmate.api.domain.Profile;
import moodmate.api.domain.Program;
import moodmate.api.dto.CounselorRegisterRequestDTO;
import moodmate.api.repository.CounselorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CounselorService {

    private final CounselorRepository counselorRepository;


    @Transactional
    public Counselor createCounselor(CounselorRegisterRequestDTO requestDTO) {

        log.info("[Counselor Service] create counselor");

        Profile profile = requestDTO.getProfile();

        Counselor counselor = Counselor.builder()
                .name(requestDTO.getName())
                .comment(requestDTO.getComment())
                .profile(profile)
                .build();

        return counselorRepository.save(counselor);

    }

    public List<Counselor> findCounselors() {
        return counselorRepository.findAll();
    }

    @Transactional
    public void update(Long id, String name, String comment) {
        log.info("[Counselor Service] update");

        Counselor counselor = counselorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Counselor not found"));
        counselor.setName(name);
        counselor.setComment(comment);
    }

    public Counselor findCounselorsById(Long counselorId) {
        log.info("[Counselor Service] findById");

        return counselorRepository.findById(counselorId)
                .orElseThrow(() -> new RuntimeException("Counselor not found"));

    }

    public List<Counselor> searchByName(String name) {
        log.info("[Counselor Service] find counselor for search");

        return counselorRepository.findByNameContainingIgnoreCase(name);
    }
}
