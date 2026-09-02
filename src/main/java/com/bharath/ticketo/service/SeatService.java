package com.bharath.ticketo.service;

import com.bharath.ticketo.dto.seat.SeatRequest;
import com.bharath.ticketo.dto.seat.SeatResponse;

import java.util.List;

public interface SeatService {
    SeatResponse createSeat(SeatRequest request);
    List<SeatResponse> getSeatsByScreen(Long screenId);
    List<SeatResponse> getAllSeats();
    SeatResponse getSeatById(Long seatId);
    SeatResponse updateSeat(Long seatId, SeatRequest request);
    String deleteSeat(Long id);
}
