package com.bharath.ticketo.dto.movie;

import com.bharath.ticketo.model.enums.MovieStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieRequest {
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "duration is required")
    @Min(1)
    private Integer duration;

    @NotBlank(message = "language is required")
    private String language;

    @NotBlank(message = "genre is required")
    private String genre;

    @NotBlank(message = "release date is required")
    private LocalDate releaseDate;

    @NotBlank(message = "rating is required")
    private Double rating;

    @NotBlank(message = "status is required")
    private MovieStatus status;

    private MultipartFile posterUrl;
}
