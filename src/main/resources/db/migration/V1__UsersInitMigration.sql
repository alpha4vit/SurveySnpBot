create table if not exists users
(
    chat_id    bigint primary key,
    created_at timestamp not null,
    updated_at timestamp not null
)