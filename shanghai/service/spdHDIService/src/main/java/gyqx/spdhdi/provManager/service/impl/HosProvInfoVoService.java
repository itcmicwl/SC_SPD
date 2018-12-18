package gyqx.spdhdi.provManager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.UserOnlineStateUtils;
import gyqx.spdhdi.myInfos.vo.HosInfoVo;
import gyqx.spdhdi.myInfos.vo.HosProvVo;
import gyqx.spdhdi.myInfos.vo.ProvHosInfoVo;
import gyqx.spdhdi.provManager.dao.HosProvInfoVoDao;
import gyqx.spdhdi.provManager.service.IHosProvInfoVoService;

@Service	
public class HosProvInfoVoService implements IHosProvInfoVoService {
	@Resource
	HosProvInfoVoDao hosProvInfoVoDao;
	@Resource
	SimpleDao simpleDao;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private UserOnlineStateUtils  userOnlineStateUtils;
	

	@Override
	public QueryResult<ProvHosInfoVo> getHosProvInfoVoList(QueryInfo<ProvHosInfoVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		//设置医院id
		queryInfo.getQueryObject().setHosId(userOnlineStateUtils.getCurrent().getCorpId());
		return PageUtils.endPage(hosProvInfoVoDao.getHosProvInfoVoList(queryInfo.getQueryObject()));
	}
	
	/**
	 * 医院查询平台供应商列表：不包括已建立关系的供应商
	 */
	@Override
	public List<HosProvVo> getHosProvVoList(HosProvVo hosProvVo) throws Exception {
		String sql = "SELECT b.id,b.cname FROM bas_company_info b WHERE b.kind=2 AND NOT EXISTS("
					+ "SELECT * FROM prov_hos_info p WHERE p.hos_id = ? AND "
					+ "(p.`status` =1 or p.`status` = 2 ) AND p.prov_id = b.id"
				+ ")";
		return simpleDao.queryForList(sql, HosProvVo.class, hosProvVo.getHosId());
	}
	
	@Override
	public QueryResult<HosInfoVo> getHosInfoVoList(QueryInfo<HosInfoVo> queryInfo) throws Exception {
		QueryResult<HosInfoVo> lstProvHosInfoVosInfo = new QueryResult<HosInfoVo>();
		PageInfo<HosInfoVo> provHosInfoVoPage = hosProvInfoVoDao.getHosInfoVoList(queryInfo);
		lstProvHosInfoVosInfo.setData(provHosInfoVoPage.getList());
		return lstProvHosInfoVosInfo;
	}
	
	@Override
	public ProvHosInfoVo getProvHosInfoByid(String id) {
		return hosProvInfoVoDao.getProvHosInfoByid(id);
	}
	
	@Override
	public int insertProvHosInfo(ProvHosInfoVo provHosInfoVo) throws Exception {
		String id = atomUtil.newValue("sys.config");
		provHosInfoVo.setId(id);
		provHosInfoVo.setStatus("2");// 医院待审
		provHosInfoVo.setUid(userOnlineStateUtils.getCurrent().getUserId());
//		provHosInfoVo.setProvId(userOnlineStateUtils.getCurrent().getCorpId());
//		simpleDao.insert(basMfrsInfo);
		return hosProvInfoVoDao.insertProvHosInfo(provHosInfoVo);
	}
	
	@Override
	public int setStatus(ProvHosInfoVo provHosInfoVo) throws Exception {
//		return simpleDao.update(basMfrsInfo);
//		provHosInfoVo.setStatus("0");//已解除
		provHosInfoVo.setUid(userOnlineStateUtils.getCurrent().getUserId());
		return hosProvInfoVoDao.setStatus(provHosInfoVo);
	}
	
	@Override
	public int setProvHosInfo(ProvHosInfoVo provHosInfoVo) throws Exception {
//		return simpleDao.update(basMfrsInfo);
		provHosInfoVo.setUid(userOnlineStateUtils.getCurrent().getUserId());
		return hosProvInfoVoDao.setProvHosInfo(provHosInfoVo);
	}
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		return hosProvInfoVoDao.deleteByPrimaryKey(id);
	}
	
	@Override
	public boolean existProvHosInfo(ProvHosInfoVo provHosInfoVo) throws Exception {
		return hosProvInfoVoDao.existProvHosInfo(provHosInfoVo);
	}

	@Override
	public int updateLogo(String logo) throws Exception {
		return hosProvInfoVoDao.updateLogo(logo);
	}

}
