create table item (
    id bigint auto_increment,
    user_id bigint not null,
    name varchar(255),
    estimated_value numeric,
    status   varchar(255),
    primary key (id)
);
