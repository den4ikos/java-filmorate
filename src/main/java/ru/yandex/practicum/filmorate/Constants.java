package ru.yandex.practicum.filmorate;

public class Constants {
    public static final String DATE_BEFORE = "1895-12-28";
    public static final String FORMAT = "yyyy-MM-dd";
    public static final String NO_USER = "There is no any user!";
    public static final String NO_FILM = "There is no any film!";
    public static final String NO_MPA = "There is no any MPA!";
    public static final String NO_GENRE = "There is no any genre!";
    public static final String CONFIRMED_FRIEND = "confirmed";
    public static final String UNCONFIRMED_FRIEND = "unconfirmed";
    public final static String SELECT_USERS = "SELECT * FROM users ORDER BY name";
    public final static String INSERT_USER = "INSERT INTO users (name, email, login, birthday) VALUES (?, ?, ?, ?)";
    public final static String FIND_LAST_USER = "SELECT * FROM users ORDER BY id DESC LIMIT 1";
    public final static String UPDATE_USER = "UPDATE users SET name = ?, email = ?, login = ?, birthday = ? WHERE id = ?";
    public final static String FIND_USER = "SELECT * FROM users WHERE id = ? limit 1";
    public final static String DELETE_USER = "DELETE FROM users WHERE id = ?";
    public final static String ADD_FRIEND = "INSERT INTO friends SET user_id = ?, friend_id = ?";
    public final static String GET_USER_FRIENDS = "SELECT u.*, f.status FROM users u LEFT JOIN friends f ON (u.id = f.friend_id) WHERE f.user_id = ?;";
    public final static String GET_COMMON_FRIENDS = "SELECT u.* FROM users u JOIN friends u1 ON (u.id = u1.friend_id) JOIN friends u2 ON (u1.friend_id = u2.friend_id) WHERE u1.user_id = ? and u2.user_id = ?";
    public final static String DELETE_FRIENDS = "DELETE FROM friends WHERE user_id = ? and friend_id = ?";
    public final static String GET_ALL_FILMS = "SELECT f.*, mpa.name as mpa_name FROM films f LEFT JOIN motion_picture_associations mpa ON (f.mpa_id = mpa.id) ORDER BY f.id";
    public final static String ADD_FILM = "INSERT INTO films (name, description, releaseDate, duration, rate, mpa_id) VALUES(?, ?, ?, ?, ?, ?)";
    public final static String GET_FILM = "SELECT f.*, mpa.name as mpa_name FROM films f LEFT JOIN motion_picture_associations mpa ON (f.mpa_id = mpa.id) ORDER BY f.id DESC LIMIT 1";
    public final static String GET_FILM_BY_ID = "SELECT f.*, mpa.name AS mpa_name FROM films f LEFT JOIN motion_picture_associations mpa ON (f.mpa_id = mpa.id) WHERE f.id = ? LIMIT 1";
    public final static String ADD_LIKE = "INSERT INTO likes (user_id, film_id) VALUES (?, ?)";
    public final static String REMOVE_LIKE = "DELETE FROM likes WHERE user_id = ? AND film_id = ?";
    public final static String GET_GENRE_BY_ID = "SELECT * FROM genres WHERE id = ?";
    public final static String GET_ALL_GENRE = "SELECT * FROM genres ORDER BY id";
}
