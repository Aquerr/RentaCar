CREATE TABLE user_profile
(
    user_id      BIGINT PRIMARY KEY,
    first_name   VARCHAR(100),
    last_name    VARCHAR(100),
    email        VARCHAR(64),
    phone_number VARCHAR(15),
    birth_date   DATE,
    city         VARCHAR(64),
    zip_code     VARCHAR(5),
    street       VARCHAR(64),
    country      VARCHAR(64),
    icon_name    VARCHAR(25),
    CONSTRAINT fk_user_profile_user_id FOREIGN KEY (user_id) REFERENCES rentacar_user (id)
);