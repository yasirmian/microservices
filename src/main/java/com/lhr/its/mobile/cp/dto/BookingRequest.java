package com.lhr.its.mobile.cp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookingRequest {
    @NotBlank
    private String residentHash;
}
