create table item
(
    id              bigint auto_increment,
    user_id         bigint not null,
    name            varchar(255),
    estimated_value numeric,
    status          varchar(255),
    primary key (id)
);

create table users
(
    id            bigint auto_increment primary key,
    date_of_birth date,
    name          varchar(255),
    status        varchar(255)
);

INSERT INTO users (id, date_of_birth, name, status)
VALUES (1, '1977-08-02', 'Wanderson', 'DISABLED');

