package com.bharath.ticketo.dto.screen;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreenRequest {
    @NotBlank(message = "screen Number is required")
    private Integer screenNumber;

    @NotBlank(message = "capacity is required")
    private Integer capacity;

    @NotBlank(message = "theatre ID is required")
    private Long theatreId;
}
