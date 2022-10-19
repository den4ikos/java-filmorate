package ru.yandex.practicum.filmorate.storage;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component("dbUserStorage")
@Primary
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Map<Long, User> get() {
        String query = "select * from users order by name";
        return jdbcTemplate
                .query(query, new UserRowMapper())
                .stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
    }

    @Override
    public User add(User user) {
        jdbcTemplate.update(
                "insert into users (name, email, login, birthday) values (?, ?, ?, ?)",
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday());
        return user;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(Long userId) {

    }

    private User makeUser(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("name");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();

        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setLogin(login);
        user.setName(name);
        user.setBirthday(birthday);

        return user;
    }
}
