CREATE SCHEMA if not exists cheatsheet;


CREATE TABLE if not exists cheatsheet.post
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    body  TEXT         NOT NULL
    );
);

insert into cheatsheet.post (id, title, body) values (1000,'Post Test Title 1000', 'Post Test Body 1000');