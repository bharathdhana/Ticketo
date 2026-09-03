package com.bharath.ticketo.dto.reservation;

import com.bharath.ticketo.model.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResponse {
    private Long id;
    private String bookingNumber;
    private Long userId;
    private Long showId;
    private BigDecimal totalAmount;
    private BookingStatus status;
    private LocalDateTime bookedAt;
    private List<Long> seatIds;
}
