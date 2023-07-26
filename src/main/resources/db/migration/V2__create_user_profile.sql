CREATE TABLE rentacar_user_profile (
   id IDENTITY NOT NULL PRIMARY KEY,
   credentials_id BIGINT NOT NULL UNIQUE,
   first_name VARCHAR(100),
   last_name VARCHAR(100),
   email VARCHAR(64),
   phone_number VARCHAR(15),
   birth_date DATE,
   city VARCHAR(64),
   zip_code VARCHAR(5),
   street VARCHAR(64),
   country VARCHAR(64),
   FOREIGN KEY (credentials_id) REFERENCES rentacar_user_credentials (id)
);

CREATE SEQUENCE rentacar_user_profile_seq AS BIGINT START WITH 1 INCREMENT BY 5;