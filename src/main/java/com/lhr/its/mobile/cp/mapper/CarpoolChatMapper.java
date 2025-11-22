package com.lhr.its.mobile.cp.mapper;

import com.lhr.its.mobile.cp.dto.ChatMessageRequest;
import com.lhr.its.mobile.cp.dto.ChatMessageResponse;
import com.lhr.its.mobile.cp.entity.CarpoolChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarpoolChatMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tripId", ignore = true)
    @Mapping(target = "companyId", ignore = true)
    @Mapping(target = "spaceId", ignore = true)
    @Mapping(target = "sentAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "read", constant = "false")
    CarpoolChatMessage toEntity(ChatMessageRequest request);

    ChatMessageResponse toResponse(CarpoolChatMessage entity);
}
