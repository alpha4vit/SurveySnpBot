create table if not exists survey
(
    id         uuid primary key,
    chat_id    bigint references users (chat_id) not null,
    name       text,
    email      text,
    score      bigint,
    state      text                              not null,
    created_at timestamp                         not null,
    updated_at timestamp                         not null
)