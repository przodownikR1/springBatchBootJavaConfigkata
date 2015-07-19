DROP TABLE person IF EXISTS;

CREATE TABLE person  (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    login VARCHAR(20),
    passwd VARCHAR(125),
    age  INTEGER
);
