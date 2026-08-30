package com.bharath.ticketo.repository;

import com.bharath.ticketo.model.Screen;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {
    boolean existsByTheatreId(Long theatreId);
    boolean existsByScreenNumberAndTheatreId(@NotBlank(message = "screen Number is required") Integer screenNumber, Long theatreId);
    boolean existsByScreenNumberAndTheatreIdAndIdNot(@NotBlank(message = "screen Number is required") Integer screenNumber, Long id, Long theatreId);
}
