package com.lhr.its.mobile.cp.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ChatMessageResponse {
    private UUID id;
    private UUID tripId;
    private UUID companyId;
    private UUID spaceId;
    private String senderResidentHash;
    private String receiverResidentHash;
    private String message;
    private LocalDateTime sentAt;
    private boolean read;
}
