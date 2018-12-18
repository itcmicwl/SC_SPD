
-- TABLE_NAME,COLUMN_NAME,ORDINAL_POSITION as pos,COLUMN_KEY as keyflag,DATA_TYPE as dtype
-- ,IS_NULLABLE as nullflag,CHARACTER_MAXIMUM_LENGTH as len,NUMERIC_PRECISION as prec,NUMERIC_SCALE as scale,COLUMN_COMMENT as remark 
SELECT 
	d.name  TABLE_NAME,
	a.colorder pos,
	a.name COLUMN_NAME,
	 (case when 
	 (SELECT count(*) 
		 FROM sysobjects 
		 WHERE (name in 
		    (SELECT name
			 FROM sysindexes
			 WHERE (id = a.id) AND (indid in 
				 (SELECT indid
				 FROM sysindexkeys
				 WHERE (id = a.id) AND (colid in 
					 (SELECT colid
					 FROM syscolumns
					 WHERE (id = a.id) AND (name = a.name)
					 )
				 )
				 )
			 )
			 )
		 ) AND (xtype = 'PK') 
	 ) > 0 then 'pk' else '' end
	 ) keyflag,
	b.name dtype,
	COLUMNPROPERTY(a.id,a.name,'PRECISION') as len,
	isnull(COLUMNPROPERTY(a.id,a.name,'Scale'),0) as scale,
	 (case when a.isnullable=1 then 'yes'else '' end) nullflag,
	isnull(g.[value],'') AS remark
FROM  syscolumns a 
 left join systypes b on a.xtype=b.xusertype
 inner join sysobjects d on a.id=d.id  and  d.xtype='U' and d.name<>'dtproperties'
 left join syscomments e on a.cdefault=e.id
 left join sys.extended_properties g on a.id=g.major_id AND a.colid = g.minor_id  
--where d.name in ('Contact','StockBmps','AddressType')---查询具体的表，注释掉后就是查询整个数据库了
order by a.id,a.colorder