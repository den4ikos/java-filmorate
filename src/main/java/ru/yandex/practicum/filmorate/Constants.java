package ru.yandex.practicum.filmorate;

public class Constants {
    public static final String DATE_BEFORE = "1895-12-28";
    public static final String FORMAT = "yyyy-MM-dd";
    public static final String NO_USER = "There is no any user!";
    public static final String CONFIRMED_FRIEND = "confirmed";
    public static final String UNCONFIRMED_FRIEND = "unconfirmed";
    public final static String SELECT_USERS = "select * from users order by name";
    public final static String INSERT_USER = "insert into users (name, email, login, birthday) values (?, ?, ?, ?)";
    public final static String FIND_LAST_USER = "select * from users order by id desc limit 1";
    public final static String UPDATE_USER = "update users set name = ?, email = ?, login = ?, birthday = ? where id = ?";
    public final static String FIND_USER = "select * from users where id = ? limit 1";
    public final static String DELETE_USER = "delete from users where id = ?";
    public final static String ADD_FRIEND = "insert into friends set user_id = ?, friend_id = ?, friend_status_id = ?";
}
