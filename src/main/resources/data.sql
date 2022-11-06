delete from genres;
insert into genres (id, name) values
(1, 'Комедия'),
(2, 'Драма'),
(3, 'Action'),
(4, 'fantasy'),
(5, 'horror'),
(6, 'mystery');

delete from motion_picture_associations;
insert into motion_picture_associations (id, name, short_description) values
(1, 'G', 'У фильма нет возрастных ограничений'),
(2, 'PG', 'детям рекомендуется смотреть фильм с родителями'),
(3, 'PG-13', 'детям до 13 лет просмотр не желателен'),
(4, 'R', 'лицам до 17 лет просматривать фильм можно только в присутствии взрослого'),
(5, 'NC-17', 'лицам до 18 лет просмотр запрещён');