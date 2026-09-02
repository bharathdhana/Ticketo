package com.bharath.ticketo.repository;

import com.bharath.ticketo.model.Screen;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {
    List<Screen> findByTheatre_Id(Long theatreId);
    boolean existsByTheatre_Id(Long theatreId);
    boolean existsByScreenNumberAndTheatre_Id(@NotBlank(message = "screen Number is required") Integer screenNumber, Long theatreId);
    boolean existsByScreenNumberAndTheatre_IdAndIdNot(@NotBlank(message = "screen Number is required") Integer screenNumber, Long id, Long theatreId);
}
