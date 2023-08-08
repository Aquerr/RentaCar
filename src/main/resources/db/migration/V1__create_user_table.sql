CREATE SEQUENCE user_credentials_seq AS BIGINT START WITH 1 INCREMENT BY 5;

CREATE TABLE user_credentials (
      id BIGSERIAL PRIMARY KEY,
      username VARCHAR(36) UNIQUE NOT NULL,
      email VARCHAR(64) UNIQUE NOT NULL,
      password VARCHAR(255) NOT NULL,
      activated BOOLEAN DEFAULT 0 NOT NULL,
      locked BOOLEAN DEFAULT 0 NOT NULL,
      created_date_time TIMESTAMP WITH TIME ZONE NOT NULL,
      activated_date_time TIMESTAMP WITH TIME ZONE
);