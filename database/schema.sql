-- Food Delivery App: Oracle schema (works on Oracle 10g XE and newer)
-- Run as the project user, for example: CONNECT fooddelivery/your_password

CREATE TABLE users (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    email VARCHAR2(150) NOT NULL UNIQUE,
    password VARCHAR2(255) NOT NULL,
    role VARCHAR2(20) DEFAULT 'USER' NOT NULL
);
CREATE SEQUENCE users_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER users_trg BEFORE INSERT ON users FOR EACH ROW
BEGIN
  IF :NEW.id IS NULL THEN SELECT users_seq.NEXTVAL INTO :NEW.id FROM dual; END IF;
END;
/

CREATE TABLE restaurants (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(150) NOT NULL,
    address VARCHAR2(255),
    cuisine VARCHAR2(100)
);
CREATE SEQUENCE restaurants_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER restaurants_trg BEFORE INSERT ON restaurants FOR EACH ROW
BEGIN
  IF :NEW.id IS NULL THEN SELECT restaurants_seq.NEXTVAL INTO :NEW.id FROM dual; END IF;
END;
/

CREATE TABLE food_items (
    id NUMBER PRIMARY KEY,
    restaurant_id NUMBER NOT NULL REFERENCES restaurants(id),
    name VARCHAR2(150) NOT NULL,
    price NUMBER(10,2) NOT NULL,
    available NUMBER(1) DEFAULT 1 NOT NULL
);
CREATE SEQUENCE food_items_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER food_items_trg BEFORE INSERT ON food_items FOR EACH ROW
BEGIN
  IF :NEW.id IS NULL THEN SELECT food_items_seq.NEXTVAL INTO :NEW.id FROM dual; END IF;
END;
/

CREATE TABLE orders (
    id NUMBER PRIMARY KEY,
    user_id NUMBER NOT NULL REFERENCES users(id),
    total NUMBER(10,2) NOT NULL,
    status VARCHAR2(30) DEFAULT 'PLACED' NOT NULL,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL
);
CREATE SEQUENCE orders_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER orders_trg BEFORE INSERT ON orders FOR EACH ROW
BEGIN
  IF :NEW.id IS NULL THEN SELECT orders_seq.NEXTVAL INTO :NEW.id FROM dual; END IF;
END;
/

CREATE TABLE order_items (
    id NUMBER PRIMARY KEY,
    order_id NUMBER NOT NULL REFERENCES orders(id),
    food_item_id NUMBER NOT NULL REFERENCES food_items(id),
    quantity NUMBER NOT NULL,
    price NUMBER(10,2) NOT NULL
);
CREATE SEQUENCE order_items_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER order_items_trg BEFORE INSERT ON order_items FOR EACH ROW
BEGIN
  IF :NEW.id IS NULL THEN SELECT order_items_seq.NEXTVAL INTO :NEW.id FROM dual; END IF;
END;
/

-- Sample data
INSERT INTO restaurants (name, address, cuisine) VALUES ('Spice Garden', 'Chandannagar', 'Indian');
INSERT INTO restaurants (name, address, cuisine) VALUES ('Pizza Point', 'Chinsurah', 'Italian');
INSERT INTO food_items (restaurant_id, name, price) VALUES (1, 'Chicken Biryani', 180);
INSERT INTO food_items (restaurant_id, name, price) VALUES (1, 'Paneer Butter Masala', 150);
INSERT INTO food_items (restaurant_id, name, price) VALUES (2, 'Margherita Pizza', 220);
COMMIT;

-- To make a user an admin after registering:
-- UPDATE users SET role = 'ADMIN' WHERE email = 'your_email_here';
-- COMMIT;