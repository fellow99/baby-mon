CREATE VIEW [V_DATE_RECORD] AS 
select (RECORD_DATE + 1000*60*60*8) / (1000*60*60*24) * (1000*60*60*24) RECORD_DATE,
RECORD_TYPE,
(case when RECORD_TYPE = 'milk' then sum(AMOUNT)
      when RECORD_TYPE = 'juice' then sum(AMOUNT)
      when RECORD_TYPE = 'shit' then sum(AMOUNT)
      else avg(AMOUNT)
end) AMOUNT
 from T_RECORD 
group by (RECORD_DATE + 1000*60*60*8) / (1000*60*60*24) * (1000*60*60*24), RECORD_TYPE;

