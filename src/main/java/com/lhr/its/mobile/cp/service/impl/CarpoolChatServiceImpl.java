package com.lhr.its.mobile.cp.service.impl;

import com.lhr.its.mobile.cp.dto.ChatMessageRequest;
import com.lhr.its.mobile.cp.dto.ChatMessageResponse;
import com.lhr.its.mobile.cp.entity.CarpoolChatMessage;
import com.lhr.its.mobile.cp.entity.TripStatus;
import com.lhr.its.mobile.cp.exception.BusinessException;
import com.lhr.its.mobile.cp.exception.ResourceNotFoundException;
import com.lhr.its.mobile.cp.mapper.CarpoolChatMapper;
import com.lhr.its.mobile.cp.repository.CarpoolChatRepository;
import com.lhr.its.mobile.cp.repository.CarpoolTripRepository;
import com.lhr.its.mobile.cp.service.CarpoolChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CarpoolChatServiceImpl implements CarpoolChatService {

    private final CarpoolChatRepository chatRepository;
    private final CarpoolTripRepository tripRepository;
    private final CarpoolChatMapper chatMapper;

    @Override
    public ChatMessageResponse sendMessage(UUID companyId, UUID spaceId, UUID tripId, ChatMessageRequest request) {
        TripStatus status = tripRepository.findById(tripId)
                .filter(trip -> trip.getCompanyId().equals(companyId) && trip.getSpaceId().equals(spaceId))
                .map(trip -> trip.getStatus())
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found"));

        if (status == TripStatus.CANCELLED || status == TripStatus.COMPLETED) {
            throw new BusinessException("Chat is read-only for completed or cancelled trips");
        }
        CarpoolChatMessage entity = chatMapper.toEntity(request);
        entity.setTripId(tripId);
        entity.setCompanyId(companyId);
        entity.setSpaceId(spaceId);
        // Placeholder: broadcast via STOMP to /topic/chat/{tripId} and /user queues.
        return chatMapper.toResponse(chatRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getMessages(UUID companyId, UUID spaceId, UUID tripId) {
        // Placeholder: apply visibility rules when trips are inactive.
        List<CarpoolChatMessage> messages = chatRepository.findByTripIdAndCompanyIdAndSpaceIdOrderBySentAtAsc(tripId, companyId, spaceId);
        return messages.stream().map(chatMapper::toResponse).toList();
    }
}
