
 [
 	name:'生产厂商信息同步',
 		tableConfigs:[
 			[
 				tableName:'bas_mfrs_info' 					
 			],
 			[
 				tableName:'bas_mfrs_certificate'
 			],
 			[
 				tableName:'bas_mfrs_certificate_image'
 			]
 		],
 		idempotentExpression:"(new Date()).format('yyyyMMdd_HHmmss_SSS')"
 ]
	 	