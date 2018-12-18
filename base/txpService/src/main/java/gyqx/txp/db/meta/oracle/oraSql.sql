-- select * from TT_A
-- select cast(sysdate as timestamp) from dual
-- select * from user_tables;
-- select * from user_tab_comments;
select * from user_tab_columns ;
select * from user_col_comments;
-- select * from user_indexes;
select cu.* from user_cons_columns cu;
select au.* from user_constraints au;


select col.TABLE_NAME,col.COLUMN_NAME,col.DATA_TYPE,
 col.CHAR_LENGTH,
 au.CONSTRAINT_TYPE,
  col.DATA_PRECISION,
  col.DATA_SCALE,
  col.NULLABLE,
  cc.COMMENTS 
from user_tab_columns col left join user_col_comments cc 
       on col.TABLE_NAME = cc.TABLE_NAME and col.COLUMN_NAME = cc.COLUMN_NAME
       left join user_cons_columns cu on col.TABLE_NAME = cu.TABLE_NAME and col.COLUMN_NAME = cu.COLUMN_NAME
       left join user_constraints au on cu.constraint_name = au.CONSTRAINT_NAME
order by col.column_id
-- select to_char( SYSTIMESTAMP, 'yyyy-mm-dd hh24:mi:ss.ff3') from dual
--update tt_a set f4= to_timestamp('2006-01-02 13:40:20.2','yyyy-mm-dd hh24:mi:ss.ff')
