CREATE SEQUENCE IF NOT EXISTS song_metadata_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE song_metadata
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    album VARCHAR(255),
    length VARCHAR(255),
    year INT,
    resource_id INT
);