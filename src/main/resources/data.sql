DROP TABLE IF EXISTS orders;

CREATE TABLE orders
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(250) NOT NULL,
    customer VARCHAR(250) NOT NULL,
    date     DATE         NOT NULL
);