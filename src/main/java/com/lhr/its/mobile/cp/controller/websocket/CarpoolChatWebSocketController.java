package com.lhr.its.mobile.cp.controller.websocket;

import com.lhr.its.mobile.cp.dto.ChatMessageRequest;
import com.lhr.its.mobile.cp.dto.ChatMessageResponse;
import com.lhr.its.mobile.cp.service.CarpoolChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CarpoolChatWebSocketController {

    private final CarpoolChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{tripId}")
    public void sendMessage(@DestinationVariable UUID tripId, @Payload ChatMessageRequest request) {
        // In a real implementation, the company and space would be inferred from the authenticated session or headers.
        // Here we assume they are provided within the message payload for demo purposes.
        // Placeholder UUIDs illustrate future enrichment points.
        UUID demoCompany = UUID.randomUUID();
        UUID demoSpace = UUID.randomUUID();
        ChatMessageResponse response = chatService.sendMessage(demoCompany, demoSpace, tripId, request);
        messagingTemplate.convertAndSend("/topic/chat/" + tripId, response);
        messagingTemplate.convertAndSendToUser(request.getReceiverResidentHash(), "/queue/messages", response);
    }
}
