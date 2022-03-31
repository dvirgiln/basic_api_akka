CREATE TABLE IF NOT EXISTS postman (
    id NUMERIC PRIMARY KEY,
    url VARCHAR
);


CREATE TABLE IF NOT EXISTS postman_header (
    id NUMERIC PRIMARY KEY,
    postman_id NUMERIC,
    key VARCHAR,
    value VARCHAR,
    CONSTRAINT fk_postman FOREIGN KEY(postman_id) REFERENCES postman(id)
);

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START 1;
