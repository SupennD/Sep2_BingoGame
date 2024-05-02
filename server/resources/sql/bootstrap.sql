CREATE SCHEMA IF NOT EXISTS bingo;

SET SCHEMA 'bingo';

DROP TABLE IF EXISTS "user";
CREATE TABLE "user"
(
    "id"       SERIAL      NOT NULL,
    "userName" VARCHAR(50) NOT NULL,
    "password" VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS player;
CREATE TABLE player
(
    "userId" INTEGER NOT NULL,
    -- TODO: add other needed columns
    PRIMARY KEY ("userId"),
    FOREIGN KEY ("userId") REFERENCES "user" ("id") ON UPDATE CASCADE ON DELETE CASCADE
);