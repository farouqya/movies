-- Table: public.movies

-- DROP TABLE IF EXISTS public.movies;

CREATE TABLE IF NOT EXISTS public.bkp_movies
(
    movie_id bigint NOT NULL DEFAULT nextval('movies_movie_id_seq'::regclass),
    title text COLLATE pg_catalog."default" NOT NULL DEFAULT 'No Book'::text,
    year integer,
    genre text COLLATE pg_catalog."default" NOT NULL,
    director text COLLATE pg_catalog."default",
    rating double precision,
    length interval,
    CONSTRAINT bkp_movies_pkey PRIMARY KEY (movie_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.bkp_movies
    OWNER to postgres;


-- Table: public.actors

-- DROP TABLE IF EXISTS public.actors;

CREATE TABLE IF NOT EXISTS public.bkp_actors
(
    id bigint NOT NULL DEFAULT nextval('actors_actors_id_seq'::regclass),
    name text COLLATE pg_catalog."default" NOT NULL,
    age integer,
    gender text COLLATE pg_catalog."default",
    nationality text COLLATE pg_catalog."default",
    movie_table_id bigint NOT NULL,
    CONSTRAINT bkp_actors_pkey PRIMARY KEY (id),
    CONSTRAINT actors_movie_table_id_fkey FOREIGN KEY (movie_table_id)
        REFERENCES public.bkp_movies (movie_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.bkp_actors
    OWNER to postgres;