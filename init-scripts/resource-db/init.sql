CREATE SEQUENCE IF NOT EXISTS resource_table_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE resource_table
(
    id   INTEGER NOT NULL,
    data BYTEA,
    CONSTRAINT pk_resource_table PRIMARY KEY (id)
);