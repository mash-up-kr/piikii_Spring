CREATE TABLE piikii.room
(
    id             BIGSERIAL primary key,
    address        VARCHAR(255) not null,
    meet_day       DATE         not null,
    thumbnail_link VARCHAR(255) null,
    password       SMALLINT     not null,
    vote_deadline  TIMESTAMP    null,
    room_id        UUID         not null unique default uuid_generate_v4(),
    meeting_name   VARCHAR(255) not null,
    message        VARCHAR(255) null,

    created_at     TIMESTAMP    not null,
    modified_at    TIMESTAMP    not null,
    is_deleted     BOOLEAN      not null default false
);

CREATE TABLE piikii.room_user
(
    id          BIGSERIAL primary key,
    room_id     BIGINT    not null,
    voted       BOOLEAN,

    created_at  TIMESTAMP not null,
    modified_at TIMESTAMP not null,
    is_deleted  BOOLEAN   not null default false
);

CREATE TABLE piikii.room_vote
(
    id            BIGSERIAL primary key,
    user_id       uuid        not null,
    room_place_id BIGINT      not null,
    content       varchar(10) not null,

    created_at    TIMESTAMP   not null,
    modified_at   TIMESTAMP   not null,
    is_deleted    BOOLEAN     not null default false
);

CREATE TABLE piikii.source_place
(
    id             BIGSERIAL primary key,
    origin_map_id  BIGINT      not null,
    url            TEXT        not null,
    thumbnail_link TEXT        null,
    address        TEXT        null,
    phone_number   VARCHAR(15) null,
    star_grade     REAL        null,
    source         VARCHAR(10) null,

    created_at     TIMESTAMP   not null,
    modified_at    TIMESTAMP   not null,
    is_deleted     BOOLEAN     not null default false
);

CREATE TABLE piikii.room_place
(
    id                 BIGSERIAL primary key,
    room_id            BIGINT       not null,
    url                TEXT         null,
    thumbnail_link     TEXT         null,
    address            TEXT         null,
    phone_number       VARCHAR(15)  null,
    star_grade         REAL         null,
    source             VARCHAR(10)  null, -- [AVOCADO, LEMON, MANUAL]
    note               VARCHAR(100) null,
    vote_like_count    SMALLINT     null,
    vote_dislike_count SMALLINT     null,

    created_at         TIMESTAMP    not null,
    modified_at        TIMESTAMP    not null,
    is_deleted         BOOLEAN      not null default false
);

CREATE TABLE piikii.room_course_result
(
    id                 BIGSERIAL primary key,
    room_id            BIGINT    not null,
    course_category_id BIGINT    not null,
    room_place_id      BIGINT    not null,

    created_at         TIMESTAMP not null,
    modified_at        TIMESTAMP not null,
    is_deleted         BOOLEAN   not null default false
);

CREATE TABLE piikii.room_category
(
    id          BIGSERIAL primary key,
    room_id     BIGINT      not null,
    name        VARCHAR(20) not null,

    created_at  TIMESTAMP   not null,
    modified_at TIMESTAMP   not null,
    is_deleted  BOOLEAN     not null default false
);
