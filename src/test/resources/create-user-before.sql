DELETE FROM payments;
DELETE FROM payments_tariffs;
DELETE FROM tariffs;
DELETE FROM tokens;
DELETE FROM user_roles;
DELETE FROM user_balances;
DELETE FROM roles;
DELETE FROM users;

INSERT INTO roles(id, role_name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

INSERT INTO users(id, user_name, user_email, password, creation_date) VALUES
(1, 'admin', 'admin@admin.com', '$2a$10$EfVS7r4YFJVUKtoKtipoAuuj.e6z7ed/nEDNGrXB2z6M52d9zmtkW', current_date),
(2, 'user', 'user@user.com', '$2a$10$7JGsM41kbXX7/vJ2lc3pb.wdoIoANWTme.NErCU2TSv1RcPnDaBaS', current_date);

INSERT INTO user_roles(user_id, role_id) VALUES
(1, 1), (1, 2),
(2, 2);