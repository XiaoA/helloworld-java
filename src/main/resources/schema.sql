drop table if exists accounts;

create table if not exists accounts (
                                        id uuid primary key,
                                        display_name text not null,
                                        account_type text not null,
                                        created_at timestamp not null,
                                        updated_at timestamp not null
);