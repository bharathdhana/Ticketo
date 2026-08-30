package com.bharath.ticketo.dto.screen;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreenRequest {
    @NotNull(message = "screen Number is required")
    private Integer screenNumber;

    @NotNull(message = "capacity is required")
    private Integer capacity;

    @NotNull(message = "theatre ID is required")
    private Long theatreId;
}
