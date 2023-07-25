CREATE TABLE rentacar_user (
                          id IDENTITY NOT NULL PRIMARY KEY,
                          username VARCHAR(36) NOT NULL,
                          password VARCHAR(255) NOT NULL
);

CREATE SEQUENCE rentacar_seq AS BIGINT START WITH 1 INCREMENT BY 5;