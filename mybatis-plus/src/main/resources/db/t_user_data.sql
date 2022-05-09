DELETE FROM t_user;

INSERT INTO t_user (id, user_name, age, email,is_deleted,create_time) VALUES
(1, 'Jone', 18, 'test1@baomidou.com',1,'2022-05-09 09:50:55'),
(2, 'Jack', 20, 'test2@baomidou.com',1,'2022-05-09 09:50:55'),
(3, 'Tom', 28, 'test3@baomidou.com',1,'2022-05-09 09:50:55'),
(4, 'Sandy', 21, 'test4@baomidou.com',1,'2022-05-09 09:50:55'),
(5, 'Billie', 24, 'test5@baomidou.com',0,'2022-05-09 09:50:55');