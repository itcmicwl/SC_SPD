package gyqx.platform.sys.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import common.db.SimpleDao;
import common.db.exception.SqlInjectException;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.po.SysOrg;
import gyqx.platform.sys.dao.mapper.SysOrgMapper;

@Repository
public class SysOrgDao 
{
	@Resource
	private SysOrgMapper mapper;
	@Resource
	private SimpleDao dao;
	
	
private SqlSession session;
	
	public void setSession(SqlSession session) {
		this.session = session;
	}
	
	public List getAll()  throws Exception{
		return mapper.getAll();

	}
	
	public List query(Map<String, Object> map)  throws Exception{
		return mapper.query(map);
	}
	public List getOrgVo(Map<String,Object> map) throws Exception{
		return mapper.getOrgVo(map);
	}
	public SysOrg get(String id)
	{
		return dao.get(SysOrg.class, id);		
	}
	
	public SysOrg addOrg(SysOrg org) throws Exception
	{
		String pid = org.getPid();
		SysOrg parent = get(pid);
		org= codeMgr(org,parent);
		dao.insert(org);
		return org;
	}

	
	
	public SysOrg codeMgr(SysOrg org,SysOrg parent)throws Exception{
		//if(parent != null && StringUtils.hasText(org.getPid())){
		if(parent != null && StringUtils.hasText(org.getPid())&&org.getPid()!="/"){
			//lock this 
			dao.updateVersion(SysOrg.class, org.getPid());
			
			List<String> maxCodes = dao.queryValues("select max(code) from sys_org where pid = ? ",String.class,org.getPid());

			String code = StringUtils.hasText(parent.getCode())? parent.getCode() +"." + "001" : "001";
			if(maxCodes.size()> 0 && StringUtils.hasText(maxCodes.get(0)))
			{
				String maxCode = maxCodes.get(0);
				int lastDotIndex = maxCode.lastIndexOf(".");
				String lastSeg = maxCode.substring( lastDotIndex +1,maxCode.length());				
				code = maxCode.substring(0,lastDotIndex+1) + String.format("%03d", Integer.parseInt(lastSeg)+1);
			}
			org.setCode(code);
		}
		else{   
			List<String> maxCodes = dao.queryValues("select max(code) from sys_org where corp_id = ? and (pid=''or pid is null or pid ='/')",String.class,org.getCorpId());

			//String code = StringUtils.hasText(parent.getCode())? parent.getCode() +"." + "001" : "001";
			String code = org.getCorpId()+":001";
			if(maxCodes.size()> 0 && StringUtils.hasText(maxCodes.get(0)))
			{
				String maxCode = maxCodes.get(0);
				int lastDotIndex = maxCode.lastIndexOf(":");
				String lastSeg = maxCode.substring( lastDotIndex +1,maxCode.length());				
				code = maxCode.substring(0,lastDotIndex+1) + String.format("%03d", Integer.parseInt(lastSeg)+1);
			}
			org.setCode(code);
		}
		return org;
	}
	public void updateAllSonCode(Map<String,Object> map)throws Exception {
		mapper.updateAllSonCode(map);
	}
	public SysOrg updateOrg(SysOrg org) throws Exception 
	{
		return dao.updateAndGet(org);
	}


	public QueryResult<SysOrg> listOrg(QueryInfo queryInfo) throws Exception 
	{
		return dao.queryForList(queryInfo,SysOrg.class);
	}

	public void deleteSysOrg(SysOrg org) throws Exception 
	{
		dao.delete(org);
	}
	
    public void stateDeleteOrgs(Map<String,Object> map)throws Exception {
    	mapper.stateDeleteOrgs(map);
	}
}