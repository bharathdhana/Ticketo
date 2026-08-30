package com.bharath.ticketo.repository;

import com.bharath.ticketo.model.ReservationSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Long> {
    boolean existsByReservationShowIdAndSeatId(Long showId, Long seatId);
    List<ReservationSeat> findByReservationId(Long reservationId);
    void deleteByReservationId(Long reservationId);
}
