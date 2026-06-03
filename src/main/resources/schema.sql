drop table if exists addenda;
drop table if exists forms;
drop table if exists users;
drop table if exists accounts;

create table if not exists accounts (
                                        id uuid primary key,
                                        display_name text not null,
                                        account_type text not null,
                                        created_at timestamp not null,
                                        updated_at timestamp not null
);

create table if not exists users (
                                     id uuid primary key,
                                     account_id uuid not null references accounts(id),
                                     display_name text not null,
                                     email text not null,
                                     role text not null
);

create table if not exists forms (
                                     id uuid primary key,
                                     account_id uuid not null references accounts(id),
                                     form_type text not null,
                                     form_title text not null,
                                     created_at timestamp not null,
                                     updated_at timestamp not null
);

create table if not exists addenda (
                                      id uuid primary key,
                                      form_id uuid not null references forms(id),
                                      addendum_title text not null,
                                      addendum_type text not null,
                                      addendum_text text not null
);
