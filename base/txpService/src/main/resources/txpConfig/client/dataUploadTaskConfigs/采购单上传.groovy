[
	name:'采购单上传',
	tableConfigs:[
		[
			tableName:'purchase',
			compareExpression:"status>10"
		],
		[
			tableName:'purchase_list',
			parentTableName:'purchase',
			compareExpression:'not exists(select * from purchase m where m.id = pid and m.status<=10  ) '
		]
	]
 ]
