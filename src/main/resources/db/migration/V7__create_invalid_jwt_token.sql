CREATE TABLE invalid_jwt_token
(
    id                    INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    jwt                   VARCHAR(255)             NOT NULL,
    expiration_date_time  TIMESTAMP WITH TIME ZONE NOT NULL,
    invalidated_date_time TIMESTAMP WITH TIME ZONE NOT NULL
);