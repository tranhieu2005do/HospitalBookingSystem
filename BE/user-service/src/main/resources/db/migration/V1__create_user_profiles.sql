CREATE TABLE user_profiles (
    id BIGSERIAL PRIMARY KEY,
    firebase_uid VARCHAR(128) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(255),
    phone_number VARCHAR(50),
    date_of_birth DATE,
    gender VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_user_profiles_email ON user_profiles(email);
CREATE INDEX idx_user_profiles_firebase_uid ON user_profiles(firebase_uid);
