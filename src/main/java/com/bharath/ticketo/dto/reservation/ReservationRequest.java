package com.bharath.ticketo.dto.reservation;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequest {
    @NotBlank(message = "show ID is required")
    private Long showId;

    @NotBlank(message = "seat ID is required")
    private List<Long> seatIds;
}
