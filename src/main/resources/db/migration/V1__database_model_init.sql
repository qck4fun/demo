-- CREATE DATABASE playground;

-- MS SQL server
CREATE TABLE [transaction](
	[trxId]                    [bigint] IDENTITY(1000,1) NOT NULL,
	[amount]                   [numeric](19, 2)    NOT NULL,
	[currency]                 [nvarchar](3)       NOT NULL,
	[id]                       [nvarchar](20)      NOT NULL,
	[bankref]                  [nvarchar](20)      NOT NULL,
	[transactionId]            [nvarchar](20)      NOT NULL,
	[bookingDate]              [date]              NULL,
	[postingDate]              [date]              NOT NULL,
	[creditDebitIndicator]     [varchar](4)        NULL,
	[ownAccountNumber]         [nvarchar](20)      NULL,
	[counterPartyAccount]      [bigint]            NOT NULL,
	[detail1]                  [nvarchar](50)      NULL,
	[detail2]                  [nvarchar](50)      NULL,
	[detail3]                  [nvarchar](50)      NULL,
	[detail4]                  [nvarchar](50)      NULL,
	[productBankRef]           [nvarchar](50)      NULL,
	[transactionType]          [bigint]            NOT NULL,
	[statement]                [bigint]            NOT NULL,
	[constantSymbol]           [varchar](10)       NULL,
	[specificSymbol]           [varchar](10)       NULL,
	[variableSymbol]           [varchar](10)       NULL
)
GO

CREATE TABLE [transactionType](
	[trxTypeId]                [bigint] IDENTITY(1000,1) NOT NULL,
	[type]                     [nvarchar](20)            NOT NULL,
	[code]                     [int]                     NOT NULL
)
GO

CREATE TABLE [statement](
	[statementId]              [bigint] IDENTITY(1000,1) NOT NULL,
	[number]                   [nvarchar](20)            NOT NULL,
	[period]                   [nvarchar](20)            NOT NULL,
	[description]              [nvarchar](1000)          NULL
)
GO

CREATE TABLE [account](
	[accountId]              [bigint] IDENTITY(1000,1) NOT NULL,
	[name]                   [nvarchar](50)            NOT NULL,
	[number]                 [nvarchar](20)            NOT NULL,
	[code]                   [nvarchar](4)             NOT NULL
)
GO


ALTER TABLE [transaction]
    ADD CONSTRAINT PK_transaction_trxId PRIMARY KEY (trxId)
GO

ALTER TABLE [transactionType]
    ADD CONSTRAINT PK_transactionType_trxTypeId PRIMARY KEY (trxTypeId)
GO

ALTER TABLE [statement]
    ADD CONSTRAINT PK_statement_statementId PRIMARY KEY (statementId)
GO

ALTER TABLE [account]
    ADD CONSTRAINT PK_account_accountId PRIMARY KEY (accountId)
GO

ALTER TABLE [transaction]
    ADD CONSTRAINT FK_transaction_counterPartyAccount FOREIGN KEY (counterPartyAccount) REFERENCES account(accountId)
GO

ALTER TABLE [transaction]
    ADD CONSTRAINT FK_transaction_transactionType FOREIGN KEY (transactionType) REFERENCES transactionType(trxTypeId)
GO

ALTER TABLE [transaction]
    ADD CONSTRAINT FK_transaction_statement FOREIGN KEY (statement) REFERENCES statement(statementId)
GO

