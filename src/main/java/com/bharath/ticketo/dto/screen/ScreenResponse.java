package com.bharath.ticketo.dto.screen;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreenResponse {
    private Long id;
    private Integer screenNumber;
    private Integer capacity;
    private Long theatreId;
}
