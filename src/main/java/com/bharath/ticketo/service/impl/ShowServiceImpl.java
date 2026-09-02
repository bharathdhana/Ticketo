package com.bharath.ticketo.service.impl;

import com.bharath.ticketo.dto.show.ShowRequest;
import com.bharath.ticketo.dto.show.ShowResponse;
import com.bharath.ticketo.exception.ResourceNotFoundException;
import com.bharath.ticketo.model.Movie;
import com.bharath.ticketo.model.Screen;
import com.bharath.ticketo.model.Show;
import com.bharath.ticketo.repository.*;
import com.bharath.ticketo.service.ShowService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final ReservationRepository reservationRepository;
    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;
    private final MovieRepository movieRepository;

    @Override
    @Transactional
    public ShowResponse createShow(ShowRequest request) {
        Movie movie =  movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        if(request.getStartTime() == null)
            throw new IllegalArgumentException("Start Time is required");

        if(request.getEndTime() == null)
            throw new IllegalArgumentException("End Time is required");

        if (request.getStartTime().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Show must be scheduled for the future");

        if (!request.getStartTime().isBefore(request.getEndTime()))
            throw new IllegalArgumentException("Start time must be before end time");

        if(showRepository.existsOverlappingShow(request.getScreenId(), request.getEndTime(), request.getStartTime()))
            throw new IllegalArgumentException("Overlapping Start Time and End Time");

        if(request.getTicketPrice() == null || request.getTicketPrice() <= 0)
            throw new IllegalArgumentException("Ticket Price should be greater than 0");

        Show show = Show.builder()
                .movie(movie)
                .screen(screen)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .ticketPrice(request.getTicketPrice())
                .status(request.getStatus())
                .build();
        Show savedShow = showRepository.save(show);
        return mapToShowResponse(savedShow);
    }

    @Override
    public ShowResponse findShowById(Long id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show Not Found"));
        return mapToShowResponse(show);
    }

    @Override
    public List<ShowResponse> findAllShows() {
        return showRepository.findAll().stream().map(this::mapToShowResponse).toList();
    }

    @Override
    public List<ShowResponse> getShowByMovie(Long movieId) {
        if(!movieRepository.existsById(movieId))
            throw new ResourceNotFoundException("Movie Not Found");
        return showRepository.findByMovie_Id(movieId).stream().map(this::mapToShowResponse).toList();
    }

    @Override
    public List<ShowResponse> getShowByTheatre(Long theatreId) {
        if(!theatreRepository.existsById(theatreId))
            throw new ResourceNotFoundException("Theatre Not Found");
        return showRepository.findByScreenTheatre_Id(theatreId).stream().map(this::mapToShowResponse).toList();
    }

    @Override
    public List<ShowResponse> getShowByDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        List<Show> shows = showRepository.findByStartTimeBetween(start, end);
        return shows.stream().map(this::mapToShowResponse).toList();
    }

    @Override
    @Transactional
    public ShowResponse updateShow(Long id, ShowRequest request) {
        Show show =  showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show Not Found"));

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie Not Found"));

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() -> new ResourceNotFoundException("Screen Not Found"));

        if(request.getStartTime() == null)
            throw new IllegalArgumentException("Start Time is required");

        if(request.getEndTime() == null)
            throw new IllegalArgumentException("End Time is required");

        if (request.getStartTime().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Show must be scheduled for the future");

        if (!request.getStartTime().isBefore(request.getEndTime()))
            throw new IllegalArgumentException("Start time must be before end time");

        if(showRepository.existsOverlappingShowForUpdate(id, request.getStartTime(), request.getEndTime(), request.getScreenId()))
            throw new IllegalArgumentException("Overlapping Start Time and End Time");

        if(request.getTicketPrice() == null || request.getTicketPrice() <= 0)
            throw new IllegalArgumentException("Ticket Price should be greater than 0");

        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(request.getStartTime());
        show.setEndTime(request.getEndTime());
        show.setTicketPrice(request.getTicketPrice());
        show.setStatus(request.getStatus());
        Show updateShow = showRepository.save(show);
        return mapToShowResponse(updateShow);
    }

    @Override
    @Transactional
    public String deleteShow(Long id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Show Not Found"));
        if(reservationRepository.existsByShowId(id))
            throw new IllegalArgumentException("Cannot delete show because reservation exists");

        showRepository.delete(show);
        return "Show Deleted Successfully";
    }

    private ShowResponse mapToShowResponse(Show show) {
        return ShowResponse.builder()
                .id(show.getId())
                .movieId(show.getMovie().getId())
                .screenId(show.getScreen().getId())
                .startTime(show.getStartTime())
                .endTime(show.getEndTime())
                .ticketPrice(show.getTicketPrice())
                .status(show.getStatus())
                .build();
    }
}
