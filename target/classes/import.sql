INSERT INTO users (id, email, password) VALUES (1,'testuser@gmail.com','password');

INSERT INTO accounts (id, name, balance, description, creation_date, user_id) VALUES (1,'Primary Checking',10000.00,'A really nice bank account to track my checking account transactions','2022-08-25T20:32:26.568Z',1);

INSERT INTO transactions (id, amount, description, type, account_id) VALUES (1,2500.00,'Payroll Direct Deposit','Income',1),(2,2500.00,'Payroll Direct Deposit','Income',1),(3,2500.00,'Payroll Direct Deposit','Income',1),(4,2500.00,'Payroll Direct Deposit','Income',1);