package com.hospital.booking.exception;

public class SlotHoldFailedException extends RuntimeException {
    public SlotHoldFailedException(String message) {
        super(message);
    }
}
