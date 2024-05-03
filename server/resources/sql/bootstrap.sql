CREATE SCHEMA IF NOT EXISTS bingo;

SET SCHEMA 'bingo';

DROP TABLE IF EXISTS "user";
CREATE TABLE "user"
(
    "userName" VARCHAR(50) NOT NULL,
    "password" VARCHAR(50) NOT NULL,
    PRIMARY KEY ("userName")
);

DROP TABLE IF EXISTS player;
CREATE TABLE player
(
    "userName" VARCHAR(50) NOT NULL,
    -- TODO: add other needed columns
    PRIMARY KEY ("userName"),
    FOREIGN KEY ("userName") REFERENCES "user" ("userName") ON UPDATE CASCADE ON DELETE CASCADE
);