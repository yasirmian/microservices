package com.lhr.its.mobile.cp.repository;

import com.lhr.its.mobile.cp.entity.CarpoolChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CarpoolChatRepository extends JpaRepository<CarpoolChatMessage, UUID> {
    List<CarpoolChatMessage> findByTripIdAndCompanyIdAndSpaceIdOrderBySentAtAsc(UUID tripId, UUID companyId, UUID spaceId);
}
