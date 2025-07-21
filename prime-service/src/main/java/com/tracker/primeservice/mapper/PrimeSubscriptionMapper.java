package com.tracker.primeservice.mapper;

import com.tracker.primeservice.dto.PrimeSubscriptionDto;
import com.tracker.primeservice.entity.PrimeSubscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrimeSubscriptionMapper {

    PrimeSubscriptionDto toDto(PrimeSubscription entity);

    PrimeSubscription toEntity(PrimeSubscriptionDto dto);
}
