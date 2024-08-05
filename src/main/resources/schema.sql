CREATE TABLE IF NOT EXISTS transaction (
   id SERIAL PRIMARY KEY,
   transaction_id VARCHAR(255) NOT NULL,
   amount DECIMAL(10, 2) NOT NULL,
   currency VARCHAR(3) NOT NULL,
   timestamp TIMESTAMP NOT NULL,
   status VARCHAR(255) NOT NULL
);