CREATE TABLE `users`
(
    `id`             BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `email`          VARCHAR(128) NOT NULL COLLATE 'utf8mb4_unicode_ci',
    `username`       VARCHAR(64)  NOT NULL COLLATE 'utf8mb4_unicode_ci',
    `password`       TEXT         NOT NULL COLLATE 'utf8mb4_unicode_ci',
    `avatar`         LONGTEXT     NULL     DEFAULT NULL COLLATE 'utf8mb4_unicode_ci',
    `firstname`      TEXT         NOT NULL COLLATE 'utf8mb4_unicode_ci',
    `lastname`       TEXT         NOT NULL COLLATE 'utf8mb4_unicode_ci',
    `disabled`       TINYINT(1)   NOT NULL DEFAULT '0',
    `email_verified` TINYINT(1)   NOT NULL DEFAULT '0',
    `created_at`     TIMESTAMP    NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uc_users_username` (`username`) USING BTREE,
    UNIQUE INDEX `uc_users_email` (`email`) USING BTREE
)
    COLLATE = 'utf8mb4_unicode_ci'
    ENGINE = InnoDB
;
