CREATE TABLE `verify_tokens`
(
    `id`         BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `token`      VARCHAR(255) NOT NULL COLLATE 'utf8mb4_unicode_ci',
    `user_id`    BIGINT(20)   NOT NULL,
    `expire_in`  DATETIME     NOT NULL,
    `token_type` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_unicode_ci',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uc_verify_tokens_token` (`token`) USING BTREE,
    UNIQUE INDEX `uc_e7faf1935c5c23e77dad038f0` (`token`, `user_id`, `token_type`) USING BTREE,
    INDEX `FK_VERIFY_TOKENS_ON_USER` (`user_id`) USING BTREE,
    CONSTRAINT `FK_VERIFY_TOKENS_ON_USER` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON UPDATE NO ACTION ON DELETE CASCADE
)
    COLLATE = 'utf8mb4_unicode_ci'
    ENGINE = InnoDB
;
