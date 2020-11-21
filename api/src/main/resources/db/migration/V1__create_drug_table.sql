create sequence drug_sequence start with 1 increment by 1;

create table drug (
    id bigint primary key default nextval('drug_sequence'),
    name varchar(1000) not null,
    type varchar(500),
    description text,
    price numeric(10, 2) not null,
    store_url text not null,
    image_url text
)
