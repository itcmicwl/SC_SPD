package gyqx.platform.sys.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import gyqx.platform.po.SysDict;
import gyqx.platform.po.SysDictValue;
import gyqx.platform.sys.dao.SysDictDao;
import gyqx.platform.sys.service.ISysDictService;
import gyqx.platform.sys.vo.SysDictValueVo;
import gyqx.platform.sys.vo.SysDictVo;

@Service
public class SysDictService implements ISysDictService
{
	@Resource
	private SysDictDao sysDictDao;
	@Resource
	private SimpleDao simpleDao;
	@Resource
	private SysConfigUtil configUtil;
	
	@Resource
	private SysAtomUtil atomUtil;

	
	public QueryResult<SysDict>  query(Map<String, Object> map) throws Exception{
		return sysDictDao.query(map);
	}
	
	public SysDict insert(SysDict dict) throws Exception{
		 if(simpleDao.checkExists(SysDict.class, "ename", dict.getEname())>0){
	        throw new Exception(dict.getEname()+"已存在！");//已冲突
	        }	
		//String id = atomUtil.newValue("dict_id");
		String id = dict.getEname();
		dict.setId(id);
		dict.setMt(new java.sql.Date(new Date().getTime()));
		dict.setVersion(0);
		return sysDictDao.insert(dict);
	}



	public SysDict update(SysDict dict) throws Exception{	
	   long  num=	simpleDao.queryForObject("select count(*) from sys_dict where ename= ?  and id<>?", Long.class, dict.getEname(),dict.getId());
	   if(num>0){
		   ValidateException.Throw("ename", dict.getEname()+"已存在！",dict.getEname() );
		   
		 //  throw new Exception(dict.getEname()+"已存在！");
	   }
		return sysDictDao.update(dict);

	}
	public void delete(String id) throws Exception{
		sysDictDao.delete(id);
	}
	
	
	public QueryResult<SysDictValueVo>  queryDictValueVos (Map<String,Object> map) throws Exception{
		return  sysDictDao.queryDictValueVos(map);
	}

    public SysDictValue updateDictValue(SysDictValue dv) throws Exception{
    	  long  num=	simpleDao.queryForObject("select count(*) from sys_dict_value where  dict_id=? and( ename= ? or val=? ) and id<>? ", Long.class,dv.getDictId(), dv.getEname(),dv.getVal(),dv.getId());
   	   if(num>0){
   		   throw new Exception(dv.getEname()+"已存在或编码"+dv.getVal()+"冲突！");
   	   }
    	return sysDictDao.updateDictValue(dv);
    }
    public SysDictValue insertDictValue(SysDictValue dv) throws Exception{
    	  long  num=	simpleDao.queryForObject("select count(*) from sys_dict_value where  dict_id=? and( ename= ? or val=? )  ", Long.class,dv.getDictId(), dv.getEname(),dv.getVal());
      	   if(num>0){
       		   throw new Exception(dv.getEname()+"已存在或编码"+dv.getVal()+"冲突！");
      	   }
    	
    	String id = atomUtil.newValue("dict_value_id");
    	dv.setId(id);
    	return sysDictDao.insertDictValue(dv);
    }
   
    public  void  deleteDictValue(String id) throws Exception{
    	sysDictDao.deleteDictValue(id);
   }

	@Override
	public QueryResult<SysDict> getDictList(QueryInfo<SysDictVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		List<SysDict> list = sysDictDao
				.getDictListPage(queryInfo.getQueryObject());
		return PageUtils.endPage(list);
	}

}
