package moodmate.api.repository;

import moodmate.api.domain.Counselor;
import moodmate.api.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CounselorRepository extends JpaRepository<Counselor, Long> {
    List<Counselor> findByNameContainingIgnoreCase(String name);

}
