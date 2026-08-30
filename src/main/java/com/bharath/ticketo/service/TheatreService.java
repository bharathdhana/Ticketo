package com.bharath.ticketo.service;

import com.bharath.ticketo.dto.theatre.TheatreRequest;
import com.bharath.ticketo.dto.theatre.TheatreResponse;

import java.util.List;

public interface TheatreService {
    TheatreResponse createTheatre(TheatreRequest request);
    TheatreResponse updateTheatre(TheatreRequest request, Long theatreId);
    String deleteTheatre(Long theatreId);
    TheatreResponse getTheatreByName(String name);
    List<TheatreResponse> getAllTheatres();
}
