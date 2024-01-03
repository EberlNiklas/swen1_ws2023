CREATE DATABASE mctgdb;

\c mctgdb;

CREATE TABLE IF NOT EXISTS card (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    done BOOLEAN
    );

CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE ,
    password VARCHAR(255) NOT NULL,
    points INT DEFAULT 100,
    coins INT DEFAULT 20,
    deck_id VARCHAR(255)
    );




