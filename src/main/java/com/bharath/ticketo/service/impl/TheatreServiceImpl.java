package com.bharath.ticketo.service.impl;

import com.bharath.ticketo.dto.theatre.TheatreRequest;
import com.bharath.ticketo.dto.theatre.TheatreResponse;
import com.bharath.ticketo.exception.ResourceNotFoundException;
import com.bharath.ticketo.model.Theatre;
import com.bharath.ticketo.model.enums.TheatreStatus;
import com.bharath.ticketo.repository.ScreenRepository;
import com.bharath.ticketo.repository.TheatreRepository;
import com.bharath.ticketo.service.TheatreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheatreServiceImpl implements TheatreService {

    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;

    @Transactional
    @Override
    public TheatreResponse createTheatre(TheatreRequest request) {
        if(request.getName() == null || request.getName().isEmpty())
            throw new IllegalArgumentException("Theatre name is required");

        if(request.getLocation() == null || request.getLocation().isEmpty())
            throw new IllegalArgumentException("Theatre location is required");

        if(theatreRepository.existsByNameAndLocation(request.getName(), request.getLocation())) {
            throw new ResourceNotFoundException("Theatre already exists");
        }

        Theatre newTheatre = Theatre.builder()
                .name(request.getName())
                .location(request.getLocation())
                .city(request.getCity())
                .status(TheatreStatus.ACTIVE)
                .build();
        theatreRepository.save(newTheatre);
        return mapToTheatreResponse(newTheatre);
    }

    @Override
    @Transactional
    public TheatreResponse updateTheatre(TheatreRequest request, Long theatreId) {
        Theatre theatre =  theatreRepository.findById(theatreId)
                .orElseThrow(() -> new  ResourceNotFoundException("Theatre not found"));

        if(request.getName() == null || request.getName().isEmpty())
            throw new IllegalArgumentException("Theatre name is required");

        if(request.getLocation() == null || request.getLocation().isEmpty())
            throw new IllegalArgumentException("Theatre location is required");

        boolean duplicate = theatreRepository.existsByNameAndLocationAndIdNot(request.getName(), request.getLocation(), theatreId);
        if(duplicate)
            throw new ResourceNotFoundException("Theatre already exists");

        theatre.setName(request.getName());
        theatre.setLocation(request.getLocation());
        theatre.setCity(request.getCity());
        theatre.setStatus(TheatreStatus.ACTIVE);
        Theatre updatedTheatre =  theatreRepository.save(theatre);
        return mapToTheatreResponse(updatedTheatre);
    }

    @Override
    @Transactional
    public String deleteTheatre(Long theatreId) {
        Theatre theatre =  theatreRepository.findById(theatreId)
                .orElseThrow(() -> new  ResourceNotFoundException("Theatre not found"));
        boolean hasScreens = screenRepository.existsByTheatreId(theatreId);
        if(hasScreens) {
            theatre.setStatus(TheatreStatus.INACTIVE);
            theatreRepository.save(theatre);
            return "Theatre Disabled because screens are associated with it";
        }

        theatreRepository.delete(theatre);
        return "Theatre Deleted Successfully";
    }

    @Override
    public List<TheatreResponse> getTheatreByName(String name) {
       return theatreRepository.findByNameContainingIgnoreCase(name).stream().map(this::mapToTheatreResponse).toList();
    }

    @Override
    public List<TheatreResponse> getAllTheatres() {
        return theatreRepository.findAll().stream().map(this::mapToTheatreResponse).toList();
    }

    private TheatreResponse mapToTheatreResponse(Theatre theatre) {
        return TheatreResponse.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .location(theatre.getLocation())
                .city(theatre.getCity())
                .status(theatre.getStatus())
                .build();
    }
}
