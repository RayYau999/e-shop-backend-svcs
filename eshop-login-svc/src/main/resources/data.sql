truncate table `users`;

insert into `users` (`id`, `username`, `password`, `full_name`, `enabled`, `role`) values(1, 'user', '{bcrypt}$2a$10$IeofhAYT3lUfrF0bi1aflOat.IU3xOkZWaAWAuVc9jO2.QxTtH4RO', 'User', 1, 'USER');

alter table `users` AUTO_INCREMENT = 2;