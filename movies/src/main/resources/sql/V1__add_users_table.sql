CREATE TABLE public.users
(
    id bigserial,
    name character varying,
    age integer,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;