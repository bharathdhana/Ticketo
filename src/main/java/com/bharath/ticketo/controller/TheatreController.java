package com.bharath.ticketo.controller;

import com.bharath.ticketo.dto.theatre.TheatreRequest;
import com.bharath.ticketo.dto.theatre.TheatreResponse;
import com.bharath.ticketo.service.TheatreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/theatre")
public class TheatreController {

    private final TheatreService theatreService;

    @PostMapping
    public ResponseEntity<TheatreResponse> createTheatre(@RequestBody @Valid TheatreRequest request) {
        TheatreResponse response = theatreService.createTheatre(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TheatreResponse> updateTheatre(@RequestBody @Valid TheatreRequest request, @PathVariable long id) {
        TheatreResponse response = theatreService.updateTheatre(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTheatre(@PathVariable long id) {
        String response = theatreService.deleteTheatre(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<TheatreResponse>> getTheatreByName(@PathVariable String name) {
        List<TheatreResponse> response = theatreService.getTheatreByName(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TheatreResponse>> getAllTheatres() {
        List<TheatreResponse> response = theatreService.getAllTheatres();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
