package com.hospital.scheduling.enums;

public enum SlotStatus {
    AVAILABLE, // vẫn còn trống bác sĩ rảnh và chưa ai đặt
    HOLD, // đang trong trạng thái thanh toán, timeout sẽ trả về AVAILABLE
    BOOKED, // đã có người đặt
    CANCELLED, // đã bị hủy
    BLOCKED // bị chặn có chủ đích do bác sĩ hoặc bệnh viện
}
