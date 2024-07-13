TRUNCATE users RESTART IDENTITY CASCADE ;
TRUNCATE ticket CASCADE;

INSERT INTO users (user_name, creation_date, status)
values ('Ivan', '2025-01-02', 'ACTIVATED'),
       ('Alex', '2024-01-02', 'ACTIVATED'),
       ('Bob', '2023-01-02', 'ACTIVATED');

INSERT INTO ticket (user_id, ticket_type, creation_date)
VALUES (1, 'DAY', '2024-06-30 10:00:00'),
       (3, 'DAY', '2024-06-30 10:00:00'),
       (2, 'WEEK', '2024-06-30 10:00:00'),
       (1, 'MONTH', '2024-06-30 10:00:00');
