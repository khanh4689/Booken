-- =========================
-- 1. Category
-- =========================
CREATE TABLE category (
    categoryId BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    status BOOLEAN DEFAULT TRUE,
    start_date DATE,
    end_date DATE
);

-- =========================
-- 2. Supplier
-- =========================
CREATE TABLE supplier (
    supplierId BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    phone VARCHAR(50),
    address VARCHAR(255),
    status BOOLEAN DEFAULT TRUE,
    start_date DATE,
    end_date DATE
);

-- =========================
-- 3. Users
-- =========================
CREATE TABLE users (
    userId BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(50),
    address VARCHAR(255),
    role VARCHAR(50), -- CUSTOMER, ADMIN, GUEST
    status BOOLEAN DEFAULT TRUE,
    start_date DATE,
    end_date DATE
);

-- =========================
-- 3.1 Verification Tokens
-- =========================
CREATE TABLE verification_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    userId BIGINT UNIQUE,
    CONSTRAINT fk_verification_user FOREIGN KEY (userId)
        REFERENCES users(userId)
        ON DELETE CASCADE
);

-- =========================
-- 4. Product
-- =========================
CREATE TABLE product (
    productId BIGSERIAL PRIMARY KEY,
    categoryId BIGINT REFERENCES category(categoryId),
    supplierId BIGINT REFERENCES supplier(supplierId),
    name VARCHAR(255),
    description TEXT,
    images TEXT,
    stock INT,
    price NUMERIC(12,2),
    status BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- =========================
-- 5. Category Promotion
-- =========================
CREATE TABLE category_promotion (
    promotionId BIGSERIAL PRIMARY KEY,
    categoryId BIGINT REFERENCES category(categoryId),
    name VARCHAR(255),
    discount_type VARCHAR(50), -- PERCENT, FIXED
    discount_value NUMERIC(12,2),
    start_date DATE,
    end_date DATE,
    status BOOLEAN DEFAULT TRUE
);

-- =========================
-- 6. Review
-- =========================
CREATE TABLE review (
    reviewId BIGSERIAL PRIMARY KEY,
    productId BIGINT REFERENCES product(productId),
    userId BIGINT REFERENCES users(userId),
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    images TEXT,
    status BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- =========================
-- 7. Cart
-- =========================
CREATE TABLE cart (
    cartId BIGSERIAL PRIMARY KEY,
    userId BIGINT REFERENCES users(userId),
    status BOOLEAN DEFAULT TRUE
);

-- =========================
-- 8. Cart Item
-- =========================
CREATE TABLE cart_item (
    cartItemId BIGSERIAL PRIMARY KEY,
    cartId BIGINT REFERENCES cart(cartId),
    productId BIGINT REFERENCES product(productId),
    quantity INT,
    price NUMERIC(12,2)
);

-- =========================
-- 9. Orders
-- =========================
CREATE TABLE orders (
    orderId BIGSERIAL PRIMARY KEY,
    userId BIGINT REFERENCES users(userId),
    order_date TIMESTAMP,
    status VARCHAR(50), -- PENDING, PROCESSING, COMPLETED, CANCELLED
    total_amount NUMERIC(12,2),
    final_amount NUMERIC(12,2),
    coupon_code VARCHAR(50)
);

-- =========================
-- 10. Order Items
-- =========================
CREATE TABLE order_items (
    orderItemId BIGSERIAL PRIMARY KEY,
    orderId BIGINT REFERENCES orders(orderId),
    productId BIGINT REFERENCES product(productId),
    quantity INT,
    price NUMERIC(12,2)
);

-- =========================
-- 11. Payment
-- =========================
CREATE TABLE payment (
    paymentId BIGSERIAL PRIMARY KEY,
    orderId BIGINT REFERENCES orders(orderId),
    payment_method VARCHAR(50), -- VNPAY, COD, CREDIT_CARD
    amount NUMERIC(12,2),
    payment_date TIMESTAMP,
    status VARCHAR(50), -- PENDING, SUCCESS, FAILED
    transaction_id VARCHAR(255)
);

-- =========================
-- 12. Coupon
-- =========================
CREATE TABLE coupon (
    couponId BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE,
    description TEXT,
    discount_type VARCHAR(50), -- PERCENT, FIXED
    discount_value NUMERIC(12,2),
    min_order_amount NUMERIC(12,2),
    start_date DATE,
    end_date DATE,
    status BOOLEAN DEFAULT TRUE
);

-- ==========================================================
-- ================== SAMPLE DATA ===========================
-- ==========================================================

-- 1. Category
INSERT INTO category (name, status, start_date) VALUES
('Fiction', TRUE, CURRENT_DATE),
('Science', TRUE, CURRENT_DATE),
('Design', TRUE, CURRENT_DATE);

-- 2. Supplier
INSERT INTO supplier (name, phone, address, status, start_date) VALUES
('Book Supplier A', '0123456789', '123 Main St', TRUE, CURRENT_DATE),
('Book Supplier B', '0987654321', '456 Central Ave', TRUE, CURRENT_DATE);

-- 3. Users
INSERT INTO users (name, email, password, phone, address, role, start_date) VALUES
('Admin User', 'admin@example.com', 'admin123', '0901111111', 'Hanoi', 'ADMIN', CURRENT_DATE),
('Customer One', 'customer1@example.com', 'password123', '0902222222', 'HCM City', 'CUSTOMER', CURRENT_DATE),
('Customer Two', 'customer2@example.com', 'password123', '0903333333', 'Danang', 'CUSTOMER', CURRENT_DATE);

-- 4. Product
INSERT INTO product (categoryId, supplierId, name, description, images, stock, price, created_at, updated_at) VALUES
(1, 1, 'Black Night', 'A thrilling fiction book.', '1.png', 100, 20.00, NOW(), NOW()),
(1, 1, 'About The First Night', 'Romantic story.', '2.png', 100, 25.00, NOW(), NOW()),
(1, 2, 'Open The Sky', 'Adventure novel.', '3.png', 100, 30.00, NOW(), NOW()),
(1, 2, 'Book Hard Cover', 'Classic edition.', '4.png', 100, 40.00, NOW(), NOW()),
(2, 1, 'The Big Book Of Science', 'Comprehensive science book.', '5.png', 100, 80.00, NOW(), NOW()),
(1, 2, 'By The Air', 'Fantasy journey.', '6.png', 100, 18.00, NOW(), NOW()),
(1, 1, 'Murdering Last Year', 'Crime and mystery.', '7.png', 100, 27.00, NOW(), NOW()),
(2, 2, 'Stay Healthy', 'Health guide.', '8.png', 100, 50.00, NOW(), NOW()),
(2, 1, 'Self Care', 'Personal wellness.', '9.png', 100, 34.00, NOW(), NOW()),
(2, 1, 'Welcome to Space', 'Space science.', '10.png', 100, 150.00, NOW(), NOW()),
(1, 1, 'Monsoon', 'Drama novel.', '11.png', 100, 23.00, NOW(), NOW()),
(1, 2, 'Every Thing You Ever', 'Motivational story.', '12.png', 100, 57.00, NOW(), NOW()),
(3, 1, 'Graphic Design School', 'Design learning book.', '13.png', 100, 100.00, NOW(), NOW()),
(2, 2, 'Food Poison', 'Health science.', '14.png', 100, 76.00, NOW(), NOW()),
(3, 1, 'Design', 'Design concepts.', '15.png', 100, 10.00, NOW(), NOW()),
(2, 2, 'World News', 'Current world events.', '16.png', 100, 20.00, NOW(), NOW());

-- 5. Category Promotion
INSERT INTO category_promotion (categoryId, name, discount_type, discount_value, start_date, end_date, status) VALUES
(1, 'Fiction Launch Sale', 'PERCENT', 10.00, '2025-09-01', '2025-09-30', TRUE),
(2, 'Science Week Discount', 'FIXED', 20.00, '2025-09-10', '2025-09-20', TRUE);

-- 6. Review
INSERT INTO review (productId, userId, rating, comment, images, status, created_at, updated_at) VALUES
(1, 1, 5, 'Great book, very inspiring!', 'review1.png', TRUE, NOW(), NOW()),
(2, 2, 3, 'Not bad but could be better.', 'review2.png', TRUE, NOW(), NOW());

-- 7. Cart
INSERT INTO cart (userId, status) VALUES
(1, TRUE),
(2, TRUE);

-- 8. Cart Item
INSERT INTO cart_item (cartId, productId, quantity, price) VALUES
(1, 1, 2, 20.00),
(1, 3, 1, 30.00),
(2, 2, 1, 25.00);

-- 9. Orders
INSERT INTO orders (userId, order_date, status, total_amount, final_amount, coupon_code) VALUES
(1, NOW(), 'PENDING', 70.00, 63.00, 'WELCOME10'),
(2, NOW(), 'PROCESSING', 25.00, 25.00, NULL);

-- 10. Order Items
INSERT INTO order_items (orderId, productId, quantity, price) VALUES
(1, 1, 2, 20.00),
(1, 3, 1, 30.00),
(2, 2, 1, 25.00);

-- 11. Payment
INSERT INTO payment (orderId, payment_method, amount, payment_date, status, transaction_id) VALUES
(1, 'VNPAY', 63.00, NOW(), 'SUCCESS', 'TXN123456'),
(2, 'COD', 25.00, NOW(), 'PENDING', 'TXN123457');

-- 12. Coupon
INSERT INTO coupon (code, description, discount_type, discount_value, min_order_amount, start_date, end_date, status) VALUES
('WELCOME10', '10% off for first order', 'PERCENT', 10.00, 20.00, '2025-09-01', '2025-12-31', TRUE),
('SAVE20', 'Save 20k for orders above 200k', 'FIXED', 20.00, 200.00, '2025-09-01', '2025-11-30', TRUE);
