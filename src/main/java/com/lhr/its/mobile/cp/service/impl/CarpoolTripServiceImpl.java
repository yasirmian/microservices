package com.lhr.its.mobile.cp.service.impl;

import com.lhr.its.mobile.cp.dto.BookingRequest;
import com.lhr.its.mobile.cp.dto.CarpoolTripRequest;
import com.lhr.its.mobile.cp.dto.CarpoolTripResponse;
import com.lhr.its.mobile.cp.dto.CarpoolTripSearchCriteria;
import com.lhr.its.mobile.cp.entity.CarpoolTrip;
import com.lhr.its.mobile.cp.entity.TripStatus;
import com.lhr.its.mobile.cp.exception.BusinessException;
import com.lhr.its.mobile.cp.exception.ResourceNotFoundException;
import com.lhr.its.mobile.cp.mapper.CarpoolTripMapper;
import com.lhr.its.mobile.cp.repository.CarpoolTripRepository;
import com.lhr.its.mobile.cp.service.CarpoolTripService;
import com.lhr.its.mobile.cp.util.CarpoolTripSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CarpoolTripServiceImpl implements CarpoolTripService {

    private final CarpoolTripRepository tripRepository;
    private final CarpoolTripMapper tripMapper;

    @Override
    public CarpoolTripResponse createTrip(UUID companyId, UUID spaceId, CarpoolTripRequest request) {
        CarpoolTrip trip = tripMapper.toEntity(request);
        trip.setCompanyId(companyId);
        trip.setSpaceId(spaceId);
        trip.setStatus(TripStatus.CREATED);
        return tripMapper.toResponse(tripRepository.save(trip));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarpoolTripResponse> searchTrips(UUID companyId, UUID spaceId, CarpoolTripSearchCriteria criteria, Pageable pageable) {
        Specification<CarpoolTrip> specification = CarpoolTripSpecification.build(companyId, spaceId, criteria);
        return tripRepository.findAll(specification, pageable).map(tripMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public CarpoolTripResponse getTrip(UUID companyId, UUID spaceId, UUID tripId, String requestingResidentHash) {
        CarpoolTrip trip = findTrip(companyId, spaceId, tripId);
        // Placeholder: mask data when the requesting resident is not the creator or booker.
        return tripMapper.toResponse(trip);
    }

    @Override
    public CarpoolTripResponse bookTrip(UUID companyId, UUID spaceId, UUID tripId, BookingRequest bookingRequest) {
        CarpoolTrip trip = findTrip(companyId, spaceId, tripId);
        if (trip.getStatus() == TripStatus.CANCELLED || trip.getStatus() == TripStatus.COMPLETED) {
            throw new BusinessException("Trip is not available for booking");
        }
        if (trip.getAvailableSeats() <= 0) {
            throw new BusinessException("No seats available");
        }
        trip.setAvailableSeats(trip.getAvailableSeats() - 1);
        trip.setBookedByResidentHash(bookingRequest.getResidentHash());
        trip.setStatus(TripStatus.ACTIVE);
        trip.setUpdatedAt(LocalDateTime.now());
        // Placeholder: expose full profile of booking resident to owner after booking.
        return tripMapper.toResponse(tripRepository.save(trip));
    }

    @Override
    public CarpoolTripResponse cancelTrip(UUID companyId, UUID spaceId, UUID tripId, String residentHash) {
        CarpoolTrip trip = findTrip(companyId, spaceId, tripId);
        if (!residentHash.equals(trip.getCreatedByResidentHash())) {
            throw new BusinessException("Only the trip owner can cancel the trip");
        }
        trip.setStatus(TripStatus.CANCELLED);
        trip.setUpdatedAt(LocalDateTime.now());
        // Placeholder: notify riders and close chat rooms.
        return tripMapper.toResponse(tripRepository.save(trip));
    }

    @Override
    public CarpoolTripResponse withdrawBooking(UUID companyId, UUID spaceId, UUID tripId, String residentHash) {
        CarpoolTrip trip = findTrip(companyId, spaceId, tripId);
        if (trip.getBookedByResidentHash() == null || !trip.getBookedByResidentHash().equals(residentHash)) {
            throw new BusinessException("Resident has no booking to withdraw");
        }
        trip.setBookedByResidentHash(null);
        trip.setAvailableSeats(trip.getAvailableSeats() + 1);
        trip.setUpdatedAt(LocalDateTime.now());
        // Placeholder: reduce owner visibility to masked profile and notify via WebSocket.
        return tripMapper.toResponse(tripRepository.save(trip));
    }

    private CarpoolTrip findTrip(UUID companyId, UUID spaceId, UUID tripId) {
        return tripRepository.findById(tripId)
                .filter(trip -> trip.getCompanyId().equals(companyId) && trip.getSpaceId().equals(spaceId))
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found"));
    }
}
