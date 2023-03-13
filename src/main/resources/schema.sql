CREATE SEQUENCE IF NOT EXISTS customer_seq START 1;
CREATE TABLE IF NOT EXISTS Customer (
    id BIGINT DEFAULT nextval('customer_seq') PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(100) NOT NULL,
    phone VARCHAR(255) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS food_seq START 1;
CREATE TABLE IF NOT EXISTS Food(
    id BIGINT DEFAULT nextval('food_seq') PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    price int NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS payment_seq START 1;

CREATE TABLE IF NOT EXISTS Payment(
    id BIGINT DEFAULT nextval('payment_seq') PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    amount int NOT NULL,
    method VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    date DATE,
    FOREIGN KEY (customer_id) REFERENCES Customer(id)  ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE SEQUENCE IF NOT EXISTS delivery_seq START 1;
CREATE TABLE IF NOT EXISTS Delivery(
    id BIGINT DEFAULT nextval('delivery_seq') PRIMARY KEY,
    status VARCHAR(50) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS order_seq START 1;
CREATE TABLE IF NOT EXISTS Orders(
    id BIGINT DEFAULT nextval('order_seq') PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    payment_id BIGINT NOT NULL,
    delivery_id BIGINT NOT NULL,
    date DATE,
    quantity BIGINT,
    FOREIGN KEY (customer_id) REFERENCES Customer(id)  ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (payment_id) REFERENCES Payment(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (delivery_id) REFERENCES Delivery(id) ON DELETE CASCADE ON UPDATE CASCADE
);

--order_items table as a junction table between order and food
CREATE TABLE IF NOT EXISTS order_items (
  order_id BIGINT,
  food_id BIGINT,
  PRIMARY KEY (order_id, food_id),
  FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (food_id) REFERENCES Food(id) ON DELETE CASCADE ON UPDATE CASCADE
);

----INSERT INTO customer VALUES
----(1,'Vrushaket','Pune','9595068833'),
----(2,'Om','Yawal','7588052599'),
----(3,'Chetan','Jalgaon','7558665231'),
----(4,'Bhushan','Pune','9595068822');
--
----INSERT INTO FOOD VALUES
----(100,'Burger',90),
----(101,'Pizza',300),
----(102,'Noodles',100),
----(103,'Fries',80);
--
----INSERT INTO PAYMENT VALUES
----(1000,1,200,'UPI','success','2023-03-23'),
----(1001,2,600,'Net Banking','failed','2023-03-22'),
----(1002,1,240,'Debit Card','success','2023-03-22'),
----(1003,3,200,'UPI','pending','2023-03-23'),
----(1004,4,160,'UPI','success','2023-03-22');
----
----INSERT INTO Delivery VALUES
----(100,'on the way'),
----(101,'cancelled'),
----(102,'waiting'),
----(103,'waiting'),
----(104,'on the way');
----
----INSERT INTO ORDERS VALUES
----(10000,1,1000,100,'2023-03-23'),
----(10001,2,1001,101,'2023-03-22'),
----(10002,1,1002,102,'2023-03-22'),
----(10003,3,1003,103,'2023-03-23'),
----(10004,4,1004,104,'2023-03-22');
--
----INSERT INTO ORDER_ITEMS VALUES
----(10000,102,2),
----(10001,101,2),
----(10002,103,3),
----(10003,102,2),
----(10004,103,2);