CREATE TABLE todo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '식별자',
    password VARCHAR(50) NOT NULL ,
    text VARCHAR(255) NOT NULL ,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    edited_at DATETIME NOT NULL,
    user_id BIGINT NOT NULL
);

CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 식별자',
    name       VARCHAR(50) NOT NULL COMMENT '사용자 이름',
    email      VARCHAR(50) NOT NULL UNIQUE COMMENT '사용자 이메일',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '수정일'
);

ALTER TABLE todo ADD CONSTRAINT fk_todo_user
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

