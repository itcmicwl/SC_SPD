package gyqx.platform.sys.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.utils.SysAtomUtil;
import gyqx.platform.sys.dao.mapper.ISysModuleFunctionMapper;
import gyqx.platform.sys.vo.ModuleFunctionInfo;
import gyqx.platform.sys.vo.SysModuleFunctionVo;

@Repository
public class SysModuleFunctionDao {
	@Resource 
	ISysModuleFunctionMapper sysModuleFunctionVoMapper;
	@Resource
	private SimpleDao dao;
	@Resource
	private SysAtomUtil atomUtil;
	public PageInfo<SysModuleFunctionVo> getSysModuleFunctionVoList(QueryInfo<SysModuleFunctionVo> queryInfo){
		PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
		List<SysModuleFunctionVo> lstSysConfig = sysModuleFunctionVoMapper.getSysModuleFunctionVoList(queryInfo.getQueryObject());
		PageInfo<SysModuleFunctionVo> sysConfigPage = new PageInfo<SysModuleFunctionVo>(lstSysConfig);
		return sysConfigPage;
	}
	public SysModuleFunctionVo getSysModuleFunctionVoByid(SysModuleFunctionVo sysModuleFunctionVo){
		return sysModuleFunctionVoMapper.getSysModuleFunctionVoByid(sysModuleFunctionVo);
	}
	public Integer existCodeInSys(SysModuleFunctionVo sysModuleFunctionVo){
		return sysModuleFunctionVoMapper.existCodeInSys(sysModuleFunctionVo);
	}
	public Integer existNameInModule(SysModuleFunctionVo sysModuleFunctionVo){
		return sysModuleFunctionVoMapper.existNameInModule(sysModuleFunctionVo);
	}
	public int insertSysModuleFunctionVo(SysModuleFunctionVo sysModuleFunctionVo){
		String id =atomUtil.newValue("sys.funId");
		sysModuleFunctionVo.setId(id);
		return sysModuleFunctionVoMapper.inset(sysModuleFunctionVo);
	}
	public int setSysModuleFunctionVo(SysModuleFunctionVo sysModuleFunctionVo){
		return sysModuleFunctionVoMapper.setSysModuleFunction(sysModuleFunctionVo);
	}
	public int delSysModuleFunction(String id){
		return sysModuleFunctionVoMapper.delSysModuleFunction(id);
	}
	
	public List<ModuleFunctionInfo> listModuleFunctions(String moduleId)
	{
		String sql = "select m.id as module_id, m.ename as module_name, f.id as function_id,f.ename as function_name, f.remark ,d.url"
				+" from sys_module m left join sys_module_function f on m.id = f.module_id LEFT JOIN sys_module_function_design d on f.id= d.function_id "
				+" where f.module_id = ? ";
		return dao.queryForList(sql, ModuleFunctionInfo.class,moduleId);
	}
	
	
}
