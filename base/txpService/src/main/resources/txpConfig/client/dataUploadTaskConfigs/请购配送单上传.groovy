
 [
	name:'请购配送单上传',
	tableConfigs:[
		[
			tableName:'distr_bill'
		],
		[
			tableName:'distr_bill_list',
			parentTableName:'distr_bill'
		],
		[
			tableName:'distr_bill_unique_list',
			parentTableName:'distr_bill_list'
		],
		[
			tableName:'distr_bill_humiture_list',
			parentTableName:'distr_bill_list'
		]
	]
 ]
