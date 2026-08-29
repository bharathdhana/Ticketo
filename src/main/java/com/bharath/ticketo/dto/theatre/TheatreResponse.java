package com.bharath.ticketo.dto.theatre;

import com.bharath.ticketo.model.enums.TheatreStatus;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TheatreResponse {
    private Long id;
    private String name;
    private String location;
    private String city;
    private TheatreStatus status;
}
