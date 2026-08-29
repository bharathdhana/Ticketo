package com.bharath.ticketo.dto.theatre;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TheatreRequest {
    @NotBlank(message = "Theatre name is required")
    private String name;

    @NotBlank(message = "Theatre location is required")
    private String location;

    @NotBlank(message = "City is required")
    private String city;
}
