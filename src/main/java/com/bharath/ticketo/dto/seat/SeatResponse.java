package com.bharath.ticketo.dto.seat;

import com.bharath.ticketo.model.enums.SeatType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatResponse {
    private Long id;
    private Integer seatNumber;
    private Character rowNumber;
    private SeatType seatType;
    private Double price;
    private Long screenId;
}
