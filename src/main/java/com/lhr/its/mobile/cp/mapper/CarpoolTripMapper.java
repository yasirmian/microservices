package com.lhr.its.mobile.cp.mapper;

import com.lhr.its.mobile.cp.dto.CarpoolTripRequest;
import com.lhr.its.mobile.cp.dto.CarpoolTripResponse;
import com.lhr.its.mobile.cp.entity.CarpoolTrip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CarpoolTripMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "bookedByResidentHash", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "companyId", ignore = true)
    @Mapping(target = "spaceId", ignore = true)
    @Mapping(target = "version", ignore = true)
    CarpoolTrip toEntity(CarpoolTripRequest request);

    CarpoolTripResponse toResponse(CarpoolTrip entity);

    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    void update(@MappingTarget CarpoolTrip entity, CarpoolTripRequest request);
}
