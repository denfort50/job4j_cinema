CREATE TABLE IF NOT EXISTS sessions (
    id SERIAL PRIMARY KEY,
    name text UNIQUE,
    photo BYTEA
);