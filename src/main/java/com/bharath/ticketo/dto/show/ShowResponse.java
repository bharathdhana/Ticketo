package com.bharath.ticketo.dto.show;

import com.bharath.ticketo.model.enums.ShowStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowResponse {
    private Long id;
    private Long movieId;
    private Long screenId;
    private Date showDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double ticketPrice;
    private ShowStatus status;
}
