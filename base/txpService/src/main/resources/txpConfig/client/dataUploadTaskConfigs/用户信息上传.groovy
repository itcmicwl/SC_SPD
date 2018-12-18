
 [
 	name:'用户信息上传',
 		tableConfigs:[
 			[
 				tableName:'sys_user'
 			],
			[
 				tableName:'sys_user_login'
 			],
			[
				tableName:'sys_user_org'
			]
 		],
 		idempotentExpression:"(new Date()).format('yyyyMMdd_HHmmss_SSS')"
 ]
	 	