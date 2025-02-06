CREATE SCHEMA if not exists cheatsheet;

create table if not exists cheatsheet.message
(
    id                              SERIAL PRIMARY KEY,
    content                         varchar(255)
);

INSERT INTO cheatsheet.message (content) VALUES ('Random message 1');
INSERT INTO cheatsheet.message (content) VALUES ('Random message 2');
INSERT INTO cheatsheet.message (content) VALUES ('Random message 3');
INSERT INTO cheatsheet.message (content) VALUES ('Random message 4');
INSERT INTO cheatsheet.message (content) VALUES ('Random message 5');
