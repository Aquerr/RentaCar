CREATE TABLE rentacar_user_credentials (
      id IDENTITY NOT NULL PRIMARY KEY,
      username VARCHAR(36) UNIQUE NOT NULL,
      email VARCHAR(64) UNIQUE NOT NULL,
      password VARCHAR(255) NOT NULL,
      verified TINYINT DEFAULT 0,
      locked TINYINT DEFAULT 0
);

CREATE SEQUENCE rentacar_user_credentials_seq AS BIGINT START WITH 1 INCREMENT BY 5;