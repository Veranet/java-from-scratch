CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    creation_date TIMESTAMP NOT NULL
);

CREATE TYPE ticket_type AS ENUM ('DAY', 'WEEK', 'MONTH', 'YEAR');
CREATE TYPE sector AS ENUM ('A', 'B', 'C');

CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users (id),
    ticket_type TICKET_TYPE NOT NULL,
    stadium_sector SECTOR NOT NULL,
    creation_date TIMESTAMP NOT NULL
);

CREATE TYPE ROLE AS ENUM ('ADMIN', 'CLIENT');

CREATE TABLE user_role (
   user_id INT REFERENCES users (id),
   role ROLE NOT NULL
);

INSERT INTO users (user_name, creation_date)
values ('Ivan', '2025-01-02'),
('Alex', '2024-01-02'),
('Bob', '2023-01-02');

INSERT INTO ticket (user_id, ticket_type, stadium_sector, creation_date)
VALUES (1, 'DAY', 'A', '2024-06-30 10:00:00'),
(3, 'DAY', 'A', '2024-06-30 10:00:00'),
(2, 'WEEK', 'A', '2024-06-30 10:00:00'),
(1, 'MONTH', 'C', '2024-06-30 10:00:00');

INSERT INTO user_role(user_id, role)
values
(1, 'ADMIN'),
(2, 'CLIENT'),
(3, 'CLIENT');