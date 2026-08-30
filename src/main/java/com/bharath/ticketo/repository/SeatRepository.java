package com.bharath.ticketo.repository;

import com.bharath.ticketo.model.Seat;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByScreenId(Long screenId);
    boolean existsBySeatNumberAndScreenId(Long screenId, @NotBlank(message = "seat Number is required") Long seatNumber);
    boolean existsBySeatNumberAndScreenIdAndIdNot(Long screenId, @NotBlank(message = "seat Number is required") Long seatNumber, Long id);
}
