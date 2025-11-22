package com.lhr.its.mobile.cp.service;

import com.lhr.its.mobile.cp.dto.BookingRequest;
import com.lhr.its.mobile.cp.dto.CarpoolTripRequest;
import com.lhr.its.mobile.cp.entity.CarpoolTrip;
import com.lhr.its.mobile.cp.entity.TripStatus;
import com.lhr.its.mobile.cp.mapper.CarpoolTripMapper;
import com.lhr.its.mobile.cp.repository.CarpoolTripRepository;
import com.lhr.its.mobile.cp.service.impl.CarpoolTripServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarpoolTripServiceTest {

    @Mock
    private CarpoolTripRepository tripRepository;

    @Mock
    private CarpoolTripMapper tripMapper;

    @InjectMocks
    private CarpoolTripServiceImpl tripService;

    private UUID companyId;
    private UUID spaceId;

    @BeforeEach
    void setup() {
        companyId = UUID.randomUUID();
        spaceId = UUID.randomUUID();
    }

    @Test
    void shouldCreateTrip() {
        CarpoolTripRequest request = new CarpoolTripRequest();
        request.setFromLocation("A");
        request.setToLocation("B");
        request.setPickupPoints(java.util.List.of("P1"));
        request.setDropPoints(java.util.List.of("D1"));
        request.setAvailableSeats(2);
        request.setCostPerSeat(BigDecimal.ONE);
        request.setAllowedItems("Bags");
        request.setVehicleType("Car");
        request.setDepartureTime(LocalDateTime.now().plusDays(1));
        request.setCreatedByResidentHash("owner");

        CarpoolTrip entity = CarpoolTrip.builder()
                .companyId(companyId)
                .spaceId(spaceId)
                .status(TripStatus.CREATED)
                .build();

        when(tripMapper.toEntity(any())).thenReturn(entity);
        when(tripRepository.save(any())).thenReturn(entity);
        tripService.createTrip(companyId, spaceId, request);
    }

    @Test
    void shouldBookTrip() {
        UUID tripId = UUID.randomUUID();
        BookingRequest request = new BookingRequest();
        request.setResidentHash("r1");

        CarpoolTrip trip = CarpoolTrip.builder()
                .id(tripId)
                .companyId(companyId)
                .spaceId(spaceId)
                .availableSeats(1)
                .status(TripStatus.CREATED)
                .createdByResidentHash("owner")
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        when(tripRepository.save(any())).thenReturn(trip);
        tripService.bookTrip(companyId, spaceId, tripId, request);
    }
}
