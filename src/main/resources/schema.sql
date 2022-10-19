CREATE TABLE IF NOT EXISTS films (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    mpa_id INTEGER,
    name VARCHAR(200),
    description text DEFAULT NULL,
    duration INTEGER
);

CREATE TABLE IF NOT EXISTS users (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email VARCHAR(200) NOT NULL,
    login VARCHAR(200) NOT NULL,
    name VARCHAR(200) NOT NULL,
    birthday TIMESTAMP DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS friends (
    user_id INTEGER NOT NULL,
    friend_status_id INTEGER NOT NULL,
    friend_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS likes (
    user_id INTEGER NOT NULL,
    film_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS genres (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS film_genre (
    film_id INTEGER NOT NULL,
    genre_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS motion_picture_assocations (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    short_description VARCHAR(255) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS friend_statuses (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    short_description VARCHAR(255) DEFAULT NULL
);

ALTER TABLE films DROP CONSTRAINT IF EXISTS fk_films_mpa_id;
ALTER TABLE films ADD CONSTRAINT fk_films_mpa_id FOREIGN KEY(mpa_id) REFERENCES motion_picture_assocations (id);

ALTER TABLE friends DROP CONSTRAINT IF EXISTS fk_friends_user_id;
ALTER TABLE friends ADD CONSTRAINT fk_friends_user_id FOREIGN KEY(user_id) REFERENCES users (id);

ALTER TABLE friends DROP CONSTRAINT IF EXISTS fk_friends_friend_status_id;
ALTER TABLE friends ADD CONSTRAINT fk_friends_friend_status_id FOREIGN KEY(friend_status_id) REFERENCES friend_statuses (id);

ALTER TABLE likes DROP CONSTRAINT IF EXISTS fk_likes_user_id;
ALTER TABLE likes ADD CONSTRAINT fk_likes_user_id FOREIGN KEY(user_id) REFERENCES users (id);

ALTER TABLE likes DROP CONSTRAINT IF EXISTS fk_likes_film_id;
ALTER TABLE likes ADD CONSTRAINT fk_likes_film_id FOREIGN KEY(film_id) REFERENCES films (id);

ALTER TABLE film_genre DROP CONSTRAINT IF EXISTS fk_film_genre_film_id;
ALTER TABLE film_genre ADD CONSTRAINT fk_film_genre_film_id FOREIGN KEY(film_id) REFERENCES films (id);

ALTER TABLE film_genre DROP CONSTRAINT IF EXISTS fk_film_genre_genre_id;
ALTER TABLE film_genre ADD CONSTRAINT fk_film_genre_genre_id FOREIGN KEY(genre_id) REFERENCES genres (id);