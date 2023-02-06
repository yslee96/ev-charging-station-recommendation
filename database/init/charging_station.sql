DROP TABLE IF EXISTS `charging_station`;
CREATE TABLE `charging_station` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_date` datetime(6) DEFAULT NULL,
    `modified_date` datetime(6) DEFAULT NULL,
    `latitude` double NOT NULL,
    `longitude` double NOT NULL,
    `charging_station_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    primary key (`id`)
) ENGINE = InnoDB COLLATE = utf8mb4_unicode_ci;

INSERT INTO `charging_station`
VALUES
    (1,'2022-10-17 23:46:34.982624','2022-10-17 23:46:34.982624',37.60894036,127.029052, '라이츄'),
    (2,'2022-10-17 23:46:35.025774','2022-10-17 23:46:35.025774',37.61040424,127.0569046,'피카츄');