
 [
 		name:'公司证照图片信息同步',
 		tableNames:['bas_company_certificate_image'],
 		findFilenamesExpression:'''
 			def rows =db.queryValues('select file_path from bas_company_certificate_image',null,String)
 		''',
 		endTransferDataExpression:''
 ]