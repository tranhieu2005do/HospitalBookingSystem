package com.hospital.booking.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "scheduling-service")
public interface ScheduleServiceClient {
    // Add Feign methods here
}
