package com.bharath.ticketo.service;

import com.bharath.ticketo.dto.screen.ScreenRequest;
import com.bharath.ticketo.dto.screen.ScreenResponse;

import java.util.List;

public interface ScreenService {
    ScreenResponse createScreen(ScreenRequest request);
    ScreenResponse getScreenById(Long id);
    List<ScreenResponse> getScreensByTheater(Long theaterId);
    ScreenResponse updateScreen(Long id, ScreenRequest request);
    String deleteScreen(Long id);
}
