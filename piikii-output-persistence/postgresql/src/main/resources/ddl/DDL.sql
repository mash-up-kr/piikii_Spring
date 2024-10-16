CREATE SCHEMA IF NOT EXISTS piikii;

CREATE TYPE piikii.source_type AS ENUM ('AVOCADO', 'LEMON', 'MANUAL');
CREATE TYPE piikii.schedule_type AS ENUM ('ARCADE', 'DISH', 'DESSERT', 'ALCOHOL');
CREATE TYPE piikii.vote_result AS ENUM ('AGREE', 'DISAGREE');

CREATE TABLE piikii.room
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    room_uid       UUID         NOT NULL UNIQUE,
    name           VARCHAR(255) NOT NULL,
    thumbnail_link VARCHAR(255) NOT NULL,
    password       VARCHAR(4)   NOT NULL,
    vote_deadline  TIMESTAMP(6),
    message        VARCHAR(255),
    is_deleted     BOOLEAN      NOT NULL,
    created_at     TIMESTAMP(6) NOT NULL,
    modified_at    TIMESTAMP(6) NOT NULL
);

CREATE TABLE piikii.schedule
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    room_uid    UUID                 NOT NULL,
    name        VARCHAR(255)         NOT NULL,
    sequence    INTEGER              NOT NULL,
    type        piikii.schedule_type NOT NULL,
    is_deleted  BOOLEAN              NOT NULL,
    created_at  TIMESTAMP(6)         NOT NULL,
    modified_at TIMESTAMP(6)         NOT NULL
);

CREATE TABLE piikii.place
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    room_uid        UUID               NOT NULL,
    schedule_id     BIGINT             NOT NULL,
    name            VARCHAR(255)       NOT NULL,
    url             VARCHAR(255),
    thumbnail_links TEXT               NOT NULL,
    address         VARCHAR(255),
    phone_number    VARCHAR(15),
    star_grade      REAL               NOT NULL,
    origin          piikii.source_type NOT NULL,
    memo            VARCHAR(150),
    confirmed       BOOLEAN            NOT NULL,
    latitude        DOUBLE PRECISION,
    longitude       DOUBLE PRECISION,
    opening_hours   VARCHAR(255),
    is_deleted      BOOLEAN            NOT NULL,
    created_at      TIMESTAMP(6)       NOT NULL,
    modified_at     TIMESTAMP(6)       NOT NULL
);

CREATE TABLE piikii.course
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    room_uid    UUID         NOT NULL,
    schedule_id BIGINT       NOT NULL UNIQUE,
    place_id    BIGINT       NOT NULL,
    is_deleted  BOOLEAN      NOT NULL,
    created_at  TIMESTAMP(6) NOT NULL,
    modified_at TIMESTAMP(6) NOT NULL
);

CREATE TABLE piikii.vote
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_uid    UUID               NOT NULL,
    place_id    BIGINT             NOT NULL,
    result      piikii.vote_result NOT NULL,
    is_deleted  BOOLEAN            NOT NULL,
    created_at  TIMESTAMP(6)       NOT NULL,
    modified_at TIMESTAMP(6)       NOT NULL
);

CREATE TABLE piikii.piikii_user
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_uid    UUID         NOT NULL,
    room_id     BIGINT       NOT NULL,
    is_deleted  BOOLEAN      NOT NULL,
    created_at  TIMESTAMP(6) NOT NULL,
    modified_at TIMESTAMP(6) NOT NULL
);

CREATE TABLE piikii.origin_place
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    origin_map_id   VARCHAR(255)       NOT NULL UNIQUE,
    name            VARCHAR(255)       NOT NULL,
    url             VARCHAR(255)       NOT NULL,
    thumbnail_links TEXT               NOT NULL,
    address         VARCHAR(255),
    phone_number    VARCHAR(15),
    star_grade      REAL,
    origin          piikii.source_type NOT NULL,
    latitude        DOUBLE PRECISION,
    longitude       DOUBLE PRECISION,
    opening_hours   VARCHAR(255),
    review_count    INTEGER            NOT NULL,
    category        VARCHAR(255),
    is_deleted      BOOLEAN            NOT NULL,
    created_at      TIMESTAMP(6)       NOT NULL,
    modified_at     TIMESTAMP(6)       NOT NULL
);
