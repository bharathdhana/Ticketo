package com.bharath.ticketo.dto.seat;

import com.bharath.ticketo.model.enums.SeatType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatRequest {
    @NotBlank(message = "seat Number is required")
    private String seatNumber;

    @NotBlank(message = "row Number is required")
    private Character rowNumber;

    @NotBlank(message = "seat type is required")
    private SeatType seatType;

    @NotBlank(message = "price is required")
    private Double price;

    @NotBlank(message = "screen ID is required")
    private Long screenId;
}
