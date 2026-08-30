package com.bharath.ticketo.service.impl;

import com.bharath.ticketo.dto.screen.ScreenRequest;
import com.bharath.ticketo.dto.screen.ScreenResponse;
import com.bharath.ticketo.exception.ResourceNotFoundException;
import com.bharath.ticketo.model.Screen;
import com.bharath.ticketo.model.Theatre;
import com.bharath.ticketo.repository.ScreenRepository;
import com.bharath.ticketo.repository.ShowRepository;
import com.bharath.ticketo.repository.TheatreRepository;
import com.bharath.ticketo.service.ScreenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;
    private final ShowRepository showRepository;

    @Override
    @Transactional
    public ScreenResponse createScreen(Long theatreId, ScreenRequest request) {
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new ResourceNotFoundException("theatre not found"));

        if(screenRepository.existsByScreenNumberAndTheatreId(request.getScreenNumber(), theatreId))
            throw new RuntimeException("Screen already exists in this theatre");

        Screen screen = Screen.builder()
                .screenNumber(request.getScreenNumber())
                .capacity(request.getCapacity())
                .theatre(theatre)
                .build();
        Screen savedScreen = screenRepository.save(screen);
        return mapToScreenResponse(savedScreen);
    }

    @Override
    public ScreenResponse getScreenById(Long id) {
        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));
        return mapToScreenResponse(screen);
    }

    @Override
    public List<ScreenResponse> getScreensByTheater(Long id) {
        return screenRepository.findById(id).stream().map(this::mapToScreenResponse).toList();
    }

    @Override
    @Transactional
    public ScreenResponse updateScreen(Long id, ScreenRequest request) {
        Screen screen =  screenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        Long theatreId = request.getTheatreId();
        if(screenRepository.existsByScreenNumberAndTheatreIdAndIdNot(request.getScreenNumber(), id, theatreId))
            throw new RuntimeException("Screen already exists in this theatre");

        screen.setScreenNumber(request.getScreenNumber());
        screen.setCapacity(request.getCapacity());
        Screen updated = screenRepository.save(screen);
        return mapToScreenResponse(updated);
    }

    @Override
    @Transactional
    public String deleteScreen(Long id) {
        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));
        boolean hasShows = showRepository.existsByScreenId(id);
        if(hasShows) {
            return "Screen Cannot be deleted, shows are associated with it";
        }
        screenRepository.delete(screen);
        return "Screen has been deleted";
    }

    private ScreenResponse mapToScreenResponse(Screen screen) {
        return ScreenResponse.builder()
                .screenNumber(screen.getScreenNumber())
                .capacity(screen.getCapacity())
                .theatreId(screen.getTheatre().getId())
                .build();
    }
}
