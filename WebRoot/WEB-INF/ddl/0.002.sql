CREATE TABLE IF NOT EXISTS [T_VERSION] (
  [VERSION] INTEGER NOT NULL PRIMARY KEY, 
  [VERSION_DATE] DATETIME NOT NULL DEFAULT CURRENT_DATETIME, 
  [REMARK] TEXT);