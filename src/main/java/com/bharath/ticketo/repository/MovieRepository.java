package com.bharath.ticketo.repository;

import com.bharath.ticketo.model.Movie;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByTitle(@NotBlank(message = "title is required") String title);
    boolean existsByTitleAndIdNot(@NotBlank(message = "title is required") String title, Long id);
    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
