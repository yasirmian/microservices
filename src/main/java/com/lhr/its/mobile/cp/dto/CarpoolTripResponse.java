package com.lhr.its.mobile.cp.dto;

import com.lhr.its.mobile.cp.entity.TripStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CarpoolTripResponse {
    private UUID id;
    private UUID companyId;
    private UUID spaceId;
    private String fromLocation;
    private String toLocation;
    private List<String> pickupPoints;
    private List<String> dropPoints;
    private int availableSeats;
    private BigDecimal costPerSeat;
    private String allowedItems;
    private String vehicleType;
    private LocalDateTime departureTime;
    private String remarks;
    private TripStatus status;
    private String createdByResidentHash;
    private String bookedByResidentHash;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
