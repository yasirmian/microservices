package com.lhr.its.mobile.cp.service;

import com.lhr.its.mobile.cp.dto.BookingRequest;
import com.lhr.its.mobile.cp.dto.CarpoolTripRequest;
import com.lhr.its.mobile.cp.dto.CarpoolTripResponse;
import com.lhr.its.mobile.cp.dto.CarpoolTripSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CarpoolTripService {

    CarpoolTripResponse createTrip(UUID companyId, UUID spaceId, CarpoolTripRequest request);

    Page<CarpoolTripResponse> searchTrips(UUID companyId, UUID spaceId, CarpoolTripSearchCriteria criteria, Pageable pageable);

    CarpoolTripResponse getTrip(UUID companyId, UUID spaceId, UUID tripId, String requestingResidentHash);

    CarpoolTripResponse bookTrip(UUID companyId, UUID spaceId, UUID tripId, BookingRequest bookingRequest);

    CarpoolTripResponse cancelTrip(UUID companyId, UUID spaceId, UUID tripId, String residentHash);

    CarpoolTripResponse withdrawBooking(UUID companyId, UUID spaceId, UUID tripId, String residentHash);
}
