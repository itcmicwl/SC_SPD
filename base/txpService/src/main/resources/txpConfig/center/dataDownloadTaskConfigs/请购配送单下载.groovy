
 [
	name:'请购配送单下载',
	forSingleSite:true,
	tableConfigs:[
		[
			tableName:'distr_bill',
			siteIdFieldName:'purchase_company_id'
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
