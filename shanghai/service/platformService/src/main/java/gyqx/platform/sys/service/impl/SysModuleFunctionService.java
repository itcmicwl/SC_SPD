package gyqx.platform.sys.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import gyqx.platform.sys.dao.SysModuleFunctionDao;
import gyqx.platform.sys.service.ISysModuleFunctionService;
import gyqx.platform.sys.vo.SysModuleFunctionVo;

@Service
public class SysModuleFunctionService implements ISysModuleFunctionService {
	@Resource
	SysModuleFunctionDao sysModuleFunctionDao;

	@Override
	public QueryResult<SysModuleFunctionVo> getSysModuleFunctionVoList(QueryInfo<SysModuleFunctionVo> queryInfo) {
		QueryResult<SysModuleFunctionVo> lstSysConfig = new QueryResult<SysModuleFunctionVo>();
		PageInfo<SysModuleFunctionVo> sysModuleFunction = sysModuleFunctionDao.getSysModuleFunctionVoList(queryInfo);
		lstSysConfig.setData(sysModuleFunction.getList());
		lstSysConfig.setPageNum(sysModuleFunction.getPageNum());
		lstSysConfig.setPageSize(sysModuleFunction.getPageSize());
		lstSysConfig.setTotal(sysModuleFunction.getTotal());
		return lstSysConfig;
	}

	@Override
	public SysModuleFunctionVo getSysModuleFunctionVoByid(SysModuleFunctionVo sysModuleFunctionVo) {
		return sysModuleFunctionDao.getSysModuleFunctionVoByid(sysModuleFunctionVo);
	}

	@Override
	public boolean existCodeInSys(SysModuleFunctionVo sysModuleFunctionVo) {
		return sysModuleFunctionDao.existCodeInSys(sysModuleFunctionVo) > 0;
	}

	@Override
	public boolean existNameInModule(SysModuleFunctionVo sysModuleFunctionVo) {
		return sysModuleFunctionDao.existNameInModule(sysModuleFunctionVo) > 0;
	}

	@Override
	public int insertSysModuleFunctionVo(SysModuleFunctionVo sysModuleFunctionVo) {
		return sysModuleFunctionDao.insertSysModuleFunctionVo(sysModuleFunctionVo);
	}

	@Override
	public int setSysModuleFunctionVo(SysModuleFunctionVo sysModuleFunctionVo) {
		sysModuleFunctionVo.setMt(new Date());
		return sysModuleFunctionDao.setSysModuleFunctionVo(sysModuleFunctionVo);
	}

	@Override
	public int delSysModuleFunction(String id) {
		return sysModuleFunctionDao.delSysModuleFunction(id);
	}

}
