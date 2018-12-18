
 [
 	name:'注册证信息下载',
 		tableConfigs:[
 			[
 				tableName:'bas_regist_goods' 				
 			],
 			[
 				tableName:'bas_registration_info'
 			],
 			[
 				tableName:'bas_registration_image' 
 			]
 		],
 		idempotentExpression:"(new Date()).format('yyyyMMdd_HHmmss_SSS')"
 ]
	 	