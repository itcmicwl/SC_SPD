package gyqx.platform.sys.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import common.base.PinYin;
import common.db.SimpleDao;
import common.db.exception.SqlInjectException;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.platform.po.*;
import gyqx.platform.sys.dao.SysOrgDao;
import gyqx.platform.sys.service.ISysOrgService;

@Service
public class SysOrgService implements ISysOrgService
{
	@Resource
	private SysOrgDao sysOrgDao;
	
	@Resource
	private SysConfigUtil configUtil;
	
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private SimpleDao simpleDao;

	@Override
	public SysOrg addOrg(SysOrg org) throws Exception {
		
		String id = configUtil.getValue("sys.name")+":"+atomUtil.newValue("org_id");
		org.setId(id);  
	
		org.setFlag("1");
		org.setLastUpdateDatetime(new java.sql.Date(new Date().getTime()));
		org.setVersion(0);
		if(StringUtils.isEmpty(org.getPid())){
			org.setPid("/");
		}
		
		//else if(org.getPid().contains("company")){
			   //前端pid 存的是 pcode  需要查询找到真实的pid
		else if(org.getPid().indexOf(":")>0&&(org.getPid().indexOf("p")==0||org.getPid().indexOf("h")==0||org.getPid().indexOf("c")==0)){

		    SysOrg parentOrg =(SysOrg)	simpleDao.getByFieldName(SysOrg.class, "code", org.getPid());
			org.setPid(parentOrg.getId());
		}
		org.setShortPinyin(PinYin.getShortPinyin(org.getCname()));
		// 查重处理
		   long  num=	simpleDao.queryForObject("select count(*) from sys_org where ename= ?  and flag=1 and corp_id=? ", Long.class, org.getEname(),org.getCorpId());
		   if(num>0){
			        ValidateException.Throw("ename", org.getEname() +  "已存在！",org.getEname() );
				   }
		   num =simpleDao.queryForObject("select count(*) from sys_org where cname= ? and flag=1  and corp_id=?  ", Long.class, org.getCname(),org.getCorpId());
		   if(num>0){
		        ValidateException.Throw("cname", org.getCname() +  "已经存在！",org.getCname() );
			   }
		return sysOrgDao.addOrg(org);
	}

	@Override
	public SysOrg updateOrg(SysOrg org) throws Exception {
		org.setShortPinyin(PinYin.getShortPinyin(org.getCname()));
		org.setLastUpdateDatetime(new java.sql.Date(new Date().getTime()));
		//查重处理
		 long  num=	simpleDao.queryForObject("select count(*) from sys_org where ename= ?  and flag=1  and id<> ? and corp_id= ? ", Long.class, org.getEname(),org.getId(),org.getCorpId());
		 if(num>0){
		           ValidateException.Throw("ename", org.getEname() +  "已存在！",org.getEname() );
			      }
		 num =simpleDao.queryForObject("select count(*) from sys_org where cname= ? and flag=1  and id<> ?  and corp_id= ?", Long.class, org.getCname(),org.getId(),org.getCorpId());
		  if(num>0){
		            ValidateException.Throw("cname", org.getCname() +  "已经存在！",org.getCname() );
			       }
		 // if(org.getPid().contains("company")){  //pid存的是pcode
		else if (org.getPid().indexOf(":") > 0 && (org.getPid().indexOf("p") == 0 || org.getPid().indexOf("h") == 0
				|| org.getPid().indexOf("c") == 0)) {

			String oldCode = org.getCode();// 暂存原有code
			SysOrg parentOrg = (SysOrg) simpleDao.getByFieldName(SysOrg.class, "code", org.getPid());
			org.setPid(parentOrg.getId());

			// 需要判断 是否修改机构层级，如修改了需要重新生成code
			org = sysOrgDao.codeMgr(org, parentOrg);
			// 是否有子机构 ，有则所有子机构的code 都要调整
			long sonNum = simpleDao.queryForObject("select count(*) from sys_org where pid = ?", Long.class,
					org.getId());
			if (sonNum > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("oldCode", oldCode);
				map.put("newCode", org.getCode());
				sysOrgDao.updateAllSonCode(map);
			}
		}
		 
		  
		return sysOrgDao.updateOrg(org);
	}

	@Override
	public QueryResult<SysOrg> listOrg(QueryInfo queryInfo) throws Exception{

		return sysOrgDao.listOrg(queryInfo);
	}

	@Override
	public void deleteSysOrg(SysOrg org) throws Exception {
		 sysOrgDao.deleteSysOrg(org);
		
	}
	public List getAll() throws Exception{
		return sysOrgDao.getAll();
	}
	
	public List query(Map<String, Object> map) throws Exception{
		return sysOrgDao.query(map);
	}
	public List getOrgVo(Map<String,Object> map) throws Exception{
		return sysOrgDao.getOrgVo(map);
	}
	
    public void stateDeleteOrgs(Map<String,Object> map)throws Exception {
    	//判断是否有子机构，有则不允许删除
    
    	long num=simpleDao.queryForObject("select count(*) from sys_org where pid = ? and flag=1", Long.class, map.get("id"));
    	  if(num>0){
   		   ValidateException.Throw("code", "该机构存在子机构，不允许删除！",null );
   		   
   		 //  throw new Exception(dict.getEname()+"已存在！");
   	   }
    	 sysOrgDao.stateDeleteOrgs(map);
    }
}
