CREATE TABLE marks_users (
       mark_id BIGINT,
       users_id BIGINT,
       PRIMARY KEY (mark_id, users_id),
       FOREIGN KEY (mark_id) REFERENCES marks(id),
       FOREIGN KEY (users_id) REFERENCES users(id)
);
