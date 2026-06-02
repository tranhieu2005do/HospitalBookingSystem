CREATE TABLE doctor_schedules (
    id BIGINT PRIMARY KEY,
    doctor_id VARCHAR(128) NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_doctor_schedules_doctor_id
ON doctor_schedules(doctor_id);

CREATE TABLE time_slots (
    id BIGINT PRIMARY KEY,
    doctor_id VARCHAR(128) NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    status VARCHAR(30) NOT NULL,
    appointment_id VARCHAR(128),
    version BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT uk_doctor_slot UNIQUE (doctor_id, date, start_time)
);

CREATE INDEX idx_time_slots_doctor_id ON time_slots(doctor_id);
CREATE INDEX idx_time_slots_date ON time_slots(date);
CREATE INDEX idx_time_slots_status ON time_slots(status);

CREATE TABLE slot_holds (
    id BIGINT PRIMARY KEY,
    slot_id BIGINT NOT NULL,
    patient_id VARCHAR(128) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_slot_holds_time_slots
        FOREIGN KEY (slot_id)
        REFERENCES time_slots(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_slot_holds_slot_id ON slot_holds(slot_id);
CREATE INDEX idx_slot_holds_expires_at ON slot_holds(expires_at);
