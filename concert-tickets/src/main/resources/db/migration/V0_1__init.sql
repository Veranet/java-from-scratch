CREATE TYPE status AS ENUM ('ACTIVATED', 'INACTIVATED');

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       user_name VARCHAR(100) NOT NULL,
                       creation_date TIMESTAMP NOT NULL,
                       status STATUS NOT NULL
);

CREATE TYPE ticket_type AS ENUM ('DAY', 'WEEK', 'MONTH', 'YEAR');

CREATE TABLE ticket (
                        id SERIAL PRIMARY KEY,
                        user_id INT NOT NULL,
                        ticket_type TICKET_TYPE NOT NULL,
                        creation_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_ticket_user FOREIGN KEY (user_id) REFERENCES users (id)
);