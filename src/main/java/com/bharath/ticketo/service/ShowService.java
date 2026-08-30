package com.bharath.ticketo.service;

import com.bharath.ticketo.dto.show.ShowRequest;
import com.bharath.ticketo.dto.show.ShowResponse;

import java.time.LocalDate;
import java.util.List;

public interface ShowService {
    ShowResponse createShow(ShowRequest request);
    ShowResponse findShowById(long id);
    List<ShowResponse> findAllShows();
    List<ShowResponse> getShowByMovie(Long movieId);
    List<ShowResponse> getShowByTheatre(Long theatreId);
    List<ShowResponse> getShowByDate(LocalDate date);
    ShowResponse updateShow(Long id, ShowRequest request);
    String deleteShow(Long id);
}
