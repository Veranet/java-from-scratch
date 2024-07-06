CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    creation_date TIMESTAMP NOT NULL
);

CREATE TYPE ticket_type AS ENUM ('DAY', 'WEEK', 'MONTH', 'YEAR');

CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users (id),
    ticket_type TICKET_TYPE NOT NULL,
    creation_date TIMESTAMP NOT NULL
);

INSERT INTO users (user_name, creation_date)
values ('Ivan', '2025-01-02'),
('Alex', '2024-01-02'),
('Bob', '2023-01-02');

INSERT INTO ticket (user_id, ticket_type, creation_date)
VALUES (1, 'DAY', '2024-06-30 10:00:00'),
(3, 'DAY', '2024-06-30 10:00:00'),
(2, 'WEEK', '2024-06-30 10:00:00'),
(1, 'MONTH', '2024-06-30 10:00:00');