CREATE TYPE payment_type AS ENUM ('ONLINE', 'CASH');

create table if not exists orders
(
    id           uuid default gen_random_uuid() not null
        constraint orders_pk
            primary key,
    state        varchar                        not null,
    payment_type payment_type not null
);

alter table orders
    owner to myuser;

