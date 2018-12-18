package fun;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import common.base.JSONHelper;
import common.base.KV;
import common.db.SimpleDao;
import common.db.datatable.DataTable;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.web.AjaxResult;
import gyqx.platform.po.SysAtom;
import gyqx.platform.po.SysDict;
import gyqx.platform.po.SysDictValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context.xml" })
public class SimpleDaoTest 
{
	@Resource
	private SimpleDao dao;

	@Before
	public void setUp() throws Exception {
	}

	//@Test
	public void test() throws Exception 
	{
		QueryInfo qi = new QueryInfo();
		qi.setQueryObject(new KV("ename","org_id"));
		qi.setOrderBy("ename");
		qi.setPredicate("ename=?");
		
		QueryResult<SysAtom> qr = dao.queryForList(qi, SysAtom.class);
		System.out.println(System.currentTimeMillis());

		for(int i = 0; i< 100;i++)
			qr = dao.queryForList(qi, SysAtom.class);
		System.out.println(System.currentTimeMillis());
		
		List<SysDictValue> list = (List<SysDictValue>) dao.queryBeanList(SysDictValue.class," dictId=? and ename like ? and version>=?", null, "CCMJ","%架",1);
		
		list = (List<SysDictValue>) dao.queryBeanList(SysDictValue.class," dictId=? or ename like ?", "dictId",new KV( "dictId","CCMJ","ename","%架"));
		
		
		System.out.println(System.currentTimeMillis());
		
	}
	@Test
	public void testDataTable() throws IOException
	{
		DataTable dt = dao.queryDataTable("select * from bas_mfrs_info");
		
		String s = JSONHelper.toString(dt.getRows());
		
	}
	//@Test
	public void testUpdate() throws Exception
	{
		SysAtom atom = dao.get(SysAtom.class, "test");
		atom.setFormat(null);
		atom.setPrefix(null);
		dao.updateNotNullFields(atom);
		
		atom = dao.get(SysAtom.class, "test");
		atom.setPrefix("pre001");
		atom.setFormat("%07d");		
		dao.updateFields(atom, "format,prefix");
		
		double d = 1.0 /5.0 * 5.0;
		
		System.out.println(d);
		
	}
	//@Test
	public void testMultSql()
	{
		String roleId="";
		String sql="delete from sys_user_role where role_id ='%s' ;\n"
				+ "delete from sys_role_menu where role_id = '%s' ;\n"
				+ "delete from sys_admin_mgr_role where role_id = '%s'";
		sql = String.format(sql, roleId,roleId,roleId);
		dao.executeSql(sql);
		System.out.println("ok");
	}
	
	//@Test
	public void testBatchSql()
	{
		dao.batch().executeSql("delete from sys_user_role where role_id =?", "a")
					.executeSql("delete from sys_role_menu where role_id = ? ", "b")
					.executeSql("delete from sys_admin_mgr_role where role_id = ?","c")
					.done();

		System.out.println("ok");
		
		
	}	

}
