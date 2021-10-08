DROP TABLE IF EXISTS mater_values;
DROP TABLE IF EXISTS client_company;
DROP TABLE IF EXISTS requests;
DROP TABLE IF EXISTS request_statuses;
DROP TABLE IF EXISTS maters;
DROP TABLE IF EXISTS addresses CASCADE ;
DROP TABLE IF EXISTS users CASCADE ;
DROP TABLE IF EXISTS user_roles  ;
DROP TABLE IF EXISTS companies;

DROP SEQUENCE IF EXISTS global_seq CASCADE;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE companies
(
    id      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name    VARCHAR(50) NOT NULL,
    address TEXT        NOT NULL,
    phone   BIGINT      NOT NULL
);

CREATE TABLE addresses
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
--     user_id   INTEGER       NOT NULL,
    city      VARCHAR NOT NULL,
    street    VARCHAR NOT NULL,
    house     VARCHAR NOT NULL,
    building  VARCHAR,
    apartment VARCHAR NOT NULL
--     FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE users
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    phone       BIGINT  NOT NULL,
    email       VARCHAR NOT NULL,
    first_name  TEXT    NOT NULL,
    second_name TEXT    NOT NULL,
    patronymic  TEXT    NOT NULL,
    address_id     INTEGER ,
    company_id  INTEGER,
    FOREIGN KEY (company_id) REFERENCES companies (id),
    FOREIGN KEY (address_id) REFERENCES addresses (id)  ,
    CONSTRAINT user_phone UNIQUE (phone)
);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);



CREATE TABLE maters
(
    id      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name    VARCHAR NOT NULL,
    user_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE mater_values
(
    id       INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    mater_id INTEGER   NOT NULL,
    date     TIMESTAMP NOT NULL,
    value    INTEGER   NOT NULL,
    FOREIGN KEY (mater_id) REFERENCES maters (id)
);



CREATE TABLE requests
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    type      INTEGER     NOT NULL,
    title     VARCHAR(50) NOT NULL,
    date      TIMESTAMP   NOT NULL,
    address   INTEGER     NOT NULL,
    comment   TEXT,
    status    INTEGER     NOT NULL,
    client_id INTEGER     NOT NULL,
--     FOREIGN KEY (address) REFERENCES addresses (id),
    FOREIGN KEY (client_id) REFERENCES users (id)
);

