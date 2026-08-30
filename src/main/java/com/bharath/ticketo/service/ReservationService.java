package com.bharath.ticketo.service;

import com.bharath.ticketo.dto.reservation.ReservationRequest;
import com.bharath.ticketo.dto.reservation.ReservationResponse;

import java.util.List;

public interface ReservationService {
    ReservationResponse createReservation(Long userId, ReservationRequest reservationRequest);
    List<ReservationResponse> getAllReservations();
    ReservationResponse getReservationById(Long id);
    List<ReservationResponse> getReservationByUser(Long userId);
    List<ReservationResponse> getReservationByShow(Long showId);
    ReservationResponse cancelReservation(Long id);
    String deleteReservation(Long id);
}
