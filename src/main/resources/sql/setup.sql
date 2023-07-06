CREATE TABLE IF NOT EXISTS products(product_id bigserial PRIMARY KEY, title varchar(255) NOT NULL, price INTEGER NOT NULL);
CREATE TABLE IF NOT EXISTS consumers(consumer_id bigserial PRIMARY KEY, name VARCHAR(255) NOT NULL);
CREATE TABLE IF NOT EXISTS bought_products(id bigserial PRIMARY KEY, consumer_id INTEGER NOT NULL, product_id INTEGER NOT NULL,
    FOREIGN KEY(consumer_id) REFERENCES consumers(consumer_id),
    FOREIGN KEY(product_id) REFERENCES products(product_id)
);