package oracle;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import gyqx.spdhdi.po.BasCompanyCertificate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/oracle-test.xml" })

@Transactional(transactionManager="txManager")
@Commit
public class SqlTest 
{
	@Resource
	JdbcTemplate jdbcTemplate;
	@Before
	public void setUp() throws Exception {
	}
	@Test
	public void test()
	{
		Date date = new Date();
		boolean b = date.getClass().equals(Date.class);
		
		java.sql.Date d0 = new java.sql.Date(123);
		b = d0.getClass().equals(Date.class);
		
		String sqlUpdate = "update BAS_COMPANY_CERTIFICATE set ID=?,COMPANY_ID=?,CERT_KIND=?,CERT_CODE=?,EXPDT_BEGIN_DATE=?,EXPDT_END_DATE=?,IS_VALIDING=?,UXID=?,FILL_DATE=?,LAST_UPDATE_DATETIME=?,VERSION=VERSION +1  where ID=? and VERSION=?";
		BasCompanyCertificate localBasCompanyCertificate ; 
				
		
		List<BasCompanyCertificate> certs = jdbcTemplate.query("select * from BAS_COMPANY_CERTIFICATE  where id=?",
				new BeanPropertyRowMapper(BasCompanyCertificate.class),"sconf-903");
		localBasCompanyCertificate = certs.get(0);

		int ia = jdbcTemplate.update(sqlUpdate, new PreparedStatementSetter(){

			@Override
			public void setValues(PreparedStatement paramPreparedStatement) throws SQLException {
				paramPreparedStatement.setObject(1, localBasCompanyCertificate.getId());
			    paramPreparedStatement.setObject(2, localBasCompanyCertificate.getCompanyId());
			    paramPreparedStatement.setObject(3, localBasCompanyCertificate.getCertKind());
			    paramPreparedStatement.setObject(4, localBasCompanyCertificate.getCertCode());
			    paramPreparedStatement.setObject(5, localBasCompanyCertificate.getExpdtBeginDate());
			    paramPreparedStatement.setObject(6, localBasCompanyCertificate.getExpdtEndDate());
			    paramPreparedStatement.setObject(7, localBasCompanyCertificate.getIsValiding());
			    paramPreparedStatement.setObject(8, localBasCompanyCertificate.getUid());
			    paramPreparedStatement.setObject(9, localBasCompanyCertificate.getFillDate());
			    paramPreparedStatement.setObject(10,  new java.sql.Timestamp(new Date().getTime()));
			    paramPreparedStatement.setObject(11, localBasCompanyCertificate.getId());
			    paramPreparedStatement.setObject(12, Integer.valueOf(localBasCompanyCertificate.getVersion()));
				
			}});
		
		
		System.out.println("ok:" + ia);
	}
	
}
