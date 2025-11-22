package com.lhr.its.mobile.cp.util;

import com.lhr.its.mobile.cp.dto.CarpoolTripSearchCriteria;
import com.lhr.its.mobile.cp.entity.CarpoolTrip;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
import java.util.UUID;

public final class CarpoolTripSpecification {
    private CarpoolTripSpecification() {
    }

    public static Specification<CarpoolTrip> build(UUID companyId, UUID spaceId, CarpoolTripSearchCriteria criteria) {
        return Specification.<CarpoolTrip>where((root, query, cb) -> cb.and(
                        cb.equal(root.get("companyId"), companyId),
                        cb.equal(root.get("spaceId"), spaceId)
                ))
                .and(like("fromLocation", criteria.getFromLocation()))
                .and(like("toLocation", criteria.getToLocation()))
                .and(like("vehicleType", criteria.getVehicleType()))
                .and(equals("status", criteria.getStatus()))
                .and((root, query, cb) -> {
                    if (criteria.getDepartureFrom() != null && criteria.getDepartureTo() != null) {
                        return cb.between(root.get("departureTime"), criteria.getDepartureFrom(), criteria.getDepartureTo());
                    } else if (criteria.getDepartureFrom() != null) {
                        return cb.greaterThanOrEqualTo(root.get("departureTime"), criteria.getDepartureFrom());
                    } else if (criteria.getDepartureTo() != null) {
                        return cb.lessThanOrEqualTo(root.get("departureTime"), criteria.getDepartureTo());
                    }
                    return null;
                });
    }

    private static Specification<CarpoolTrip> like(String field, String value) {
        return (root, query, cb) -> Objects.isNull(value) ? null : cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
    }

    private static <T> Specification<CarpoolTrip> equals(String field, T value) {
        return (root, query, cb) -> Objects.isNull(value) ? null : cb.equal(root.get(field), value);
    }
}
