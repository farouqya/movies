Do
$$
    Begin
        ALTER TABLE IF EXISTS public.movies ADD COLUMN "added_by" text;
        ALTER TABLE IF EXISTS public.movies ADD COLUMN "modified_by" text;
        ALTER TABLE IF EXISTS public.movies ADD COLUMN "created_at" timestamp without time zone;
        ALTER TABLE IF EXISTS public.movies ADD COLUMN "modified_at" timestamp without time zone;

        ALTER TABLE IF EXISTS public.actors ADD COLUMN "added_by" text;
        ALTER TABLE IF EXISTS public.actors ADD COLUMN "modified_by" text;
        ALTER TABLE IF EXISTS public.actors ADD COLUMN "created_at" timestamp without time zone;
        ALTER TABLE IF EXISTS public.actors ADD COLUMN "modified_at" timestamp without time zone;

    EXCEPTION
        WHEN undefined_column THEN RAISE NOTICE 'column another_table.not_exist does not exist';
    END
$$;
