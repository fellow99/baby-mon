CREATE TABLE IF NOT EXISTS [T_RECORD] (
  [ID] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, 
  [RECORD_TYPE] VARCHAR(32) NOT NULL, 
  [RECORD_DATE] DATETIME NOT NULL, 
  [AMOUNT] NUMERIC(8, 2), 
  [INFO] TEXT)