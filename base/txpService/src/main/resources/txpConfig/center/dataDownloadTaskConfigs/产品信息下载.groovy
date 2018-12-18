
 [
 	name:'产品信息下载',
 		tableConfigs:[
 			[
 				tableName:'bas_goods_info' 				
 			]
 		],
 		idempotentExpression:"(new Date()).format('yyyyMMdd_HHmmss_SSS')"
 ]
	 	