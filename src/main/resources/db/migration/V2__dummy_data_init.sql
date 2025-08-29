-- Own account
INSERT INTO account (number, name, code) VALUES ('2002222222', 'OWN ACCOUNT', '0300');

-- Counterparty accounts
INSERT INTO account (number, name, code) VALUES ('0000009504010019', 'PPF BANKA A.S.', '6000');
INSERT INTO account (number, name, code) VALUES ('0000009505020008', 'PPF BANKA A.S.', '6000');
INSERT INTO account (number, name, code) VALUES ('0000009503010009', 'PPF BANKA A.S.', '6000');

INSERT INTO transactionType (code, type) VALUES (1012209, 'DPO');
INSERT INTO transactionType (code, type) VALUES (0, 'DPO');

INSERT INTO statement (number, period) VALUES ('196', '2022');
INSERT INTO statement (number, period) VALUES ('195', '2022');

INSERT INTO [transaction] (amount, currency, id, bankref, transactionId, bookingDate, postingDate, creditDebitIndicator, ownAccountNumber, productBankRef, specificSymbol, statement, transactionType, variableSymbol, detail1, counterPartyAccount)
VALUES (1500, 'CZK', '20221019:0000000219', 'PS221019SO314822', '4831716', '2022-10-19', '2022-10-19', 'CRDT', '2002222222', 'PS221019SO314822', '12', (SELECT statementId FROM statement WHERE number = 195), (SELECT trxTypeId FROM transactionType WHERE code = 1012209), '12', 'Posílám peníze', (SELECT accountId FROM account WHERE number = '2002222222'));

INSERT INTO [transaction] (amount, currency, id, bankref, transactionId, bookingDate, postingDate, creditDebitIndicator, ownAccountNumber, productBankRef, specificSymbol, statement, transactionType, variableSymbol, detail1, counterPartyAccount)
VALUES (1999, 'CZK', '20221019:0000000220', 'PS221019SO314822', '4831701', '2022-10-19', '2022-10-19', 'CRDT', '2002222222', 'PS221019SO314822', '12', (SELECT statementId FROM statement WHERE number = 195), (SELECT trxTypeId FROM transactionType WHERE code = 1012209), '12', 'Trvalý příkaz 8', (SELECT accountId FROM account WHERE number = '2002222222'));

INSERT INTO [transaction] (amount, currency, id, bankref, transactionId, bookingDate, postingDate, creditDebitIndicator, ownAccountNumber, productBankRef, specificSymbol, statement, transactionType, variableSymbol, detail1, counterPartyAccount)
VALUES (2000, 'CZK', '20221019:0000000221', 'PS221019SO314823', '4831700', '2022-10-19', '2022-10-19', 'CRDT', '2002222222', 'PS221019SO314823', '61', (SELECT statementId FROM statement WHERE number = 195), (SELECT trxTypeId FROM transactionType WHERE code = 1012209), '61', 'Na dárky', (SELECT accountId FROM account WHERE number = '2002222222'));

INSERT INTO [transaction] (amount, currency, id, bankref, transactionId, bookingDate, postingDate, creditDebitIndicator, ownAccountNumber, productBankRef, specificSymbol, statement, transactionType, variableSymbol, detail1, counterPartyAccount)
VALUES (100, 'CZK', '20221018:0000003607', 'PS221018SO314645', '4831425', '2022-10-18', '2022-10-18', 'CRDT', '2002222222', 'PS221018SO314645', '12', (SELECT statementId FROM statement WHERE number = 195), (SELECT trxTypeId FROM transactionType WHERE code = 0), '12', 'Příspěvek', (SELECT accountId FROM account WHERE number = CAST('0000009504010019' AS BIGINT)));

INSERT INTO [transaction] (amount, currency, id, bankref, transactionId, bookingDate, postingDate, creditDebitIndicator, ownAccountNumber, productBankRef, specificSymbol, statement, transactionType, variableSymbol, detail1, counterPartyAccount)
VALUES (1594, 'CZK', '20221018:0000003608', 'PS221018SO314645', '4831381', '2022-10-18', '2022-10-18', 'DBIT', '2002222222', 'PS221018SO314645', '12', (SELECT statementId FROM statement WHERE number = 196), (SELECT trxTypeId FROM transactionType WHERE code = 1012209), '12', 'Platba elektřiny', (SELECT accountId FROM account WHERE number = CAST('0000009505020008' AS BIGINT)));
