package gyqx.spdhdi.baseData.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ParameterException;
import common.transform.Tx;
import common.utils.DictionaryUtils;
import common.utils.PageUtils;
import common.utils.UtilsContext;
import gyqx.spdhdi.baseData.dao.BasRegInfoDao;
import gyqx.spdhdi.baseData.service.BasRegInfoService;
import gyqx.spdhdi.baseData.vo.BasRegInfoVo;
import gyqx.spdhdi.baseData.vo.BasRegistrationInfoVo;
import gyqx.spdhdi.po.BasRegistrationInfo;

/**
 * Created by liangwu on 17-6-6.
 */
// TODO: 修改注释
@Service
public class BasRegInfoServiceImpl implements BasRegInfoService {
    @Resource
    private BasRegInfoDao basRegInfoDao;

    @Resource
    private SimpleDao simpleDao;

    @Resource
    private UtilsContext utilsContext;

    @Override
    public QueryResult<BasRegInfoVo> findCerts(String mfrsID, int pageNum, int pageSize) {
        QueryResult<BasRegInfoVo> result = new QueryResult<>();
        PageInfo<BasRegInfoVo> page = new PageInfo<>(basRegInfoDao.getCerts(mfrsID, pageNum, pageSize));
        result.setTotal(page.getTotal());
        result.setData(page.getList());
        return result;
    }

    @Override
	public QueryResult<BasRegInfoVo> getCerts(QueryInfo<BasRegInfoVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(basRegInfoDao.getCerts(queryInfo.getQueryObject()));
	}
    
    @Override
    public String addCert(BasRegistrationInfoVo basRegInfoVo) throws Exception {
        checkParam(basRegInfoVo);
        BasRegistrationInfo basRegInfo = new BasRegistrationInfo();
        Tx.transform(basRegInfoVo).to(basRegInfo);
        String regInfoId = utilsContext.getSysAtomUtil().newValue("bas_reg_info_id");
        basRegInfo.setId(regInfoId);
        basRegInfo.setIsOldInfo("0");
        basRegInfo.setUid(utilsContext.getUserStateUtils().getCurrent().getUserId());
        basRegInfo.setFillDate(new Date());
        basRegInfo.setLastUpdateDatetime(new Date());
        simpleDao.insert(basRegInfo);
        return regInfoId;
    }

    @Override
    public int updateCert(BasRegistrationInfoVo basRegInfoVo) throws Exception {
/*      // 在可以修改注册证号的情况下，验证修改后是否重复
        if (!(basRegInfoVo.getCertCode().equals(basRegInfoVo.getOldCertCode()))) {
            BasMfrsCertificate check = new BasMfrsCertificate();
            check.setMfrsId(basRegInfoVo.getMfrsId());
            check.setCertCode(basRegInfoVo.getCertCode());
            checkParam(check);
        }*/
        BasRegistrationInfo regRegInfo = new BasRegistrationInfo();
        Tx.transform(basRegInfoVo).to(regRegInfo);
        regRegInfo.setCertificateCode(null);
        regRegInfo.setUid(utilsContext.getUserStateUtils().getCurrent().getUserId());
        regRegInfo.setExpdtBeginDate(new Date());
        regRegInfo.setExpdtEndDate(new Date());
        regRegInfo.setLastUpdateDatetime(new Date());
        simpleDao.updateNotNullFields(regRegInfo);
        return regRegInfo.getVersion() + 1;
    }

    @Override
    public String deleteCert(String mfrsId, Integer version) throws Exception {
        BasRegistrationInfo basRegInfo = new BasRegistrationInfo();
        basRegInfo.setId(mfrsId);
        basRegInfo.setVersion(version);
        simpleDao.delete(basRegInfo);
        return "OK";
    }

    @Override
    public List<DictionaryUtils.DictionaryValue> findDicts() {
        return utilsContext.getDictUtils().getDictItems("ZCZLX");
    }

    private void checkParam(BasRegistrationInfoVo basRegInfo) throws Exception {
        if (simpleDao.checkExists(BasRegistrationInfo.class, "mfrsId,certificateCode", basRegInfo.getMfrsId(), basRegInfo.getCertificateCode()) > 0) {
            throw new ParameterException("证照编号重复，请重新输入！");
        }
    }
}