-- Inventory Management System - MySQL Data Model
-- Generated from .github/Inventory-datamodel.md
-- Engine: InnoDB, Charset: utf8mb4

-- Safe drop order (removes existing tables if present)
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS sale_items;
DROP TABLE IF EXISTS sales;
DROP TABLE IF EXISTS purchase_items;
DROP TABLE IF EXISTS purchases;
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS product_types;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS vendors;
SET FOREIGN_KEY_CHECKS = 1;

-- Product types / categories
CREATE TABLE product_types (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	description TEXT NULL,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
	UNIQUE KEY ux_product_types_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Vendors
CREATE TABLE vendors (
	vendor_id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(150) NOT NULL,
	contact_no VARCHAR(50) NULL,
	address TEXT NULL,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (vendor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Customers
CREATE TABLE customers (
	customer_id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(150) NOT NULL,
	contact_no VARCHAR(50) NULL,
	address TEXT NULL,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Products
CREATE TABLE products (
	product_id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(200) NOT NULL,
	product_type_id INT NULL,
	unit_price DECIMAL(13,2) NOT NULL DEFAULT 0.00,
	unit ENUM('kg','ton','bag','unit') NOT NULL DEFAULT 'unit',
	description TEXT NULL,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (product_id),
	KEY idx_products_product_type (product_type_id),
	CONSTRAINT fk_products_product_type FOREIGN KEY (product_type_id) REFERENCES product_types(id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Inventory (current stock per product)
CREATE TABLE inventory (
	product_id INT NOT NULL,
	available_qty DECIMAL(18,4) NOT NULL DEFAULT 0.0000,
	reorder_level DECIMAL(18,4) NOT NULL DEFAULT 0.0000,
	last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (product_id),
	CONSTRAINT fk_inventory_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Purchases (a purchase order from a vendor)
CREATE TABLE purchases (
	purchase_id INT NOT NULL AUTO_INCREMENT,
	vendor_id INT NOT NULL,
	purchase_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	total_cost DECIMAL(15,2) NOT NULL DEFAULT 0.00,
	notes TEXT NULL,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (purchase_id),
	KEY idx_purchases_vendor (vendor_id),
	CONSTRAINT fk_purchases_vendor FOREIGN KEY (vendor_id) REFERENCES vendors(vendor_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Purchase items (line items for a purchase)
CREATE TABLE purchase_items (
	id INT NOT NULL AUTO_INCREMENT,
	purchase_id INT NOT NULL,
	product_id INT NOT NULL,
	quantity DECIMAL(18,4) NOT NULL DEFAULT 0.0000,
	unit_price DECIMAL(13,4) NOT NULL DEFAULT 0.0000,
	line_total DECIMAL(15,2) AS (quantity * unit_price) STORED,
	PRIMARY KEY (id),
	KEY idx_purchase_items_purchase (purchase_id),
	KEY idx_purchase_items_product (product_id),
	CONSTRAINT fk_purchase_items_purchase FOREIGN KEY (purchase_id) REFERENCES purchases(purchase_id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_purchase_items_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Sales (a sales order to a customer)
CREATE TABLE sales (
	sale_id INT NOT NULL AUTO_INCREMENT,
	sale_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	customer_id INT NOT NULL,
	total_amount DECIMAL(15,2) NOT NULL DEFAULT 0.00,
	notes TEXT NULL,
	created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (sale_id),
	KEY idx_sales_customer (customer_id),
	CONSTRAINT fk_sales_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Sale items (line items for a sale)
CREATE TABLE sale_items (
	id INT NOT NULL AUTO_INCREMENT,
	sale_id INT NOT NULL,
	product_id INT NOT NULL,
	quantity DECIMAL(18,4) NOT NULL DEFAULT 0.0000,
	unit_price DECIMAL(13,4) NOT NULL DEFAULT 0.0000,
	line_total DECIMAL(15,2) AS (quantity * unit_price) STORED,
	PRIMARY KEY (id),
	KEY idx_sale_items_sale (sale_id),
	KEY idx_sale_items_product (product_id),
	CONSTRAINT fk_sale_items_sale FOREIGN KEY (sale_id) REFERENCES sales(sale_id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_sale_items_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Helpful indexes for lookups
CREATE INDEX idx_products_name ON products (name(60));
CREATE INDEX idx_vendors_name ON vendors (name(60));
CREATE INDEX idx_customers_name ON customers (name(60));

-- End of script

-- Sample product types data for construction materials
INSERT INTO product_types (name, description) VALUES
('Cement', 'Various types of cement including OPC and PPC'),
('Steel Bars', 'TMT and reinforcement steel bars'),
('Bricks', 'Clay bricks and concrete blocks'),
('Sand', 'River sand and manufactured sand'),
('Aggregates', 'Various sizes of stone aggregates'),
('Paint', 'Interior and exterior paints'),
('Tiles', 'Floor and wall tiles'),
('Pipes', 'PVC and metal pipes');

/* Usage:
   mysql -u <user> -p <database> < data-model.sql
   or run the contents in a MySQL client with appropriate privileges.

   Notes / assumptions:
   - Products reference product_types (category) via product_type_id.
   - Purchases and Sales use line-item tables to support multiple products per transaction.
   - Inventory keeps current available quantity; it is expected that application logic updates inventory when purchases/sales occur.
   - Units are constrained to ('kg','ton','bag','unit') by ENUM; extend if needed.
*/

