### Exact SQL Scripts --> 
### CRUID Operations except of Create as we created the table manually via table..
1. SELECT --> "movies and actors DBs"
   SELECT actors_id, name, age, gender, nationality, movie_table_id
   FROM public.actors;
   SELECT movie_id, title, year, genre, director, rating, length
   FROM public.movies;

2. INSERT --> "movies and actors DBs"
   INSERT INTO public.movies(
   movie_id, title, year, genre, director, rating, length)
   VALUES (?, ?, ?, ?, ?, ?, ?);
   INSERT INTO public.actors(
   actors_id, name, age, gender, nationality, movie_table_id)
   VALUES (?, ?, ?, ?, ?, ?);
   For instance -->
   INSERT INTO public.movies(
   title, year, genre, director, rating, length)
   VALUES ('Gravity', 2013, 'THRILLER', 'Alfonso Cuaron', 7.7, INTERVAL '1' HOUR + INTERVAL '31' MINUTE);

3. UPDATE --> "movies and actors DBs"
   UPDATE public.movies
   SET title=?, year=?, genre=?, director=?, rating=?, length=?
   WHERE <condition>;
   UPDATE public.actors
   SET name=?, age=?, gender=?, nationality=?, movie_table_id=?
   WHERE <condition>;
   For instance -->
   UPDATE public.actors
   SET name= Jim Carrey
   WHERE actors_id = 4;

4. DELETE --> "movies and actors DBs"
   DELETE FROM public.movies
   WHERE <condition>;
   DELETE FROM public.actors
   WHERE <condition>;
   For instance -->
   DELETE FROM public.actors
   WHERE actors_id = 7;

### Images from movies table for the query
![img.png](img.png)
### Images from actors table for the query
![img_1.png](img_1.png)

During the addition of the entries in the DB, I used the SELECT, DELETE as well..
This is how it looks like at the end.. :)))

