--these script are for demo purposes only, run once to create sample data in the DB
-----------start: This line of code just to prevent error when starting the application-----------------
select * from users;
-----------end of lines-------------------

-----------start: run once to create the table you need in the docker mysql db-----------------

-- CREATE TABLE orders (
--                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
--                         user_id BIGINT NOT NULL,
--                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
--                         total_price DOUBLE NOT NULL,
--                         status ENUM('PENDING', 'CONFIRMED') NOT NULL
-- );

-- CREATE TABLE shipments (
--                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
--                            product_id BIGINT NOT NULL,
--                            price DOUBLE NOT NULL,
--                            quantity BIGINT NOT NULL,
--                            status VARCHAR(255) NOT NULL,
--                            order_id BIGINT NOT NULL,
--                            CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id)
-- );

----------end of lines-------------------

-----------start: run below query to renew the data to the initial state that require to run the application-----------------

-- truncate table `users`;
--
-- insert into `users` (`id`, `username`, `password`, `full_name`, `enabled`, `role`) values(1, 'user', '{bcrypt}$2a$10$IeofhAYT3lUfrF0bi1aflOat.IU3xOkZWaAWAuVc9jO2.QxTtH4RO', 'User', 1, 'USER');
--
-- alter table `users` AUTO_INCREMENT = 2;
--
-- SET FOREIGN_KEY_CHECKS = 0;
--
-- TRUNCATE TABLE `orders`;
--
-- INSERT INTO orders (id, user_id, total_price, created_at)
-- VALUES (1, 1, 20.00, CURRENT_TIMESTAMP);
--
-- ALTER TABLE `orders` AUTO_INCREMENT = 2;
--
-- SET FOREIGN_KEY_CHECKS = 1;

-- SET FOREIGN_KEY_CHECKS = 0;
--
-- TRUNCATE TABLE `shipments`;
--
-- INSERT INTO shipments (product_id, price, quantity, status, order_id)
-- VALUES (1001, 50.0, 2, 'PENDING', 1);
--
-- SET FOREIGN_KEY_CHECKS = 1;

-- CREATE TABLE payment_status (
--                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,
--                                 payment_id VARCHAR(100) NOT NULL UNIQUE,
--                                 status VARCHAR(50) NOT NULL,
--                                 amount DOUBLE NOT NULL,
--                                 currency VARCHAR(20) NOT NULL,
--                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
-- );

-----------end of lines-----------------