CREATE TABLE `upload_files`
(
    `id`          int      NOT NULL AUTO_INCREMENT,
    `origin_url`  varchar(500)      DEFAULT NULL,
    `type`        int      NOT NULL,
    `width`       int               DEFAULT NULL,
    `height`      int               DEFAULT NULL,
    `duration`    int               DEFAULT NULL,
    `size`        bigint            DEFAULT NULL,
    `origin_name` varchar(255)      DEFAULT NULL,
    `file_name`   varchar(255)      DEFAULT NULL,
    `deleted`     bit(1)   NOT NULL DEFAULT 0,
    `created_at`  datetime NOT NULL,
    `updated_at`  datetime NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users`
(
    `id`             int      NOT NULL AUTO_INCREMENT,
    `phone`          varchar(20)       DEFAULT NULL,
    `email`          varchar(40)       DEFAULT NULL,
    `name`           varchar(40)       DEFAULT NULL,
    `address`        varchar(255)      DEFAULT NULL,
    `role`           int      NOT NULL,
    `password`       varchar(255)      DEFAULT NULL,
    `job`            varchar(255)      DEFAULT NULL,
    `description`    text              DEFAULT NULL,
    `birthday`       date              DEFAULT NULL,
    `gender`         int               DEFAULT NULL,
    `avatar_id`      int               DEFAULT NULL,
    `total_follower` int               DEFAULT NULL,
    `deleted`        bit(1)   NOT NULL DEFAULT 0,
    `created_at`     datetime NOT NULL,
    `updated_at`     datetime NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`phone`),
    UNIQUE KEY (`email`),
    FOREIGN KEY (`avatar_id`) REFERENCES `upload_files` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `follows`
(
    `id`         int      NOT NULL AUTO_INCREMENT,
    `owner_id`   int      NOT NULL,
    `partner_id` int      NOT NULL,
    `deleted`    bit(1)   NOT NULL DEFAULT 0,
    `created_at` datetime NOT NULL,
    `updated_at` datetime NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`),
    FOREIGN KEY (`partner_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_0900_ai_ci;

CREATE TABLE `videos`
(
    `id`          int           NOT NULL AUTO_INCREMENT,
    `owner_id`    int           NOT NULL,
    `title`       varchar(500)  NOT NULL,
    `content`     varchar(2000) NOT NULL,
    `category`    int                    DEFAULT NULL,
    `time`        int           NOT NULL,
    `rateAVG`     float                  DEFAULT NULL,
    `number_view` int                    DEFAULT NULL,
    `number_rate` int                    DEFAULT NULL,
    `avatar_id`   int                    DEFAULT NULL,
    `file_id`     int                    DEFAULT NULL,
    `deleted`     bit(1)        NOT NULL DEFAULT 0,
    `created_at`  datetime      NOT NULL,
    `updated_at`  datetime      NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`),
    FOREIGN KEY (`avatar_id`) REFERENCES `upload_files` (`id`),
    FOREIGN KEY (`file_id`) REFERENCES `upload_files` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_0900_ai_ci;


CREATE TABLE `video_questions`
(
    `id`               int           NOT NULL AUTO_INCREMENT,
    `video_id`         int           NOT NULL,
    `duration`         int           NOT NULL,
    `type`             int           NOT NULL,
    `question_content` varchar(1000) NOT NULL,
    `question_data`    varchar(2000)          DEFAULT NULL,
    `answer`           varchar(50)   NOT NULL,
    `deleted`          bit(1)        NOT NULL DEFAULT 0,
    `created_at`       datetime      NOT NULL,
    `updated_at`       datetime      NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`video_id`) REFERENCES `videos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_0900_ai_ci;

CREATE TABLE `feedbacks`
(
    `id`         int           NOT NULL AUTO_INCREMENT,
    `video_id`   int           NOT NULL,
    `owner_id`   int           NOT NULL,
    `rate`       int           NOT NULL,
    `comment`    varchar(1000) NOT NULL,
    `deleted`    bit(1)        NOT NULL DEFAULT 0,
    `created_at` datetime      NOT NULL,
    `updated_at` datetime      NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`),
    FOREIGN KEY (`video_id`) REFERENCES `videos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_0900_ai_ci;
