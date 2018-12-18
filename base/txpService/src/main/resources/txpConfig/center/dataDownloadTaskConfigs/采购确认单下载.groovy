
 [
	name:'采购确认单下载',
	forSingleSite:true,
	tableConfigs:[
		[
			tableName:'purconfirm',
			siteIdFieldName:'purchase_company_id'
		],
		[
			tableName:'purconfirm_list',
			parentTableName:'purconfirm'
		]
	]
 ]
	 	