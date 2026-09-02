package com.bharath.ticketo.repository;

import com.bharath.ticketo.model.Seat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByScreen_Id(Long screenId);
    boolean existsBySeatNumberAndScreen_Id(@NotBlank(message = "seat Number is required") String seatNumber , Long screenId);
    boolean existsBySeatNumberAndScreen_IdAndIdNot(@NotNull(message = "seat Number is required") String seatNumber, @NotNull(message = "screen ID is required") Long screenId, Long seatId);
}
