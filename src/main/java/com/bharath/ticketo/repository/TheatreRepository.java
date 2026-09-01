package com.bharath.ticketo.repository;

import com.bharath.ticketo.model.Theatre;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    boolean existsByNameAndLocationAndIdNot(@NotBlank(message = "Theatre name is required") String name, @NotBlank(message = "Theatre location is required") String location, Long theatreId);
    boolean existsByNameAndLocation(@NotBlank(message = "Theatre name is required") String name, @NotBlank(message = "Theatre location is required") String location);
    List<Theatre> findByNameContainingIgnoreCase(String name);
}
