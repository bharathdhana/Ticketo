package com.bharath.ticketo.service.impl;

import com.bharath.ticketo.dto.seat.SeatRequest;
import com.bharath.ticketo.dto.seat.SeatResponse;
import com.bharath.ticketo.exception.ResourceNotFoundException;
import com.bharath.ticketo.model.Screen;
import com.bharath.ticketo.model.Seat;
import com.bharath.ticketo.repository.ReservationRepository;
import com.bharath.ticketo.repository.ScreenRepository;
import com.bharath.ticketo.repository.SeatRepository;
import com.bharath.ticketo.service.SeatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public SeatResponse createSeat(Long screenId, SeatRequest request) {
        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        if(seatRepository.existsBySeatNumberAndScreenId(screenId, request.getScreenId()))
            throw new RuntimeException("Seat already exists in theatre");

        Seat seat = Seat.builder()
                .seatNumber(request.getSeatNumber())
                .rowNumber(request.getRowNumber())
                .seatType(request.getSeatType())
                .price(request.getPrice())
                .screen(screen)
                .build();
        Seat savedSeat = seatRepository.save(seat);
        return mapToSeatResponse(savedSeat);
    }

    @Override
    public List<SeatResponse> getSeatsByScreen(Long screenId) {
        if(!screenRepository.existsById(screenId))
            throw new ResourceNotFoundException("Screen not found");
        return seatRepository.findByScreenId(screenId).stream().map(this::mapToSeatResponse).toList();
    }

    @Override
    public SeatResponse getSeatById(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
        return mapToSeatResponse(seat);
    }

    @Override
    @Transactional
    public SeatResponse updateSeat(Long seatId, SeatRequest request) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));
        if(seatRepository.existsBySeatNumberAndScreenIdAndIdNot(seatId, request.getScreenId(), seatId))
            throw new RuntimeException("Seat already exists in theatre");

        seat.setSeatNumber(request.getSeatNumber());
        seat.setRowNumber(request.getRowNumber());
        Seat updatedSeat = seatRepository.save(seat);
        return mapToSeatResponse(updatedSeat);
    }

    @Override
    public String deleteSeat(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

       boolean hasReservations = reservationRepository.existsBySeatId(seat.getId());
       if(hasReservations)
           throw new ResourceNotFoundException("Seat cannot be deleted because it has reservations");

       seatRepository.delete(seat);
       return "Seat has been deleted";
    }

    private SeatResponse mapToSeatResponse(Seat seat) {
        return SeatResponse.builder()
                .seatNumber(seat.getSeatNumber())
                .rowNumber(seat.getRowNumber())
                .seatType(seat.getSeatType())
                .price(seat.getPrice())
                .screenId(seat.getScreen().getId())
                .build();
    }
}
