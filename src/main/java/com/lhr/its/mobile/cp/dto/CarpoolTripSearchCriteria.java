package com.lhr.its.mobile.cp.dto;

import com.lhr.its.mobile.cp.entity.TripStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class CarpoolTripSearchCriteria {
    private String fromLocation;
    private String toLocation;
    private TripStatus status;
    private String vehicleType;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime departureFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime departureTo;
}
