create table cars
(
    id                 varchar(255) not null
        constraint car_pkey
            primary key,
    owner              varchar(255),
    created_at         timestamp(6),
    registration_plate varchar(255)
);

alter table cars
    owner to admin;

