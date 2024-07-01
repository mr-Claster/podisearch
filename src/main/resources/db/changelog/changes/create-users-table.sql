CREATE TABLE users (
       id SERIAL PRIMARY KEY,
       first_name VARCHAR(255),
       last_name VARCHAR(255),
       profile_image VARCHAR(255),
       password VARCHAR(255),
       email VARCHAR(255) UNIQUE,
       provider_id INT,
       FOREIGN KEY (provider_id) REFERENCES providers(id)
);
