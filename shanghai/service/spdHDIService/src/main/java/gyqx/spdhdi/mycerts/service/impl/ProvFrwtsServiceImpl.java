package gyqx.spdhdi.mycerts.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

import common.db.SimpleDao;
import common.db.query.QueryResult;
import common.transform.Tx;
import common.utils.UserOnlineStateUtils;
import common.utils.UtilsContext;
import common.web.UserOnlineInfo;
import gyqx.spdhdi.mycerts.Constants;
import gyqx.spdhdi.mycerts.dao.ProvFrwtsDao;
import gyqx.spdhdi.mycerts.service.ProvFrwtsService;
import gyqx.spdhdi.mycerts.vo.BasCompanyInfoJxsqsVo;
import gyqx.spdhdi.mycerts.vo.ProvFrwtsVo;
import gyqx.spdhdi.mycerts.vo.WtrUserInfoVo;
import gyqx.spdhdi.po.ProvFrwts;

/**
 * Created by liangwu on 17-6-23.
 */
@Service
public class ProvFrwtsServiceImpl implements ProvFrwtsService {
	@Resource
	private ProvFrwtsDao provFrwtsDao;

	@Resource
	private UtilsContext utilsContext;

	@Resource
	private SimpleDao simpleDao;

	@Override
	public List<BasCompanyInfoJxsqsVo> findHosList(String hosName) {
		hosName = hosName.replaceAll("%", "\\\\%");
		return provFrwtsDao.getHosList(hosName);
	}

	@Override
	public List<WtrUserInfoVo> findWtrInfo() {
		String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
		return provFrwtsDao.getWtrInfo(provId);
	}

	@Override
	public String addFrwts(ProvFrwtsVo provFrwtsVo) throws Exception {
		UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
		ProvFrwts provFrwts = new ProvFrwts();
		Tx.transform(provFrwtsVo).to(provFrwts);
		String frwtsId = utilsContext.getSysAtomUtil().newValue("prov_frwts_id");
		provFrwts.setId(frwtsId);
		provFrwts.setWtrMoveNum(provFrwtsVo.getWtrPhone());
		// provFrwts.setProvId(user.getCorpId());
		provFrwts.setUid(user.getUserId());
		provFrwts.setFillDate(new Date());
		provFrwts.setLastUpdateDatetime(new Date());
		provFrwts.setVersion(0);

		simpleDao.insert(provFrwts);
		return frwtsId;
	}

	@Override
	public QueryResult<ProvFrwtsVo> findFrwts(Date start, Date end, String name, String provName, int pageNum,
			int pageSize, String corpType,String corpId) {
		name = name.replaceAll("%", "\\\\%");
		UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
		corpId = user.getCorpId();
		corpType = corpId.startsWith("h") ? "1" : "0";
		// String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
		QueryResult<ProvFrwtsVo> result = new QueryResult<>();
		PageInfo<ProvFrwtsVo> page = new PageInfo<>(
				provFrwtsDao.getFrwts(start, end, name, provName, pageNum, pageSize, corpType,corpId));
		result.setPageNum(page.getPageNum());
		result.setPageSize(page.getPageSize());
		result.setTotal(page.getTotal());
		result.setData(page.getList());
		return result;
	}

	@Override
	public Integer updateFrwts(ProvFrwtsVo provFrwtsVo) throws Exception {
		ProvFrwts provFrwts = new ProvFrwts();

		Tx.transform(provFrwtsVo).to(provFrwts);
		UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
		provFrwts.setWtrMoveNum(provFrwtsVo.getWtrPhone());
		provFrwts.setProvId(user.getCorpId());
		provFrwts.setUid(user.getUserId());
		provFrwts.setLastUpdateDatetime(new Date());

		simpleDao.updateNotNullFields(provFrwts);
		return provFrwts.getVersion() + 1;
	}

	@Override
	public String deleteFrwts(String id, int version) throws Exception {
		ProvFrwts provFrwts = new ProvFrwts();
		provFrwts.setId(id);
		provFrwts.setVersion(version);
		simpleDao.delete(provFrwts);
		return "OK";
	}

	public QueryResult<ProvFrwtsVo> getFrwtsByProv(Date start, Date end, String aboutName, int pageNum, int pageSize) {
		QueryResult<ProvFrwtsVo> result = new QueryResult<>();
		String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
		PageInfo<ProvFrwtsVo> page = new PageInfo<ProvFrwtsVo>(
				provFrwtsDao.getFrwtsByProv(provId, start, end, aboutName, pageNum, pageSize));
		result.setPageNum(page.getPageNum());
		result.setPageSize(page.getPageSize());
		result.setTotal(page.getTotal());
		result.setData(page.getList());
		return result;
	}

	public QueryResult<ProvFrwtsVo> getFrwtsByHos(Date start, Date end, String aboutName, int pageNum, int pageSize) {
		QueryResult<ProvFrwtsVo> result = new QueryResult<>();
		String hosId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
		PageInfo<ProvFrwtsVo> page = new PageInfo<ProvFrwtsVo>(
				provFrwtsDao.getFrwtsByHos(hosId, start, end, aboutName, pageNum, pageSize));
		result.setPageNum(page.getPageNum());
		result.setPageSize(page.getPageSize());
		result.setTotal(page.getTotal());
		result.setData(page.getList());
		return result;
	}

	/**
	 * 根据登录用户企业类型，获取相关的法人委托书
	 * 
	 * @param compKind
	 * @param start
	 * @param end
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public QueryResult<ProvFrwtsVo> getFrwtsListByLoginComp(String compKind, Date start, Date end, String aboutName,
			int pageNum, int pageSize) {
		if (Constants.COMP_KIND_HOS.equals(compKind)) {
			return getFrwtsByHos(start, end, aboutName, pageNum, pageSize);
		} else if (Constants.COMP_KIND_PROV.equals(compKind) || Constants.COMP_KIND_PROV_MFRS.equals(compKind)) {
			return getFrwtsByProv(start, end, aboutName, pageNum, pageSize);
		}
		return null;
	}

}
