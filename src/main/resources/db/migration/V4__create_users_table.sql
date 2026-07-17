CREATE TABLE users (
    id bigserial primary key,
    name varchar(255) not null,
    email varchar(255) not null unique
);
