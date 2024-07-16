create table addresses
(
    id           uuid         not null
        primary key,
    city         varchar(255) not null,
    complement   varchar(255),
    country      varchar(255) not null,
    neighborhood varchar(255) not null,
    number       varchar(255) not null,
    state        varchar(255) not null,
    street       varchar(255) not null,
    zip_code     varchar(255) not null
);

create table companies
(
    address_id  uuid
        unique
        constraint fk8w70yf6urddd0ky7ev90okenf
            references addresses,
    id          uuid         not null
        primary key,
    owner_id    uuid         not null,
    cnpj        varchar(255) not null
        unique,
    description varchar(255),
    name        varchar(255) not null
);

create table emails
(
    failed_attempts integer      not null,
    sent            boolean      not null,
    created_at      timestamp(6) not null,
    id              bigserial
        primary key,
    sent_at         timestamp(6),
    body            text         not null,
    subject         varchar(255) not null,
    target          varchar(255) not null
);

create table email_attachments
(
    email_id    bigint not null
        constraint fk753q149o0k6fjxh2fji1616ua
            references emails,
    attachments varchar(255)
);

create table enrollments
(
    created_at timestamp(6) not null,
    updated_at timestamp(6),
    event_id   uuid         not null,
    id         uuid         not null
        primary key,
    user_id    uuid         not null,
    status     varchar(255) not null
);

create table events
(
    end_date    date         not null,
    init_date   date         not null,
    status      integer      not null,
    address_id  uuid
        unique
        constraint fkquc7xx27bo60lupj2rf7e0hn2
            references addresses,
    company_id  uuid         not null,
    id          uuid         not null
        primary key,
    description varchar(1000),
    name        varchar(255) not null
);

create table event_attachments
(
    event_id         uuid not null
        constraint fko49p20umx9s78ww9w2h91dcao
            references events,
    attachment_paths varchar(255)
);

create table event_configurations
(
    event_id uuid         not null
        constraint fk33jpq0j7g5hn9x03pi9y8qh5q
            references events,
    key      varchar(255) not null,
    value    varchar(255),
    primary key (event_id, key)
);

create table messages
(
    id       serial
        primary key,
    subject  integer not null,
    type     char    not null,
    template text    not null
);

create table tickets
(
    expired_in         date         not null,
    valid_in           date         not null,
    created_at         timestamp(6) not null,
    last_time_consumed timestamp(6),
    event_id           uuid         not null,
    id                 uuid         not null
        primary key,
    user_id            uuid         not null,
    code               varchar(255) not null,
    description        varchar(255) not null,
    status             varchar(255) not null
);

create table users
(
    active        boolean      not null,
    birth_date    date,
    password_date date,
    role_id       integer      not null,
    company_id    uuid,
    id            uuid         not null
        primary key,
    bio           varchar(255),
    cpf           varchar(255) not null
        unique,
    email         varchar(255)
        unique,
    name          varchar(255) not null,
    password      varchar(255),
    phone         varchar(255),
    username      varchar(255) not null
        unique
);

create table password_recovery
(
    used       boolean      not null,
    created_at timestamp(6) not null,
    expires_at timestamp(6) not null,
    used_at    timestamp(6),
    id         uuid         not null
        primary key,
    user_id    uuid         not null
        constraint fke8rvirgchpmurh9y9sq1rkxsd
            references users,
    agent      varchar(255) not null,
    ip_address varchar(255) not null,
    token      varchar(255) not null
        unique
);

create table upsert_emails
(
    last_notification_date timestamp(6),
    request_date           timestamp(6) not null,
    id                     uuid         not null
        primary key,
    user_id                uuid         not null
        unique
        constraint fk4r2qn2aoel26lppx4p60a09sy
            references users,
    email                  varchar(255) not null
        unique,
    token                  varchar(255) not null
        unique
);
