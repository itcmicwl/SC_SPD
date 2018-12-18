
 [
 		name:'供应商生产厂商证照图片信息同步',
 		tableNames:['prov_mfrs_certificate_image'],
 		findFilenamesExpression:'''
 			def rows =db.queryValues('select file_path from prov_mfrs_certificate_image',null,String)
 		''',
 		endTransferDataExpression:''
 ]