INSERT INTO users (id, email, password, first_name, last_name, address, state, city, zip, user_type, creation_date) VALUES (1,'testuser@gmail.com','pass', 'Bryan', 'Serfozo', '1234 Tampa Ave', 'Florida', 'Tampa', 57624, 'CLIENT', '2022-08-25T20:32:26.568Z');
--INSERT INTO users (id, email, password, first_name, last_name, address, state, city, zip, user_type, creation_date) VALUES (1,'test','pass', 'Bryan', 'Serfozo', '1234 Tampa Ave', 'Florida', 'Tampa', 57624, 'CLIENT', '2022-08-25T20:32:26.568Z');
INSERT INTO users (id, email, password, first_name, last_name, address, state, city, zip, user_type, creation_date) VALUES (2,'test@gmail.com','pass', 'C', 'W', '1234 A Ave', 'Florida', 'Tampa', 57624, 'CLIENT', '2022-08-25T20:32:26.568Z');
INSERT INTO users (id, email, password, first_name, last_name, address, state, city, zip, user_type, creation_date) VALUES (3,'admin@gmail.com','pass', 'admin', 'admin', '1234 A Ave', 'Florida', 'Tampa', 57624, 'ADMIN', '2022-08-25T20:32:26.568Z');

INSERT INTO accounts (id, name, balance, creation_date, user_id, account_type) VALUES (1,'Primary Checking',10000.00,'2022-08-25T20:32:26.568Z',1,'CHECKING');
INSERT INTO accounts (id, name, balance, creation_date, user_id, account_type) VALUES (2,'Primary Savings',10001.00,'2022-08-25T20:32:26.568Z',2,'SAVINGS');
INSERT INTO accounts (id, name, balance, creation_date, user_id, account_type) VALUES (3,'Primary Savings',5000.00,'2022-08-25T20:32:26.568Z',3,'SAVINGS');

INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (1,150.00,'2022-12-01T20:32:26.568Z','Cable Bill','EXPENSE',1),(2,2500.00,'2022-08-25T20:32:26.568Z','Payroll Direct Deposit','INCOME',1),(3,1500.00,'2022-08-25T20:32:26.568Z','Rent','EXPENSE',1),(4,2500.00,'2022-08-25T20:32:26.568Z','Payroll Direct Deposit','INCOME',1),(5,2500.00,'2022-08-25T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (6,42.00,'2022-01-01T20:32:26.568Z','Gas','EXPENSE',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (7,2500.00,'2022-01-015T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (8,2500.00,'2022-01-29T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (9,2500.00,'2022-02-05T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (10,75.22,'2022-01-06T20:32:26.568Z','Groceries','EXPENSE',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (11,2500.00,'2022-08-14T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (12,2500.00,'2022-08-15T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (13,2500.00,'2022-08-16T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (14,2500.00,'2022-08-17T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (15,2500.00,'2022-08-18T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (16,2500.00,'2022-08-19T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (17,2500.00,'2022-08-20T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (18,2500.00,'2022-08-21T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (19,2500.00,'2022-08-22T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (20,2500.00,'2022-08-23T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (21,2500.00,'2022-08-25T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (22,2500.00,'2022-08-24T20:32:26.568Z','Phone Bill','EXPENSE',1)
INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (23,2500.00,'2021-12-31T20:32:26.568Z','Payroll Direct Deposit','INCOME',1)

INSERT INTO creditcards (id, user_id, card_number, ccv, expiration_date, total_limit, available_balance) VALUES (1, 1, 1234123412341234, 765, '2022-08-25T20:32:26.568Z', 15000.00, 15000.00);
INSERT INTO creditcards (id, user_id, card_number, ccv, expiration_date, total_limit, available_balance) VALUES (2, 3, 4567456745674567, 783, '2022-08-25T20:32:26.568Z', 10000.00, 10000.00);
INSERT INTO creditcards (id, user_id, card_number, ccv, expiration_date, total_limit, available_balance) VALUES (3, 1, 4567456745674568, 783, '2022-08-25T20:32:26.568Z', 10000.00, 5000.00);

INSERT INTO cctransactions (id, amount, description, creation_date, credit_card_id) VALUES (1, 500.00, 'shopping', '2022-08-25T20:32:26.568Z', 1);

INSERT INTO notifications (id, user_id, type, body, dismissed, seen) VALUES ('a', 1, 'WARNING', 'testing...',                           false, false);
INSERT INTO notifications (id, user_id, type, body, dismissed, seen) VALUES ('b', 1, 'WARNING', 'testing again...',                     false, false);
INSERT INTO notifications (id, user_id, type, body, dismissed, seen) VALUES ('c', 1, 'WARNING', 'dismiss me, i dare you',               false, false);
INSERT INTO notifications (id, user_id, type, body, dismissed, seen) VALUES ('d', 1, 'WARNING', 'this should not be seen...',           true,  false);
INSERT INTO notifications (id, user_id, type, body, dismissed, seen) VALUES ('e', 3, 'WARNING', 'hello',                                false, false);
INSERT INTO notifications (id, user_id, type, body, dismissed, seen) VALUES ('f', 3, 'WARNING', 'important administrator notification', false, false);
INSERT INTO notifications (id, user_id, type, body, dismissed, seen) VALUES ('g', 2, 'WARNING', 'hello cory',                           false, false);