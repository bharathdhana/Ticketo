package com.bharath.ticketo.repository;

import com.bharath.ticketo.model.ReservationSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Long> {

    List<ReservationSeat> findByReservationId(Long reservationId);
    void deleteByReservationId(Long reservationId);

    @Query("""
    SELECT COUNT(rs) > 0
    FROM ReservationSeat rs
    WHERE rs.reservation.show.id = :showId
    AND rs.seat.id = :seatId
    AND rs.reservation.status = 'CONFIRMED'
""")
    boolean isSeatReserved(
            @Param("showId") Long showId,
            @Param("seatId") Long seatId
    );

    boolean existsBySeatId(Long id);
}
