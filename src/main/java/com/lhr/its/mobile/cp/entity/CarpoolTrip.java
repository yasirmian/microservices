package com.lhr.its.mobile.cp.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carpool_trips")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarpoolTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID companyId;

    @Column(nullable = false)
    private UUID spaceId;

    @Column(nullable = false)
    private String fromLocation;

    @Column(nullable = false)
    private String toLocation;

    @ElementCollection
    @CollectionTable(name = "carpool_trip_pickups", joinColumns = @JoinColumn(name = "trip_id"))
    @Column(name = "pickup_point")
    @Builder.Default
    private List<String> pickupPoints = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "carpool_trip_drops", joinColumns = @JoinColumn(name = "trip_id"))
    @Column(name = "drop_point")
    @Builder.Default
    private List<String> dropPoints = new ArrayList<>();

    @Column(nullable = false)
    private int availableSeats;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal costPerSeat;

    @Column(nullable = false)
    private String allowedItems;

    @Column(nullable = false)
    private String vehicleType;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    private String remarks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status;

    @Column(nullable = false)
    private String createdByResidentHash;

    private String bookedByResidentHash;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private long version;
}
