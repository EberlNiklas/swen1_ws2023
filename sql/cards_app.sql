CREATE DATABASE mctgdb;

\c mctgdb;

CREATE TABLE IF NOT EXISTS card (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    done BOOLEAN
    );