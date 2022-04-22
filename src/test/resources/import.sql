create table book
(
    id          serial      not null
        constraint pk_38
            primary key,
    name        varchar(50) not null,
    author      varchar(50),
    description varchar(50),
    price       real,
    pic_path    varchar(50),
    genre       varchar(50) not null
);

create table person
(
    id         serial      not null
        constraint pk_6
            primary key,
    first_name varchar(50),
    last_name  varchar(50),
    locked     boolean,
    login      varchar(50) not null,
    password   varchar(50) not null,
    email      varchar(50),
    role       varchar(50) not null
);

create table user_order
(
    id           serial      not null
        constraint pk_67
            primary key,
    date         varchar(50),
    person_id    bigint      not null
        constraint fk_70
            references person,
    order_status varchar(50) not null
);

create table order_book
(
    id       serial not null
        constraint pk_75
            primary key,
    book_id  bigint not null
        constraint fk_81
            references book,
    order_id bigint not null
        constraint fk_78
            references user_order,
    quantity bigint
);