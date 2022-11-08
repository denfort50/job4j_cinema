CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL UNIQUE,
    email VARCHAR NOT NULL UNIQUE,
    phone VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);

COMMENT ON TABLE users IS 'Пользователи';
COMMENT ON COLUMN users.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN users.username IS 'Имя пользователя';
COMMENT ON COLUMN users.email IS 'Email пользователя';
COMMENT ON COLUMN users.phone IS 'Номер телефона пользователя';
COMMENT ON COLUMN users.password IS 'Пароль пользователя';