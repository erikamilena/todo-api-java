CREATE TABLE tasks (
    id bigserial primary key,
    category_id bigint not null references categories(id),
    title varchar(255) not null,
    state varchar(255) not null,
    created_at timestamp not null
);
