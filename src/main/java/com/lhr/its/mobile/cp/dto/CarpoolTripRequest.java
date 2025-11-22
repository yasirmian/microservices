package com.lhr.its.mobile.cp.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CarpoolTripRequest {

    @NotBlank
    private String fromLocation;

    @NotBlank
    private String toLocation;

    @NotEmpty
    private List<String> pickupPoints;

    @NotEmpty
    private List<String> dropPoints;

    @Min(1)
    private int availableSeats;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal costPerSeat;

    @NotBlank
    private String allowedItems;

    @NotBlank
    private String vehicleType;

    @NotNull
    @Future
    private LocalDateTime departureTime;

    private String remarks;

    @NotBlank
    private String createdByResidentHash;
}
