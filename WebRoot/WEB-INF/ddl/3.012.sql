CREATE VIEW [V_DATE_MILK_TYPE] AS 
select
(RECORD_DATE + 1000*60*60*8) / (1000*60*60*24) * (1000*60*60*24) RECORD_DATE,
(case when INFO like '%牛奶%' then 'cow' else 'mimi' end) RECORD_TYPE,
sum(amount) AMOUNT
from T_RECORD
where RECORD_TYPE = 'milk'
group by (RECORD_DATE + 1000*60*60*8) / (1000*60*60*24) * (1000*60*60*24), (case when INFO like '%牛奶%' then 'cow' else 'mimi' end);

