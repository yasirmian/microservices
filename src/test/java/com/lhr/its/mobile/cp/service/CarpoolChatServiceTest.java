package com.lhr.its.mobile.cp.service;

import com.lhr.its.mobile.cp.dto.ChatMessageRequest;
import com.lhr.its.mobile.cp.entity.CarpoolChatMessage;
import com.lhr.its.mobile.cp.entity.CarpoolTrip;
import com.lhr.its.mobile.cp.entity.TripStatus;
import com.lhr.its.mobile.cp.mapper.CarpoolChatMapper;
import com.lhr.its.mobile.cp.repository.CarpoolChatRepository;
import com.lhr.its.mobile.cp.repository.CarpoolTripRepository;
import com.lhr.its.mobile.cp.service.impl.CarpoolChatServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarpoolChatServiceTest {

    @Mock
    private CarpoolChatRepository chatRepository;

    @Mock
    private CarpoolTripRepository tripRepository;

    @Mock
    private CarpoolChatMapper chatMapper;

    @InjectMocks
    private CarpoolChatServiceImpl chatService;

    @Test
    void shouldSendMessageWhenTripActive() {
        UUID tripId = UUID.randomUUID();
        UUID companyId = UUID.randomUUID();
        UUID spaceId = UUID.randomUUID();

        ChatMessageRequest request = new ChatMessageRequest();
        request.setMessage("hello");
        request.setSenderResidentHash("owner");
        request.setReceiverResidentHash("rider");

        when(tripRepository.findById(tripId)).thenReturn(Optional.of(CarpoolTrip.builder()
                .id(tripId)
                .companyId(companyId)
                .spaceId(spaceId)
                .status(TripStatus.ACTIVE)
                .build()));
        when(chatMapper.toEntity(any())).thenReturn(CarpoolChatMessage.builder()
                .sentAt(LocalDateTime.now())
                .build());
        when(chatRepository.save(any())).thenReturn(CarpoolChatMessage.builder().build());
        chatService.sendMessage(companyId, spaceId, tripId, request);
    }
}
