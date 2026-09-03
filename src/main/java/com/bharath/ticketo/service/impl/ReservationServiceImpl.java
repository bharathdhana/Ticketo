package com.bharath.ticketo.service.impl;

import com.bharath.ticketo.dto.reservation.ReservationRequest;
import com.bharath.ticketo.dto.reservation.ReservationResponse;
import com.bharath.ticketo.exception.ResourceNotFoundException;
import com.bharath.ticketo.model.*;
import com.bharath.ticketo.model.enums.BookingStatus;
import com.bharath.ticketo.repository.*;
import com.bharath.ticketo.service.ReservationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationSeatRepository reservationSeatRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;

    @Override
    @Transactional
    public ReservationResponse createReservation(Long userId, ReservationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        if(request.getSeatIds() == null || request.getSeatIds().isEmpty())
            throw new IllegalArgumentException("At least one seat must be selected");

        if(request.getSeatIds().size() != request.getSeatIds().stream().distinct().count())
            throw new IllegalArgumentException("duplicate seats are not allowed");

        List<Seat> seats = new ArrayList<>();

        for(Long seatId : request.getSeatIds()) {
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new ResourceNotFoundException(" Seat not found" +  seatId));

            if (!seat.getScreen().getId().equals(show.getScreen().getId()))
                throw new IllegalArgumentException(" Seat does not belong to this show's screen: ");

            if (reservationSeatRepository.isSeatReserved(show.getId(), seatId))
                throw new IllegalArgumentException(" Seat is already reserved " + seatId);

            seats.add(seat);
        }

        BigDecimal ticketPrice = BigDecimal.valueOf(show.getTicketPrice());
        BigDecimal totalAmount = ticketPrice.multiply(BigDecimal.valueOf(seats.size()));

        Reservation reservation = Reservation.builder()
                .user(user)
                .show(show)
                .totalAmount(totalAmount)
                .status(BookingStatus.CONFIRMED)
                .bookedAt(LocalDateTime.now())
                .build();

        reservationRepository.save(reservation);

        for(Seat seat : seats) {
            ReservationSeat reservationSeat = new ReservationSeat();

            reservationSeat.setReservation(reservation);
            reservationSeat.setSeat(seat);
            reservation.getReservationSeats().add(reservationSeat);
            reservationSeatRepository.save(reservationSeat);
        }
        return mapToReservationResponses(reservation);
    }

    @Override
    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream().map(this::mapToReservationResponses).toList();
    }

    @Override
    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        return mapToReservationResponses(reservation);
    }

    @Override
    public List<ReservationResponse> getReservationByUser(Long userId) {
        if(!userRepository.existsById(userId))
            throw new ResourceNotFoundException("User not found");
        return reservationRepository.findByUserId(userId)
                .stream().map(this::mapToReservationResponses).toList();
    }

    @Override
    public List<ReservationResponse> getReservationByShow(Long showId) {
        if(!showRepository.existsById(showId))
            throw new ResourceNotFoundException("Show not found");
        return reservationRepository.findByShowId(showId)
                .stream().map(this::mapToReservationResponses).toList();
    }

    @Override
    @Transactional
    public ReservationResponse cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if(reservation.getStatus() == BookingStatus.CANCELLED)
            throw new ResourceNotFoundException("Reservation is already cancelled");

        reservation.setStatus(BookingStatus.CANCELLED);
        reservationRepository.save(reservation);
        return mapToReservationResponses(reservation);
    }

    @Override
    @Transactional
    public String deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        reservationSeatRepository.deleteByReservationId(id);
        reservationRepository.delete(reservation);
        return "Reservation seat deleted successfully";
    }

    private ReservationResponse mapToReservationResponses(Reservation reservation) {

        List<Long> seatIds = reservationSeatRepository.findByReservationId(reservation.getId())
                .stream().map(reservationSeat -> reservationSeat.getSeat().getId()).toList();

        return ReservationResponse.builder()
                .id(reservation.getId())
                .bookingNumber(reservation.getBookingNumber())
                .userId(reservation.getUser().getId())
                .showId(reservation.getShow().getId())
                .totalAmount(reservation.getTotalAmount())
                .status(reservation.getStatus())
                .bookedAt(reservation.getBookedAt())
                .seatIds(seatIds)
                .build();
    }
}
