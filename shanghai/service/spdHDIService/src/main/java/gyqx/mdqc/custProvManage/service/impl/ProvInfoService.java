package gyqx.mdqc.custProvManage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import common.utils.UserOnlineStateUtils;
import gyqx.mdqc.custProvManage.dao.ProvInfoDao;
import gyqx.mdqc.custProvManage.service.IProvInfoService;
import gyqx.mdqc.custProvManage.vo.BasCompanyInfoVo;
@Service
public class ProvInfoService implements IProvInfoService {
	@Resource
	private ProvInfoDao provInfoDao;
	
	@Resource
	private SimpleDao simpleDao;
	
	@Resource
	private SysConfigUtil configUtil;
	@Resource
	private SysAtomUtil atomUtil;
		
	@Resource
	private UserOnlineStateUtils userOnline;

	@Override
	public QueryResult<BasCompanyInfoVo> getProvInfoList(QueryInfo<BasCompanyInfoVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(provInfoDao.getProvInfoList(queryInfo.getQueryObject()));
	}

	@Override
	public List<BasCompanyInfoVo> getProvInfo(BasCompanyInfoVo basCompanyInfoVo) throws Exception {
		return provInfoDao.getProvInfoList(basCompanyInfoVo);
	}
	
	
}
