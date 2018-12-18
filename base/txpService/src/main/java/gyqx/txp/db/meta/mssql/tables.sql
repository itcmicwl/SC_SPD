select t.*,ex.value from sysobjects t left join sys.extended_properties ex  
  on t.id = ex.major_id and ex.minor_id = 0
  where  t.xtype='U' and t.name<>'dtproperties' 