CREATE TABLE IF NOT EXISTS items(item_id bigserial PRIMARY KEY, title varchar(255) NOT NULL, price INTEGER NOT NULL);
CREATE TABLE IF NOT EXISTS buyers(buyer_id bigserial PRIMARY KEY, name VARCHAR(255) NOT NULL);
CREATE TABLE IF NOT EXISTS bought_products(id bigserial PRIMARY KEY, buyer_id INTEGER NOT NULL, item_id INTEGER NOT NULL,
    FOREIGN KEY(buyer_id) REFERENCES buyers(buyer_id),
    FOREIGN KEY(item_id) REFERENCES items(item_id)
);