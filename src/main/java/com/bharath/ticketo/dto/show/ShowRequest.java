package com.bharath.ticketo.dto.show;

import com.bharath.ticketo.model.enums.ShowStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowRequest {
    @NotNull(message = "movie ID is required")
    private Long movieId;

    @NotNull(message = "screen ID is required")
    private Long screenId;

    @NotNull(message = "start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "end time is required")
    private LocalDateTime endTime;

    @NotNull(message = "ticket price is required")
    @Positive(message = "Ticket price should be greater than zero")
    private Double ticketPrice;

    @Enumerated(EnumType.STRING)
    private ShowStatus status;

}
