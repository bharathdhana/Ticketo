package com.bharath.ticketo.controller;

import com.bharath.ticketo.dto.reservation.ReservationRequest;
import com.bharath.ticketo.dto.reservation.ReservationResponse;
import com.bharath.ticketo.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/{userId}")
    public ResponseEntity<ReservationResponse> createReservation(@PathVariable Long userId, @RequestBody @Valid ReservationRequest request) {
        ReservationResponse response = reservationService.createReservation(userId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> responses = reservationService.getAllReservations();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id) {
        ReservationResponse response = reservationService.getReservationById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationResponse>> getReservationByUser(@PathVariable Long userId) {
        List<ReservationResponse> responses = reservationService.getReservationByUser(userId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<List<ReservationResponse>> getReservationByShow(@PathVariable Long showId) {
        List<ReservationResponse> responses = reservationService.getReservationByShow(showId);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable Long id) {
        ReservationResponse response = reservationService.cancelReservation(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        String response = reservationService.deleteReservation(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
