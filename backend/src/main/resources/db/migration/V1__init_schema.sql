-- 1. Category
CREATE TABLE category (
    category_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    status BOOLEAN DEFAULT TRUE,
    start_date DATE,
    end_date DATE
);

-- 2. Supplier
CREATE TABLE supplier (
    supplier_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    phone VARCHAR(50),
    address VARCHAR(255),
    status BOOLEAN DEFAULT TRUE,
    start_date DATE,
    end_date DATE
);

-- 3. Users
CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    phone VARCHAR(50),
    address VARCHAR(255),
    role VARCHAR(50), -- CUSTOMER, ADMIN, GUEST
    status BOOLEAN DEFAULT TRUE,
    start_date DATE,
    end_date DATE
);

-- 4. Product
CREATE TABLE product (
    product_id BIGSERIAL PRIMARY KEY,
    category_id BIGINT REFERENCES category(category_id),
    supplier_id BIGINT REFERENCES supplier(supplier_id),
    name VARCHAR(255),
    description TEXT,
    images TEXT,
    stock INT,
    price NUMERIC(12,2),
    status BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- 5. CategoryPromotion
CREATE TABLE category_promotion (
    promotion_id BIGSERIAL PRIMARY KEY,
    category_id BIGINT REFERENCES category(category_id),
    name VARCHAR(255),
    discount_type VARCHAR(50), -- PERCENT, FIXED
    discount_value NUMERIC(12,2),
    start_date DATE,
    end_date DATE,
    status BOOLEAN DEFAULT TRUE
);

-- 6. Review
CREATE TABLE review (
    review_id BIGSERIAL PRIMARY KEY,
    product_id BIGINT REFERENCES product(product_id),
    user_id BIGINT REFERENCES users(user_id),
    name VARCHAR(255),
    description TEXT,
    images TEXT,
    stock INT,
    price NUMERIC(12,2),
    status BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- 7. Cart
CREATE TABLE cart (
    cart_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(user_id),
    status BOOLEAN DEFAULT TRUE
);

-- 8. CartItem
CREATE TABLE cart_item (
    cart_item_id BIGSERIAL PRIMARY KEY,
    cart_id BIGINT REFERENCES cart(cart_id),
    product_id BIGINT REFERENCES product(product_id),
    quantity INT,
    price NUMERIC(12,2)
);

-- 9. Orders
CREATE TABLE orders (
    order_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(user_id),
    order_date TIMESTAMP,
    status VARCHAR(50), -- PENDING, PROCESSING, COMPLETED, CANCELLED
    total_amount NUMERIC(12,2),
    final_amount NUMERIC(12,2),
    coupon_code VARCHAR(50)
);

-- 10. OrderItem
CREATE TABLE order_items (
    order_item_id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(order_id),
    product_id BIGINT REFERENCES product(product_id),
    quantity INT,
    price NUMERIC(12,2)
);

-- 11. Payment
CREATE TABLE payment (
    payment_id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(order_id),
    payment_method VARCHAR(50), -- VNPAY, COD, CREDIT_CARD
    amount NUMERIC(12,2),
    payment_date TIMESTAMP,
    status VARCHAR(50), -- PENDING, SUCCESS, FAILED
    transaction_id VARCHAR(255)
);

-- 12. Coupon
CREATE TABLE coupon (
    coupon_id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50),
    description TEXT,
    discount_type VARCHAR(50), -- PERCENT, FIXED
    discount_value NUMERIC(12,2),
    min_order_amount NUMERIC(12,2),
    start_date DATE,
    end_date DATE,
    status BOOLEAN DEFAULT TRUE
);
