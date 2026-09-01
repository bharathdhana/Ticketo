package com.bharath.ticketo.service.impl;

import com.bharath.ticketo.dto.movie.MovieRequest;
import com.bharath.ticketo.dto.movie.MovieResponse;
import com.bharath.ticketo.exception.InvalidBookingException;
import com.bharath.ticketo.exception.ResourceNotFoundException;
import com.bharath.ticketo.model.Movie;
import com.bharath.ticketo.repository.MovieRepository;
import com.bharath.ticketo.repository.ShowRepository;
import com.bharath.ticketo.service.CloudinaryService;
import com.bharath.ticketo.service.MovieService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final CloudinaryService cloudinaryService;
    private final ShowRepository showRepository;

    @Override
    @Transactional
    public MovieResponse addMovie(MovieRequest request) {
        if(movieRepository.existsByTitle(request.getTitle()))
            throw new InvalidBookingException("Movie already exists!");

        Movie savedMovie = Movie.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .language(request.getLanguage())
                .genre(request.getGenre())
                .releaseDate(request.getReleaseDate())
                .rating(request.getRating())
                .status(request.getStatus())
                .posterUrl(request.getPosterUrl())
                .build();
        movieRepository.save(savedMovie);
        return mapToMovieResponse(savedMovie);
    }

    @Override
    @Transactional
    public MovieResponse updateMovie(MovieRequest request, Long id, String poster) throws IOException {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie Not Found!"));

        if (movieRepository.existsByTitleAndIdNot(request.getTitle(), id)) {
            throw new InvalidBookingException("Movie already exists!");
        }

        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setDuration(request.getDuration());
        movie.setLanguage(request.getLanguage());
        movie.setGenre(request.getGenre());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setRating(request.getRating());
        movie.setStatus(request.getStatus());

        if(poster != null && !poster.isEmpty()) {
            String posterUrl = cloudinaryService.uploadImage(poster);
            movie.setPosterUrl(posterUrl);
        }

        Movie updatedMovie = movieRepository.save(movie);
        return mapToMovieResponse(updatedMovie);
    }

    @Override
    public MovieResponse getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie Not Found!"));
        return mapToMovieResponse(movie);
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll().stream().map(this::mapToMovieResponse).toList();
    }

    @Override
    @Transactional
    public String deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie Not Found!"));

        if(showRepository.existsByMovieId(id)) {
            throw new InvalidBookingException("Cannot delete movie shows are associated with it" + movie.getTitle());
        }
        movieRepository.deleteById(id);
        return "Movie has been deleted";
    }

    @Override
    public List<MovieResponse> searchMovie(String title) {
        List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(title);
        return movies.stream().map(this::mapToMovieResponse).toList();
    }

    private MovieResponse mapToMovieResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .duration(movie.getDuration())
                .language(movie.getLanguage())
                .genre(movie.getGenre())
                .releaseDate(movie.getReleaseDate())
                .rating(movie.getRating())
                .status(movie.getStatus())
                .posterUrl(movie.getPosterUrl())
                .build();
    }
}
