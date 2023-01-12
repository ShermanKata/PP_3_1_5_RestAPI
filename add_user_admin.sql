insert into users (age, email, lastname, password, username)
    values (30, 'user@email.ru', 'user_lastname',
            '$2a$12$w57cFmyOOYuEbx11OAkPjOLF4ht9A2bkZ1wCaknB5yw8Ky3ULpok6', 'user');

insert into users (age, email, lastname, password, username)
values (35, 'admin@email.ru', 'admin_lastname',
        '$2a$12$wD14LYx9XWN6pJR1w5rV8.pQCMvznjnX1/hAmN7B6uqDlg1v8ww6G', 'admin');

insert into roles (name) value ('ROLE_USER');
insert into roles (name) value ('ROLE_ADMIN');

insert into users_roles (user_id, role_id) VALUES (1, 1);
insert into users_roles (user_id, role_id) VALUES (2, 1);
insert into users_roles (user_id, role_id) VALUES (2, 2);
