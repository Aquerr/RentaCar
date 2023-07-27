CREATE TABLE account_activation_token (
    id IDENTITY NOT NULL PRIMARY KEY,
    credentials_id BIGINT UNIQUE NOT NULL,
    token VARCHAR(64) UNIQUE NOT NULL,
    expiration_date_time TIMESTAMP WITH TIME ZONE NOT NULL,
    used TINYINT DEFAULT 1,
    FOREIGN KEY (credentials_id) REFERENCES rentacar_user_credentials (id)
);