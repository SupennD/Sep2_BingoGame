CREATE SCHEMA IF NOT EXISTS bingo;

SET SCHEMA 'bingo';

DROP TABLE IF EXISTS "user";
CREATE TABLE "user"
(
    "userName" VARCHAR(50) NOT NULL,
    "password" VARCHAR(50) NOT NULL,
    PRIMARY KEY ("userName")
);

DROP TABLE IF EXISTS "player";
CREATE TABLE "player"
(
    "userName" VARCHAR(50) NOT NULL,
    -- TODO: add other needed columns
    PRIMARY KEY ("userName"),
    -- If a user is removed, delete the related player profile
    FOREIGN KEY ("userName") REFERENCES "user" ("userName") ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS "game";
CREATE TABLE "game"
(
    "id"     SERIAL       NOT NULL,
    "name"   VARCHAR(200) NOT NULL,
    "points" INTEGER      NOT NULL,
    PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "score";
CREATE TABLE "score"
(
    "id"        SERIAL      NOT NULL,
    "gameId"    INTEGER,
    "userName"  VARCHAR(50) NOT NULL,
    "score"     INTEGER     NOT NULL,
    "timeStamp" TIMESTAMP DEFAULT current_timestamp,
    PRIMARY KEY ("id"),
    -- Keep the score record even if a game is removed
    FOREIGN KEY ("gameId") REFERENCES "game" ("id") ON UPDATE CASCADE ON DELETE SET NULL,
    -- If a player is removed, delete all related score records
    FOREIGN KEY ("userName") REFERENCES "player" ("userName") ON UPDATE CASCADE ON DELETE CASCADE
);