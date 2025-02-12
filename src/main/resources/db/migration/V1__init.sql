CREATE SCHEMA if not exists cheatsheet;


CREATE TABLE if not exists cheatsheet.post
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    body  TEXT         NOT NULL
    );
);