package com.lhr.its.mobile.cp.repository;

import com.lhr.its.mobile.cp.entity.CarpoolTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CarpoolTripRepository extends JpaRepository<CarpoolTrip, UUID>, JpaSpecificationExecutor<CarpoolTrip> {
}
