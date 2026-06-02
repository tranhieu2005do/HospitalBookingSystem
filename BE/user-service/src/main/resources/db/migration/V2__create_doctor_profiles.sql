CREATE TABLE doctor_profiles (
    id BIGSERIAL PRIMARY KEY,
    firebase_uid VARCHAR(128) NOT NULL UNIQUE,
    specialization VARCHAR(255),
    experience_years INT,
    bio TEXT,
    room_id VARCHAR(50),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_doctor_profiles_firebase_uid ON doctor_profiles(firebase_uid);
CREATE INDEX idx_doctor_profiles_specialization ON doctor_profiles(specialization);
