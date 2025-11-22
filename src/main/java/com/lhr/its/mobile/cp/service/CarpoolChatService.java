package com.lhr.its.mobile.cp.service;

import com.lhr.its.mobile.cp.dto.ChatMessageRequest;
import com.lhr.its.mobile.cp.dto.ChatMessageResponse;

import java.util.List;
import java.util.UUID;

public interface CarpoolChatService {

    ChatMessageResponse sendMessage(UUID companyId, UUID spaceId, UUID tripId, ChatMessageRequest request);

    List<ChatMessageResponse> getMessages(UUID companyId, UUID spaceId, UUID tripId);
}
