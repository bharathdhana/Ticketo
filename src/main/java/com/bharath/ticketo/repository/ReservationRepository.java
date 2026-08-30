package com.bharath.ticketo.repository;

import com.bharath.ticketo.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("""
        SELECT COUNT(rs) > 0
        FROM ReservationSeat rs
        WHERE rs.seat.id = :seatId
    """)
    boolean existsBySeatId(@Param("seatId") Long seatId);
    boolean existsByShowId(Long showId);
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByShowId(Long showId);
}
