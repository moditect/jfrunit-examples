create schema todo;

create table if not exists test_user
(
    id       bigserial primary key,
    username text not null,
    age      int  not null
);
