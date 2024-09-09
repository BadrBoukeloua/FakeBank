-- Insert customers
INSERT INTO customer (customer_id, name) VALUES
(1, 'Arisha Barron'),
(2, 'Branden Gibson'),
(3, 'Rhonda Church'),
(4, 'Georgina Hazel');


-- Insert accounts

INSERT INTO account (account_id, customer_id, username, password, balance, created_at, updated_at) VALUES
(1, 1, 'arisha_barron', 'hashed_password1', 1000.00, NOW(), NOW()),
(2, 2, 'branden_gibson', 'hashed_password2', 1500.00, NOW(), NOW()),
(3, 3, 'rhonda_church', 'hashed_password3', 2000.00, NOW(), NOW()),
(4, 3, 'rhonda_church2', 'hashed_password4', 3000.00, NOW(), NOW());

-- Insert transactions

INSERT INTO bank_transaction (transaction_id, sender_account_id, receiver_account_id, amount, transaction_time, transaction_type) VALUES
(1, 1, 2, 200.00, NOW(), 'TRANSFER'),
(2, 2, 3, 300.00, NOW(), 'TRANSFER'),
(3, 3, 4, 500.00, NOW(), 'TRANSFER'),
(4, NULL, 4, 1000.00, NOW(), 'DEPOSIT');
