CREATE TABLE todos (
    id VARCHAR(36) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    text VARCHAR(255) NOT NULL,
    done BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    CONSTRAINT fk_todos_user FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_todos_user_created (user_id, created_at)
);
