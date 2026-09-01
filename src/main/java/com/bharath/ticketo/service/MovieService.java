package com.bharath.ticketo.service;

import com.bharath.ticketo.dto.movie.MovieRequest;
import com.bharath.ticketo.dto.movie.MovieResponse;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    MovieResponse addMovie(MovieRequest request);
    MovieResponse updateMovie(MovieRequest request, Long id, String file) throws IOException;
    MovieResponse getMovieById(Long id);
    List<MovieResponse> getAllMovies();
    String deleteMovie(Long id);
    List<MovieResponse> searchMovie(String title);

}
