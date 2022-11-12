CREATE TABLE IF NOT EXISTS sessions (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE,
    photo BYTEA
);

COMMENT ON TABLE sessions IS 'Сеансы';
COMMENT ON COLUMN sessions.id IS 'Идентификатор сеанса';
COMMENT ON COLUMN sessions.name IS 'Название сеанса';
COMMENT ON COLUMN sessions.photo IS 'Постер сеанса';