package com.bharath.ticketo.controller;

import com.bharath.ticketo.dto.screen.ScreenRequest;
import com.bharath.ticketo.dto.screen.ScreenResponse;
import com.bharath.ticketo.service.ScreenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/screen")
public class ScreenController {

    private final ScreenService screenService;

    @PostMapping
    public ResponseEntity<ScreenResponse> createScreen(@RequestBody @Valid ScreenRequest request) {
        ScreenResponse response = screenService.createScreen(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreenResponse> getScreenById(@PathVariable Long id){
        ScreenResponse response = screenService.getScreenById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ScreenResponse>> getScreensByTheater(@RequestParam("theatreId") Long theatreId) {
        List<ScreenResponse> responses = screenService.getScreensByTheater(theatreId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScreenResponse> updateScreen(@RequestBody @Valid ScreenRequest request, @PathVariable Long id) {
        ScreenResponse response = screenService.updateScreen(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteScreen(@PathVariable Long id) {
        String response = screenService.deleteScreen(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
