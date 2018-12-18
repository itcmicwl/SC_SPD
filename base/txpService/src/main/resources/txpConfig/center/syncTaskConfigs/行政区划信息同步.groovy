
 [
 	name:'行政区划信息同步',
 		tableConfigs:[
 			[
 				tableName:'bas_area' 				
 			]
 		], 
 		idempotentExpression:"(new Date()).format('yyyyMMdd_HHmmss_SSS')"
 ]
