CREATE TABLE mfa_type
(
    id   INTEGER PRIMARY KEY,
    type VARCHAR(32) UNIQUE NOT NULL
);


CREATE TABLE user_mfa_settings
(
    id             BIGSERIAL PRIMARY KEY,
    credentials_id BIGINT UNIQUE NOT NULL,
    mfa_type_id    INTEGER       NOT NULL,
    verified       BOOLEAN       NOT NULL DEFAULT 0
);

CREATE TABLE user_mfa_totp
(
    id             BIGSERIAL PRIMARY KEY,
    credentials_id BIGINT UNIQUE NOT NULL,
    secret         VARCHAR(32)   NOT NULL
);

CREATE SEQUENCE mfa_recovery_codes_seq AS BIGINT START WITH 1 INCREMENT BY 6;

CREATE TABLE mfa_recovery_codes
(
    id             BIGSERIAL PRIMARY KEY,
    credentials_id BIGINT NOT NULL,
    recovery_code  VARCHAR(6)
);

CREATE TABLE mfa_auth_challenge
(
    challenge            VARCHAR(32) UNIQUE       NOT NULL PRIMARY KEY,
    credentials_id       BIGINT                   NOT NULL,
    expiration_date_time TIMESTAMP WITH TIME ZONE NOT NULL
);