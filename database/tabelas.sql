create table addresses
(
    id           uuid         not null,
    city         varchar(255) not null,
    complement   varchar(255),
    country      varchar(255) not null,
    neighborhood varchar(255) not null,
    number       varchar(255) not null,
    state        varchar(255) not null,
    street       varchar(255) not null,
    zip_code     varchar(255) not null
);

alter table addresses
    add constraint addresses_pkey
        primary key (id);

create table companies
(
    address_id  uuid,
    id          uuid         not null,
    owner_id    uuid         not null,
    cnpj        varchar(255) not null,
    description varchar(255),
    name        varchar(255) not null
);

alter table companies
    add constraint companies_pkey
        primary key (id);

alter table companies
    add constraint companies_address_id_key
        unique (address_id);

alter table companies
    add constraint companies_cnpj_key
        unique (cnpj);

alter table companies
    add constraint fk8w70yf6urddd0ky7ev90okenf
        foreign key (address_id) references addresses;

create table email_attachments
(
    email_id    bigint not null,
    attachments varchar(255)
);

create table emails
(
    failed_attempts integer      not null,
    sent            boolean      not null,
    created_at      timestamp(6) not null,
    id              bigserial,
    sent_at         timestamp(6),
    body            text         not null,
    subject         varchar(255) not null,
    target          varchar(255) not null
);

alter table emails
    add constraint emails_pkey
        primary key (id);

alter table email_attachments
    add constraint fk753q149o0k6fjxh2fji1616ua
        foreign key (email_id) references emails;

create table enrollments
(
    birth_date date         not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6),
    event_id   uuid         not null,
    id         uuid         not null,
    user_id    uuid,
    document   varchar(255) not null,
    email      varchar(255) not null,
    name       varchar(255) not null,
    status     varchar(255) not null
);

alter table enrollments
    add constraint enrollments_pkey
        primary key (id);

create table event_attachments
(
    event_id         uuid not null,
    attachment_paths varchar(255)
);

create table event_configurations
(
    event_id uuid         not null,
    key      varchar(255) not null,
    value    varchar(255)
);

alter table event_configurations
    add constraint event_configurations_pkey
        primary key (event_id, key);

create table events
(
    end_date           date         not null,
    init_date          date         not null,
    status             integer      not null,
    address_id         uuid,
    company_id         uuid         not null,
    event_thumbnail_id uuid,
    id                 uuid         not null,
    description        varchar(1000),
    name               varchar(255) not null
);

alter table events
    add constraint events_pkey
        primary key (id);

alter table event_attachments
    add constraint fko49p20umx9s78ww9w2h91dcao
        foreign key (event_id) references events;

alter table event_configurations
    add constraint fk33jpq0j7g5hn9x03pi9y8qh5q
        foreign key (event_id) references events;

alter table events
    add constraint events_address_id_key
        unique (address_id);

alter table events
    add constraint events_event_thumbnail_id_key
        unique (event_thumbnail_id);

alter table events
    add constraint fkquc7xx27bo60lupj2rf7e0hn2
        foreign key (address_id) references addresses;

create table events_thumbnails
(
    uploaded_at timestamp(6) not null,
    id          uuid         not null,
    filename    varchar(255) not null
);

alter table events_thumbnails
    add constraint events_thumbnails_pkey
        primary key (id);

alter table events
    add constraint fk32u9t33qldisvv10fq4kffp7w
        foreign key (event_thumbnail_id) references events_thumbnails;

create table messages
(
    id       serial,
    subject  integer not null,
    type     char    not null,
    template text    not null
);

alter table messages
    add constraint messages_pkey
        primary key (id);

create table password_recovery
(
    used       boolean      not null,
    created_at timestamp(6) not null,
    expires_at timestamp(6) not null,
    used_at    timestamp(6),
    id         uuid         not null,
    user_id    uuid         not null,
    agent      varchar(255) not null,
    ip_address varchar(255) not null,
    token      varchar(255) not null
);

alter table password_recovery
    add constraint password_recovery_pkey
        primary key (id);

alter table password_recovery
    add constraint password_recovery_token_key
        unique (token);

create table payments
(
    id                 bigint       not null,
    payment_date       timestamp(6) not null,
    external_reference varchar(255) not null,
    status             varchar(255) not null
);

alter table payments
    add constraint payments_pkey
        primary key (id);

create table ticket_sale
(
    active      boolean        not null,
    entries     integer        not null,
    price       numeric(38, 2) not null,
    event_id    uuid           not null,
    id          uuid           not null,
    description varchar(255)   not null,
    name        varchar(255)   not null
);

alter table ticket_sale
    add constraint ticket_sale_pkey
        primary key (id);

create table tickets
(
    expired_in         date         not null,
    valid_in           date         not null,
    created_at         timestamp(6) not null,
    last_time_consumed timestamp(6),
    event_id           uuid         not null,
    id                 uuid         not null,
    ticket_sale_id     uuid         not null,
    user_id            uuid,
    code               varchar(255) not null,
    description        varchar(255) not null,
    document           varchar(255) not null,
    status             varchar(255) not null
);

alter table tickets
    add constraint tickets_pkey
        primary key (id);

create table upsert_emails
(
    last_notification_date timestamp(6),
    request_date           timestamp(6) not null,
    id                     uuid         not null,
    user_id                uuid         not null,
    email                  varchar(255) not null,
    token                  varchar(255) not null
);

alter table upsert_emails
    add constraint upsert_emails_pkey
        primary key (id);

alter table upsert_emails
    add constraint upsert_emails_user_id_key
        unique (user_id);

alter table upsert_emails
    add constraint upsert_emails_email_key
        unique (email);

alter table upsert_emails
    add constraint upsert_emails_token_key
        unique (token);

create table upsert_enrollments
(
    birth_date     date         not null,
    event_id       uuid         not null,
    id             uuid         not null,
    ticket_id      uuid         not null,
    ticket_sale_id uuid         not null,
    user_id        uuid,
    document       varchar(255) not null,
    email          varchar(255) not null,
    name           varchar(255) not null
);

alter table upsert_enrollments
    add constraint upsert_enrollments_pkey
        primary key (id);

create table users
(
    active        boolean      not null,
    birth_date    date,
    password_date date,
    role_id       integer      not null,
    company_id    uuid,
    id            uuid         not null,
    bio           varchar(255),
    document      varchar(255) not null,
    email         varchar(255),
    name          varchar(255) not null,
    password      varchar(255),
    phone         varchar(255),
    username      varchar(255) not null
);

alter table users
    add constraint users_pkey
        primary key (id);

alter table password_recovery
    add constraint fke8rvirgchpmurh9y9sq1rkxsd
        foreign key (user_id) references users;

alter table upsert_emails
    add constraint fk4r2qn2aoel26lppx4p60a09sy
        foreign key (user_id) references users;

alter table users
    add constraint users_document_key
        unique (document);

alter table users
    add constraint users_email_key
        unique (email);

alter table users
    add constraint users_username_key
        unique (username);