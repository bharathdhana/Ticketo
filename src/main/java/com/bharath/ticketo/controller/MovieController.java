package com.bharath.ticketo.controller;

import com.bharath.ticketo.dto.movie.MovieRequest;
import com.bharath.ticketo.dto.movie.MovieResponse;
import com.bharath.ticketo.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieResponse> addMovie(@RequestBody @Valid MovieRequest request) {
        MovieResponse response = movieService.addMovie(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieResponse> updateMovie(@Valid @RequestBody MovieRequest request, @PathVariable Long id) throws IOException {
        MovieResponse response = movieService.updateMovie(request, id, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
        MovieResponse response = movieService.getMovieById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        List<MovieResponse> response = movieService.getAllMovies();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        String response = movieService.deleteMovie(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/title/{keyword}")
    public ResponseEntity<List<MovieResponse>> searchMovie(@PathVariable String keyword) {
        List<MovieResponse> response = movieService.searchMovie(keyword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
