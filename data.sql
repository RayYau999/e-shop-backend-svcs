CREATE TABLE IF NOT EXISTS `orders` (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        user_id BIGINT NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        total_price DOUBLE NOT NULL,
                        order_ref_id VARCHAR(100) NOT NULL UNIQUE,
                        purchased_products VARCHAR(255) NOT NULL,
                        status ENUM('PENDING', 'CONFIRMED', 'COMPLETED', 'CANCELLED') NOT NULL
);

CREATE TABLE IF NOT EXISTS `shipments` (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           product_id BIGINT NOT NULL,
                           price DOUBLE NOT NULL,
                           quantity BIGINT NOT NULL,
                           status VARCHAR(255) NOT NULL,
                           order_id BIGINT NOT NULL,
                           CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE IF NOT EXISTS `users` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `username` VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `full_name` VARCHAR(255),
    `enabled` TINYINT(1) NOT NULL DEFAULT 1,
    `role` VARCHAR(50) NOT NULL DEFAULT 'USER',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

truncate table `users`;

insert into `users` (`id`, `username`, `password`, `full_name`, `enabled`, `role`) values(1, 'user', '{bcrypt}$2a$10$IeofhAYT3lUfrF0bi1aflOat.IU3xOkZWaAWAuVc9jO2.QxTtH4RO', 'User', 1, 'USER');

alter table `users` AUTO_INCREMENT = 2;

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE `orders`;

INSERT INTO orders (id, user_id, total_price, created_at, status, order_ref_id, purchased_products)
VALUES (1, 1, 20.00, CURRENT_TIMESTAMP, 'COMPLETED', 'sample-uuid-xxxx-yyyy', '1001,1002,1003');

ALTER TABLE `orders` AUTO_INCREMENT = 2;

SET FOREIGN_KEY_CHECKS = 1;

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE `shipments`;

INSERT INTO shipments (product_id, price, quantity, status, order_id)
VALUES (1001, 50.0, 2, 'PENDING', 1);

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE IF NOT EXISTS `payment_status` (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                payment_id VARCHAR(100) NOT NULL UNIQUE,
                                status VARCHAR(50) NOT NULL,
                                amount DOUBLE NOT NULL,
                                currency VARCHAR(20) NOT NULL,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                order_ref_id VARCHAR(100) UNIQUE
);

CREATE TABLE IF NOT EXISTS `product_catalog` (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              product_name VARCHAR(50) NOT NULL,
                              price DECIMAL(13,2) NOT NULL,
                              image VARCHAR(500),
                              description VARCHAR(255),
                              on_sell BOOLEAN NOT NULL DEFAULT FALSE
);

TRUNCATE TABLE `product_catalog`;
INSERT INTO product_catalog (id, product_name, price, description, image, on_sell) VALUES
     (1001, 'IPhone 17 pro (Orange)', 1020.0, 'Strongest Iphone with the professional Camera','https://store.storeimages.cdn-apple.com/1/as-images.apple.com/is/iphone-compare-iphone-17-pro-202509?wid=400&hei=512&fmt=png-alpha&.v=M0dlUVBobHVpY1h1dmlaR3RZekpEMi9sbCsxVVJmYjNiS29STjQrZEV5NnNlL1VpWDFHcHBMQXVUWWdWdkZZNGJPbDJJWDFrVGJEYlIxTitTcHhVWldNTk4rSDJkMy8vL20va2hrM1NheXZ4VldteDRHenNWeThpV3EzUWVVd2o', true),
     (1002, 'IPhone Air (White)',   700.0, 'Lightest Iphone with a strong power inside', 'https://store.storeimages.cdn-apple.com/1/as-images.apple.com/is/iphone-compare-iphone-air-202509?wid=400&hei=512&fmt=png-alpha&.v=M0dlUVBobHVpY1h1dmlaR3RZekpEMGtrRFZUNExaR0FUNGxJZXJuT2lqUjE5VXk1QVF5NWxrMFlTNWNpV2huNVM0TjRWdzF2UjRGVEY0c3dBQVZ6VGUza2N1YW5ubjVFaHZuNzNKcFIzTnc', true),
     (1003, 'IPhone 17 (Purple)',    650.0,'Iphone with a balance power and cost', 'https://store.storeimages.cdn-apple.com/1/as-images.apple.com/is/iphone-compare-iphone-17-202509?wid=400&hei=512&fmt=png-alpha&.v=M0dlUVBobHVpY1h1dmlaR3RZekpENGh0eTVTNkN2NWpWZVAwbzMwQlBCTkxxZU5scXpES1hnUm96ckN1R2pZN215d1FhSDJ0bkR0ZGZtUjZJNmFveFo2eWNJSlJFRDM1UWQ2eUozZ1l5ZDA', true),
     (1004, 'IPhone 17e (Pink)',     500.0, 'Most compatible Iphone with the basic power and the cheapest cost','https://store.storeimages.cdn-apple.com/1/as-images.apple.com/is/iphone-compare-iphone-17e-202603?wid=380&hei=512&fmt=png-alpha&.v=M0dlUVBobHVpY1h1dmlaR3RZekpEMDMzS2xmcnFyN2JjeXRuNU5pL1ZKWDd4U2s1ZXUvWFMycmRmdnZ0Qnh2UFM0TjRWdzF2UjRGVEY0c3dBQVZ6VFlvQzhPSnlRVmhZb2dXWmJRTWFrTE0', true);

