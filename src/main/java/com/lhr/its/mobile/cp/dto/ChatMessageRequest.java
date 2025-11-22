package com.lhr.its.mobile.cp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatMessageRequest {
    @NotBlank
    private String senderResidentHash;

    @NotBlank
    private String receiverResidentHash;

    @NotBlank
    private String message;
}
