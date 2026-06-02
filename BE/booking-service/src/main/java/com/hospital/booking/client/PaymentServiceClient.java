package com.hospital.booking.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {
    // Add Feign methods here
}
