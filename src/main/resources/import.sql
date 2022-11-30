INSERT INTO users (id, email, password, first_name, last_name, address, state, city, zip, user_type, creation_date) VALUES (1,'testuser@gmail.com','pass', 'Bryan', 'Serfozo', '1234 Tampa Ave', 'Florida', 'Tampa', 57624, 'CLIENT', '2022-08-25T20:32:26.568Z');
INSERT INTO users (id, email, password, first_name, last_name, address, state, city, zip, user_type, creation_date) VALUES (2,'test@gmail.com','pass', 'C', 'W', '1234 A Ave', 'Florida', 'Tampa', 57624, 'CLIENT', '2022-08-25T20:32:26.568Z');
INSERT INTO users (id, email, password, first_name, last_name, address, state, city, zip, user_type, creation_date) VALUES (3,'admin@gmail.com','pass', 'admin', 'admin', '1234 A Ave', 'Florida', 'Tampa', 57624, 'ADMIN', '2022-08-25T20:32:26.568Z');

INSERT INTO accounts (id, name, balance, creation_date, user_id, account_type) VALUES (1,'Primary Checking',10000.00,'2022-08-25T20:32:26.568Z',1,'CHECKING');
INSERT INTO accounts (id, name, balance, creation_date, user_id, account_type) VALUES (2,'Primary Savings',10001.00,'2022-08-25T20:32:26.568Z',2,'SAVINGS');
INSERT INTO accounts (id, name, balance, creation_date, user_id, account_type) VALUES (3,'Primary Savings',5000.00,'2022-08-25T20:32:26.568Z',3,'SAVINGS');

INSERT INTO transactions (id, amount, creation_date, description, type, account_id) VALUES (1,2500.00,'2022-08-25T20:32:26.568Z','Payroll Direct Deposit','Income',1),(2,2500.00,'2022-08-25T20:32:26.568Z','Payroll Direct Deposit','Income',1),(3,2500.00,'2022-08-25T20:32:26.568Z','Payroll Direct Deposit','Income',1),(4,2500.00,'2022-08-25T20:32:26.568Z','Payroll Direct Deposit','Income',1);

INSERT INTO creditcards (id, user_id, card_number, ccv, expiration_date, total_limit, available_balance) VALUES (1, 1, 1234123412341234, 765, '2022-08-25T20:32:26.568Z', 15000.00, 15000.00);
INSERT INTO creditcards (id, user_id, card_number, ccv, expiration_date, total_limit, available_balance) VALUES (2, 3, 4567456745674567, 783, '2022-08-25T20:32:26.568Z', 10000.00, 10000.00);

INSERT INTO cctransactions (id, amount, description, creation_date, credit_card_id) VALUES (1, 500.00, 'shopping', '2022-08-25T20:32:26.568Z', 1);