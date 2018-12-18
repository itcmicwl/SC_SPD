
 [
 		name:'注册证图片信息同步',
 		tableNames:['bas_registration_image'],
 		findFilenamesExpression:'''
 			def rows =db.queryValues('select file_path from bas_registration_image',null,String)
 		''',
 		endTransferDataExpression:''
 ]