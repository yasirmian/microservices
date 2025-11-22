package com.lhr.its.mobile.cp.controller;

import com.lhr.its.mobile.cp.dto.ChatMessageRequest;
import com.lhr.its.mobile.cp.dto.ChatMessageResponse;
import com.lhr.its.mobile.cp.service.CarpoolChatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/{companyId}/{spaceId}/cp/trips/{tripId}/chat")
public class CarpoolChatController {

    private final CarpoolChatService chatService;

    public CarpoolChatController(CarpoolChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatMessageResponse sendMessage(@PathVariable UUID companyId,
                                           @PathVariable UUID spaceId,
                                           @PathVariable UUID tripId,
                                           @Valid @RequestBody ChatMessageRequest request) {
        return chatService.sendMessage(companyId, spaceId, tripId, request);
    }

    @GetMapping
    public List<ChatMessageResponse> getMessages(@PathVariable UUID companyId,
                                                 @PathVariable UUID spaceId,
                                                 @PathVariable UUID tripId) {
        return chatService.getMessages(companyId, spaceId, tripId);
    }
}
