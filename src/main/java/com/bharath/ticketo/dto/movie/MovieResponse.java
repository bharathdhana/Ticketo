package com.bharath.ticketo.dto.movie;

import com.bharath.ticketo.model.enums.MovieStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponse {
    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private String language;
    private String genre;
    private LocalDate releaseDate;
    private Double rating;
    private MovieStatus status;
    private String posterUrl;
}
