/*

Generate a UUID in your application layer and then insert that into the database when you add a new row.
For example, to insert a new product you might do something like:

    INSERT INTO product (id, name, category, added_by)
    VALUES (UUID(), 'Product Name', 'Category Name', 'Person Adding');

*/

-- Table for Product
CREATE TABLE product (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    added_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    added_by VARCHAR(255) NOT NULL
);

-- Table for Product Price
CREATE TABLE product_price (
    id CHAR(36) PRIMARY KEY,
    product_id CHAR(36),
    price DECIMAL(10, 2) NOT NULL,
    discount_percent DECIMAL(5, 2) DEFAULT 0,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-- Table for Product Price Change Log
CREATE TABLE product_price_change_log (
    id CHAR(36) PRIMARY KEY,
    product_price_id CHAR(36),
    old_price DECIMAL(10, 2),
    new_price DECIMAL(10, 2),
    old_discount_percent DECIMAL(5, 2),
    new_discount_percent DECIMAL(5, 2),
    operation ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    operation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    operation_by VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_price_id) REFERENCES product_price(id)
);

-- Query to join product and product_price
SELECT
    p.name AS product_name,
    p.category AS product_category,
    pp.price AS product_price,
    pp.updated_by AS price_updated_by,
    pp.updated_time AS price_updated_time
FROM product AS p
JOIN product_price AS pp ON p.id = pp.product_id;
