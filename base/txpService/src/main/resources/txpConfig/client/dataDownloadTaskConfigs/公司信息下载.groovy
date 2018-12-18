
 [
 	name:'公司信息下载',
 		tableConfigs:[
 			[
 				tableName:'bas_company_info' 				
 			],
 			[
 				tableName:'bas_company_certificate'
 			],
 			[
 				tableName:'bas_company_certificate_image' 
 			]
 		],
 		idempotentExpression:"(new Date()).format('yyyyMMdd_HHmmss_SSS')"
 ]
	 	