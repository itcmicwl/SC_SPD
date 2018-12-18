
 [
 		name:'生产厂商证照图片信息同步',
 		tableNames:['bas_mfrs_certificate_image'],
 		findFilenamesExpression:'''
 			def rows =db.queryValues('select file_path from bas_mfrs_certificate_image',null,String)
 		''',
 		endTransferDataExpression:''
 ]