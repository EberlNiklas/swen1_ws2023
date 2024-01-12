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
    package_id VARCHAR(255) REFERENCES packages(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS stack (
    card_id VARCHAR(255),
    user_id VARCHAR(255),
    PRIMARY KEY (card_id, user_id),
    FOREIGN KEY (card_id) REFERENCES card(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS deck (
    deck_id VARCHAR(255) PRIMARY KEY,
    card_id VARCHAR(255),
    user_id VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (card_id) REFERENCES card(id)
);


DROP TABLE card, packages, users, stack, deck





