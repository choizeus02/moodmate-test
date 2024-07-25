package moodmate.api.repository;


import moodmate.api.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findByNameContainingIgnoreCase(String name);

}
