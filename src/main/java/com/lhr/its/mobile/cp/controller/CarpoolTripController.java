package com.lhr.its.mobile.cp.controller;

import com.lhr.its.mobile.cp.dto.BookingRequest;
import com.lhr.its.mobile.cp.dto.CarpoolTripRequest;
import com.lhr.its.mobile.cp.dto.CarpoolTripResponse;
import com.lhr.its.mobile.cp.dto.CarpoolTripSearchCriteria;
import com.lhr.its.mobile.cp.service.CarpoolTripService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/{companyId}/{spaceId}/cp")
public class CarpoolTripController {

    private final CarpoolTripService tripService;

    public CarpoolTripController(CarpoolTripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/trips")
    public CarpoolTripResponse createTrip(@PathVariable UUID companyId,
                                          @PathVariable UUID spaceId,
                                          @Valid @RequestBody CarpoolTripRequest request) {
        return tripService.createTrip(companyId, spaceId, request);
    }

    @GetMapping("/trips")
    public Page<CarpoolTripResponse> searchTrips(@PathVariable UUID companyId,
                                                 @PathVariable UUID spaceId,
                                                 @ParameterObject CarpoolTripSearchCriteria criteria,
                                                 @ParameterObject Pageable pageable) {
        return tripService.searchTrips(companyId, spaceId, criteria, pageable);
    }

    @GetMapping("/trips/{id}")
    public CarpoolTripResponse getTrip(@PathVariable UUID companyId,
                                       @PathVariable UUID spaceId,
                                       @PathVariable UUID id,
                                       @RequestParam(required = false) String residentHash) {
        return tripService.getTrip(companyId, spaceId, id, residentHash);
    }

    @PostMapping("/trips/{id}/book")
    public CarpoolTripResponse bookTrip(@PathVariable UUID companyId,
                                        @PathVariable UUID spaceId,
                                        @PathVariable UUID id,
                                        @Valid @RequestBody BookingRequest bookingRequest) {
        return tripService.bookTrip(companyId, spaceId, id, bookingRequest);
    }

    @PostMapping("/trips/{id}/cancel")
    public CarpoolTripResponse cancelTrip(@PathVariable UUID companyId,
                                          @PathVariable UUID spaceId,
                                          @PathVariable UUID id,
                                          @RequestParam String residentHash) {
        return tripService.cancelTrip(companyId, spaceId, id, residentHash);
    }

    @PostMapping("/trips/{id}/withdraw")
    public CarpoolTripResponse withdrawBooking(@PathVariable UUID companyId,
                                               @PathVariable UUID spaceId,
                                               @PathVariable UUID id,
                                               @RequestParam String residentHash) {
        return tripService.withdrawBooking(companyId, spaceId, id, residentHash);
    }
}
