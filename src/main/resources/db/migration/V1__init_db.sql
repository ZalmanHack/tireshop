create table booked_option
(
    id                int8 generated by default as identity,
    name              varchar(32),
    booked_service_id int8,
    booked_value_id   int8,
    primary key (id)
);
create table booked_service
(
    id                 int8 generated by default as identity,
    interval_to_order  int8,
    name               varchar(32),
    date_of_end_work   TIMESTAMP,
    date_of_start_work TIMESTAMP,
    booking_id         int8,
    primary key (id)
);
create table booked_value
(
    id       int8 generated by default as identity,
    duration int8,
    price    int8 not null check (price >= 0),
    value    varchar(32),
    primary key (id)
);
create table booking
(
    id                int8 generated by default as identity check (id >= 0),
    appointment_date  TIMESTAMP,
    closed_date       TIMESTAMP,
    order_status      int4,
    price             int8 not null,
    registration_date TIMESTAMP,
    car_id            int8,
    usr_id            int8,
    primary key (id)
);
create table car
(
    id            int8 generated by default as identity check (id >= 0),
    model         varchar(64),
    removed       boolean not null,
    state_number  varchar(16),
    tire_diameter float8  not null,
    color_id      int8,
    usr_id        int8,
    primary key (id)
);
create table color
(
    id        int8 generated by default as identity check (id >= 0),
    hex_color varchar(6),
    primary key (id)
);
create table template_option
(
    id                  int8 generated by default as identity,
    name                varchar(32),
    required            boolean not null,
    view_type           int4,
    template_service_id int8,
    primary key (id)
);
create table template_service
(
    id                int8 generated by default as identity,
    interval_to_order int8,
    name              varchar(32),
    primary key (id)
);
create table template_value
(
    id                 int8 generated by default as identity,
    duration           int8,
    price              int8 not null check (price >= 0),
    value              varchar(32),
    template_option_id int8,
    primary key (id)
);
create table timetable
(
    id                     int8 generated by default as identity check (id >= 0),
    time_of_break_end      TIME,
    time_of_break_start    TIME,
    changed_work_time_date TIMESTAMP,
    time_of_close          TIME,
    time_of_open           TIME,
    primary key (id)
);
create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);
create table usr
(
    id              int8 generated by default as identity check (id >= 0),
    activation_code varchar(255),
    active          boolean not null,
    email           varchar(320),
    first_name      varchar(26),
    last_name       varchar(26),
    new_email       varchar(320),
    password        varchar(255),
    phone           int8    not null check (phone >= 100),
    rating          int2    not null,
    username        varchar(64),
    primary key (id)
);
alter table if exists booked_option
    add constraint FK1gj1ca1r6lgshbhpg7ksjjpm4 foreign key (booked_service_id) references booked_service;
alter table if exists booked_option
    add constraint FKa4r8ops89uys0tyccpdx9hr1h foreign key (booked_value_id) references booked_value;
alter table if exists booked_service
    add constraint FKhsrm66qiu69xvk5xyi5jo9pdr foreign key (booking_id) references booking;
alter table if exists booking
    add constraint FKd9p8qdy5sj4ym0bmksdx7yrwj foreign key (car_id) references car;
alter table if exists booking
    add constraint FK9dyabyqeaxjuw6dkh8593n02h foreign key (usr_id) references usr;
alter table if exists car
    add constraint FKg0jvjcwntclcjd9cd2vmta820 foreign key (color_id) references color;
alter table if exists car
    add constraint FK67c9yy5qkq2c92isac5tlmmsl foreign key (usr_id) references usr;
alter table if exists template_option
    add constraint FKif0pc2662uhsa79schs2s9sl9 foreign key (template_service_id) references template_service;
alter table if exists template_value
    add constraint FKngorufsohn9hti0hm8nwnigtl foreign key (template_option_id) references template_option;
alter table if exists user_role
    add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr;