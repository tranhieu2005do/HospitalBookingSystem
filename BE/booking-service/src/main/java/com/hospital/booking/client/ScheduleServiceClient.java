package com.hospital.booking.client;

import com.hospital.booking.dto.response.SlotHoldResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "scheduling-service", url = "http://localhost:8083")
public interface ScheduleServiceClient {
    
    @PostMapping("/api/v1/schedules/hold")
    SlotHoldResponse holdSlot(@RequestParam("slotId") Long slotId, @RequestParam("userId") String userId);
}
