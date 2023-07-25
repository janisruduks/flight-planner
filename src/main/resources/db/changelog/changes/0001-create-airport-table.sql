--liquibase formatted sql

--changeset janis:1

create table airport
(
    id      serial primary key,
    country varchar(50),
    city    varchar(50),
    airport varchar(50)
);