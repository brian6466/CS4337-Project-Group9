CREATE DATABASE IF NOT EXISTS contentapi;

USE contentapi;

CREATE TABLE IF NOT EXISTS article (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(255) NOT NULL, -- article title
    content TEXT NOT NULL, -- article content
    author_id BINARY(16) NOT NULL, -- would be user but can't reference user table in userApi
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS share (
    id BINARY(16) PRIMARY KEY,
    article_id BINARY(16) NOT NULL,
    platform ENUM('FACEBOOK', 'TWITTER') NOT NULL,
    shared_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (article_id) REFERENCES article(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS comment (
    id BINARY(16) PRIMARY KEY,
    article_id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (article_id) REFERENCES article(id) ON DELETE CASCADE
);