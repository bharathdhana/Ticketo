package com.bharath.ticketo.dto.show;

import com.bharath.ticketo.model.enums.ShowStatus;
import jakarta.validation.constraints.AssertTrue;
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

    private ShowStatus status;

    @AssertTrue(message = "End time must be after start time")
    public boolean isEndTimeValid() {
        return endTime == null || startTime == null || endTime.isAfter(startTime);
    }
}
