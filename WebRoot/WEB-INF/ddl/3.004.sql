CREATE VIEW [V_LAST_JUICE] AS 
select RECORD_TYPE, RECORD_DATE, AMOUNT from T_RECORD where RECORD_TYPE = 'juice' ORDER BY RECORD_DATE desc limit 1;
