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
    public final static String ADD_FRIEND = "insert into friends set user_id = ?, friend_id = ?";
    public final static String GET_USER_FRIENDS = "select u.*, f.status from users u left join friends f on (u.id = f.friend_id) where f.user_id = ?;";
    public final static String GET_COMMON_FRIENDS = "select u.* from users u join friends u1 on (u.id = u1.friend_id) join friends u2 on (u1.friend_id = u2.friend_id) where u1.user_id = ? and u2.user_id = ?";
    public final static String DELETE_FRIENDS = "delete from friends where (user_id = ? and friend_id = ?) or (user_id = ? and friend_id = ?)";
}
