package gyqx.spdhdi.myInfos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.SysAtomUtil;
import common.utils.UserOnlineStateUtils;
import gyqx.spdhdi.myInfos.dao.ProvHosInfoVoDao;
import gyqx.spdhdi.myInfos.service.IProvHosInfoVoService;
import gyqx.spdhdi.myInfos.vo.HosInfoVo;
import gyqx.spdhdi.myInfos.vo.HosProvVo;
import gyqx.spdhdi.myInfos.vo.ProvHosInfoVo;
import gyqx.spdhdi.po.BasCompanyInfo;

@Service	
public class ProvHosInfoVoService implements IProvHosInfoVoService {
	@Resource
	ProvHosInfoVoDao provHosInfoVoDao;
	@Resource
	SimpleDao simpleDao;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private UserOnlineStateUtils  userOnlineStateUtils;
	
	@Override
	public QueryResult<ProvHosInfoVo> getProvHosInfoVoList(QueryInfo<ProvHosInfoVo> queryInfo) throws Exception {
		QueryResult<ProvHosInfoVo> lstProvHosInfoVosInfo = new QueryResult<ProvHosInfoVo>();
		queryInfo.getQueryObject().setProvId(userOnlineStateUtils.getCurrent().getCorpId());
		PageInfo<ProvHosInfoVo> provHosInfoVoPage = provHosInfoVoDao.getProvHosInfoVoList(queryInfo);
		PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
		lstProvHosInfoVosInfo.setData(provHosInfoVoPage.getList());
		lstProvHosInfoVosInfo.setPageNum(provHosInfoVoPage.getPageNum());
		lstProvHosInfoVosInfo.setPageSize(provHosInfoVoPage.getPageSize());
		lstProvHosInfoVosInfo.setTotal(provHosInfoVoPage.getTotal());
		return lstProvHosInfoVosInfo;
	}
	
	@Override
	public QueryResult<HosInfoVo> getHosInfoVoList(QueryInfo<HosInfoVo> queryInfo) throws Exception {
		QueryResult<HosInfoVo> lstProvHosInfoVosInfo = new QueryResult<HosInfoVo>();
		queryInfo.getQueryObject().setId(userOnlineStateUtils.getCurrent().getCorpId());
		PageInfo<HosInfoVo> provHosInfoVoPage = provHosInfoVoDao.getHosInfoVoList(queryInfo);
		lstProvHosInfoVosInfo.setData(provHosInfoVoPage.getList());
		return lstProvHosInfoVosInfo;
	}
	
	
	@Override
	public QueryResult<HosProvVo> getHosProvVoList(QueryInfo<HosProvVo> queryInfo) throws Exception {
		QueryResult<HosProvVo> lstProvHosInfoVosInfo = new QueryResult<HosProvVo>();
		queryInfo.getQueryObject().setId(userOnlineStateUtils.getCurrent().getCorpId());
		PageInfo<HosProvVo> provHosInfoVoPage = provHosInfoVoDao.getHosProvVoList(queryInfo);
		lstProvHosInfoVosInfo.setData(provHosInfoVoPage.getList());
		return lstProvHosInfoVosInfo;
	}
	
	/**
	 * 查询医院对应供应商信息
	 * @param queryInfo
	 * @return
	 * @throws Exception
	 */
	@Override
	public QueryResult<ProvHosInfoVo> getHosProvCompanyInfoVoList(QueryInfo<ProvHosInfoVo> queryInfo) throws Exception {
		QueryResult<ProvHosInfoVo> lstProvHosInfoVo = new QueryResult<ProvHosInfoVo>();
		PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
		PageInfo<ProvHosInfoVo> provHosInfoVoPage = provHosInfoVoDao.getHosProvCompanyInfoVoList(queryInfo);
		lstProvHosInfoVo.setData(provHosInfoVoPage.getList());
		lstProvHosInfoVo.setPageNum(provHosInfoVoPage.getPageNum());
		lstProvHosInfoVo.setPageSize(provHosInfoVoPage.getPageSize());
		lstProvHosInfoVo.setTotal(provHosInfoVoPage.getTotal());		
		return lstProvHosInfoVo;
	}
	
	@Override
	public ProvHosInfoVo getProvHosInfoByid(String id) {
		return provHosInfoVoDao.getProvHosInfoByid(id);
	}
	
	@Override
	public int insertProvHosInfo(ProvHosInfoVo provHosInfoVo) throws Exception {
		
		String id = atomUtil.newValue("sys.config");
		provHosInfoVo.setId(id);
		provHosInfoVo.setStatus("1");// 医院待审
		provHosInfoVo.setUid(userOnlineStateUtils.getCurrent().getUserId());
		provHosInfoVo.setProvId(userOnlineStateUtils.getCurrent().getCorpId());
//		simpleDao.insert(basMfrsInfo);
		return provHosInfoVoDao.insertProvHosInfo(provHosInfoVo);
	}
	
	/**
	 * 批量医院供应商关系
	 */
	@Override
	public void insertProvHosInfoBatch(String hosId, List<BasCompanyInfo> companyInfoVos) {
		ProvHosInfoVo provHosInfoVo = null;
		String uid = userOnlineStateUtils.getCurrent().getUserId();
		for(BasCompanyInfo basCompanyInfo:companyInfoVos){
			provHosInfoVo = new ProvHosInfoVo();
			provHosInfoVo.setHosId(hosId);
			provHosInfoVo.setProvId(basCompanyInfo.getId());
			provHosInfoVo.setIsHosView("0");
			provHosInfoVo.setCollectId(uid);
			String id = atomUtil.newValue("sys.config");
			provHosInfoVo.setId(id);
			provHosInfoVo.setStatus("1");// 医院待审
			provHosInfoVo.setUid(uid);
			provHosInfoVoDao.insertProvHosInfo(provHosInfoVo);
		}
	}	
	
	@Override
	public int setStatus(ProvHosInfoVo provHosInfoVo) throws Exception {
		provHosInfoVo.setUid(userOnlineStateUtils.getCurrent().getUserId());
		return provHosInfoVoDao.setStatus(provHosInfoVo);
	}
	
	@Override
	public int setHosView(ProvHosInfoVo provHosInfoVo) throws Exception {
		provHosInfoVo.setUid(userOnlineStateUtils.getCurrent().getUserId());
		return provHosInfoVoDao.setHosView(provHosInfoVo);
	}
	
	@Override
	public int setProvHosInfo(ProvHosInfoVo provHosInfoVo) throws Exception {
		provHosInfoVo.setUid(userOnlineStateUtils.getCurrent().getUserId());
		return provHosInfoVoDao.setProvHosInfo(provHosInfoVo);
	}
	
	@Override
	public int deleteByHosId(String id) throws Exception {
		return provHosInfoVoDao.deleteByHosId(id);
	}
	
	@Override
	public boolean existProvHosInfo(ProvHosInfoVo provHosInfoVo) throws Exception {
		return provHosInfoVoDao.existProvHosInfo(provHosInfoVo);
	}

	@Override
	public void deleteHosProvBatch(List<ProvHosInfoVo> provHosInfoVos) throws Exception {
		for(ProvHosInfoVo provHosInfoVo:provHosInfoVos){
			this.provHosInfoVoDao.deleteById(provHosInfoVo.getId());
		}
	}

	@Override
	public QueryResult<BasCompanyInfo> getNotRelatedCompanysByHosId(QueryInfo<HosInfoVo> queryInfo) {
		QueryResult<BasCompanyInfo> re = new  QueryResult<BasCompanyInfo>();
		PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
		List<BasCompanyInfo> dataList = provHosInfoVoDao.getNotRelatedCompanysByHosId(queryInfo.getQueryObject());
		PageInfo<BasCompanyInfo> sysConfigPage = new PageInfo<BasCompanyInfo>(dataList);
		re.setData(sysConfigPage.getList());
		re.setPageNum(sysConfigPage.getPageNum());
		re.setPageSize(sysConfigPage.getPageSize());
		re.setTotal(sysConfigPage.getTotal());
	   	return re;
	}

}
