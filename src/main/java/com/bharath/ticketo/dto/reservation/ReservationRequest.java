package com.bharath.ticketo.dto.reservation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "show ID is required")
    private Long showId;

    @NotNull(message = "Seat IDs are required")
    @Size(min = 1, max = 10, message = "can book between 1 and 10 seats")
    private List<@NotNull Long> seatIds;
}
