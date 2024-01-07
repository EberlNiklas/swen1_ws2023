CREATE DATABASE mctgdb;

\c mctgdb;


CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE ,
    password VARCHAR(255) NOT NULL,
    points INT DEFAULT 100,
    coins INT DEFAULT 20,
    deck_id VARCHAR(255)
    );



CREATE TABLE IF NOT EXISTS packages (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS card (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    damage VARCHAR(255),
    package_id VARCHAR(255) NOT NULL REFERENCES packages(id)
);




DROP TABLE card, packages, users





