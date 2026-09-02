package com.bharath.ticketo.controller;

import com.bharath.ticketo.dto.seat.SeatRequest;
import com.bharath.ticketo.dto.seat.SeatResponse;
import com.bharath.ticketo.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seat")
public class SeatController {
    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<SeatResponse> createSeat(@RequestBody @Valid SeatRequest request) {
        SeatResponse response = seatService.createSeat(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<SeatResponse>> getSeatsByScreen(@PathVariable Long screenId) {
        List<SeatResponse> responses = seatService.getSeatsByScreen(screenId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{seatId}")
    public ResponseEntity<SeatResponse> getSeatById(@PathVariable Long seatId) {
        SeatResponse response = seatService.getSeatById(seatId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{seatId}")
    public ResponseEntity<SeatResponse> updateSeat(@PathVariable Long seatId, @RequestBody @Valid SeatRequest request) {
        SeatResponse response = seatService.updateSeat(seatId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{seatId}")
    public ResponseEntity<String> deleteSeat(@PathVariable Long seatId) {
        String response = seatService.deleteSeat(seatId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SeatResponse>> getAllSeats() {
        List<SeatResponse> responses = seatService.getAllSeats();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
