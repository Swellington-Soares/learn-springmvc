CREATE TABLE `persistent_logins`
(
    `series`    VARCHAR(64)  NOT NULL DEFAULT '' COLLATE 'utf8mb4_unicode_ci',
    `username`  VARCHAR(255) NOT NULL DEFAULT '' COLLATE 'utf8mb4_unicode_ci',
    `token`     VARCHAR(255) NOT NULL DEFAULT '' COLLATE 'utf8mb4_unicode_ci',
    `last_used` TIMESTAMP    NULL     DEFAULT NULL,
    PRIMARY KEY (`series`) USING BTREE
)
    COLLATE = 'utf8mb4_unicode_ci'
    ENGINE = InnoDB
;
