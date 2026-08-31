package com.bharath.ticketo.dto.seat;

import com.bharath.ticketo.model.enums.SeatType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatRequest {
    @NotNull(message = "seat Number is required")
    private Integer seatNumber;

    @NotNull(message = "row Number is required")
    private Character rowNumber;

    @NotNull(message = "seat type is required")
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @NotNull(message = "price is required")
    private Double price;

    @NotNull(message = "screen ID is required")
    private Long screenId;
}
