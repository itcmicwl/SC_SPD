
 [
	name:'手术包对应品种上传',
	tableConfigs:[
		[
			tableName:'operating_package_info'
		],[
			tableName:'operating_package_goods_info',
			parentTableName:'operating_package_info',
			fkName:'temp_id'
		]
	]
 ]
