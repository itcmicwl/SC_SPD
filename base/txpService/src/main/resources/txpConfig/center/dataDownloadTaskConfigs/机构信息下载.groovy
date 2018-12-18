
 [
 	name:'机构信息下载',
 		tableConfigs:[
 			[
 				tableName:'sys_org'
 			]
 		],
 		idempotentExpression:"(new Date()).format('yyyyMMdd_HHmmss_SSS')"
 ]
	 	