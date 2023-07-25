--liquibase formatted sql

--changeset janis:2

create table flight
(
    id             serial primary key,
    from_id        int references airport (id),
    to_id          int references airport (id),
    carrier_name   varchar(100) not null,
    departure_time timestamp    not null,
    arrival_time   timestamp    not null
);