package gyqx.spdhdi.mycerts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.transform.Tx;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import common.utils.UserOnlineStateUtils;
import common.web.UserOnlineInfo;
import gyqx.mdqc.custProvManage.vo.BasCompanyInfoVo;
import gyqx.mdqc.mycerts.vo.MyMfrsVo;
import gyqx.mdqc.mycerts.vo.MyProvParamVo;
import gyqx.spdhdi.myInfos.dao.CompanyInfoVoDao;
import gyqx.spdhdi.myInfos.vo.CollectorProvCertificateVo;
import gyqx.spdhdi.myInfos.vo.CompanyCertificateVo;
import gyqx.spdhdi.mycerts.dao.MyMfrsDao;
import gyqx.spdhdi.mycerts.dao.MyProvDao;
import gyqx.spdhdi.mycerts.service.IMyMfrsService;
import gyqx.spdhdi.mycerts.vo.ProvMfrsCertificateImageVo;
import gyqx.spdhdi.mycerts.vo.ProvMfrsCertificateVo;
import gyqx.spdhdi.po.BasCompanyCertificateImage;
import gyqx.spdhdi.po.CollectorProvCertificate;
import gyqx.spdhdi.po.ProvMfrsCertificate;
import gyqx.spdhdi.po.ProvMfrsCertificateImage;
import gyqx.spdhdi.po.ProvMfrsInfo;
@Service
public class MyMfrsService implements IMyMfrsService {
	@Resource
	private MyMfrsDao myMfrsDao;
	@Resource
	private CompanyInfoVoDao companyInfoVoDao;
	@Resource
	private SimpleDao simpleDao;
	@Resource
	private SysConfigUtil configUtil;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private UserOnlineStateUtils userOnline;

	@Override
	public QueryResult<MyMfrsVo> getMyMfrsInfo(QueryInfo<MyProvParamVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(myMfrsDao.getMyMfrsInfo(queryInfo.getQueryObject()));
	}
	
	@Override
	public void addMyMfrsInfo(List<BasCompanyInfoVo> mfrsInfos) throws Exception {
		UserOnlineInfo user = userOnline.getCurrent();
		for(BasCompanyInfoVo b : mfrsInfos) {
			ProvMfrsInfo provMfrsInfo = new ProvMfrsInfo();
			provMfrsInfo.setId(atomUtil.newValue("prov_mfrs_info_id"));
			provMfrsInfo.setProvId(user.getCorpId());
			provMfrsInfo.setMfrsId(b.getId());
			provMfrsInfo.setFlag("1");
			provMfrsInfo.setFillDate(new Date());
			provMfrsInfo.setLastUpdateDatetime(new Date());
			provMfrsInfo.setUxid(user.getUserId());
			provMfrsInfo.setVersion(0);
			provMfrsInfo = simpleDao.insertAndGet(provMfrsInfo);
			// 将公司表供应商证照存放到“我的供应商”证照里。
			List<CompanyCertificateVo> companyCertificateVos = new ArrayList<CompanyCertificateVo>();
			companyCertificateVos =	companyInfoVoDao.getCompanyCertificateVo(provMfrsInfo.getMfrsId());
			for (CompanyCertificateVo c : companyCertificateVos) {
				ProvMfrsCertificate cert = new ProvMfrsCertificate();
				Tx.transform(c).to(cert);
				cert.setId(atomUtil.newValue("prov_mfrs_cert_id"));
				cert.setPId(provMfrsInfo.getId());
				cert.setMfrsId(provMfrsInfo.getMfrsId());
				cert.setFillDate(new Date());
				cert.setLastUpdateDatetime(new Date());
				cert.setUxid(user.getUserId());
				cert.setVersion(0);
				cert = simpleDao.insertAndGet(cert);
				
				List<BasCompanyCertificateImage> basCompanyCertificateImages = new ArrayList<BasCompanyCertificateImage>();
				basCompanyCertificateImages = companyInfoVoDao.getBasCompanyCertificateImage(c.getId());
				for(BasCompanyCertificateImage image:basCompanyCertificateImages) {
					ProvMfrsCertificateImage pImage = new ProvMfrsCertificateImage();
					pImage.setId(UUID.randomUUID().toString().replace("-", ""));
					pImage.setCertificateId(cert.getId());
					pImage.setFilePath(image.getFilePath());
					pImage.setFillDate(new Date());
					pImage.setLastUpdateDatetime(new Date());
					pImage.setUxid(user.getUserId());
					pImage.setVersion(0);
					pImage = simpleDao.insertAndGet(pImage);
				}
			}
		}
	}

	@Override
	public void updateMyMfrsFlag(MyMfrsVo vo) throws Exception {
		String sql = "update prov_mfrs_info set flag = ? where id = ?";
		simpleDao.executeSql(sql,vo.getFlag(),vo.getId());
	}

	@Override
	public ProvMfrsCertificateVo addMyMfrsCerts(ProvMfrsCertificateVo certVo) throws Exception {
		UserOnlineInfo user = userOnline.getCurrent();
		ProvMfrsCertificate certBean = new ProvMfrsCertificate();
		certBean.setId(atomUtil.newValue("prov_mfrs_cert_id"));
		certBean.setPId(certVo.getpId());
		certBean.setMfrsId(certVo.getMfrsId());
		certBean.setCertKind(certVo.getCertKind());
		certBean.setCertCode(certVo.getCertCode());
		certBean.setExpdtBeginDate(certVo.getExpdtBeginDate());
		certBean.setExpdtEndDate(certVo.getExpdtEndDate());
		certBean.setIsValiding(certVo.getIsValiding());
		certBean.setUxid(user.getUserId());
		certBean.setFillDate(new Date());
		certBean.setLastUpdateDatetime(new Date());
		certBean = simpleDao.insertAndGet(certBean);
		Tx.transform(certBean).to(certVo);
		
		List<ProvMfrsCertificateImageVo> certImages = certVo.getImageList();
		for(ProvMfrsCertificateImageVo c:certImages) {
			ProvMfrsCertificateImage pImage = new ProvMfrsCertificateImage();
			pImage.setId(UUID.randomUUID().toString().replace("-", ""));
			pImage.setCertificateId(certBean.getId());
			pImage.setFilePath(c.getFilePath());
			pImage.setFillDate(new Date());
			pImage.setLastUpdateDatetime(new Date());
			pImage.setUxid(user.getUserId());
			pImage.setVersion(0);
			pImage = simpleDao.insertAndGet(pImage);
//			Tx.transform(pImage).to(c);
//			certVo.setImageList(imageList);
		}
		return certVo;
	}

	@Override
	public void addImage(ProvMfrsCertificateImage c) throws Exception {
		c.setId(UUID.randomUUID().toString().replace("-", ""));
		c.setUid(userOnline.getCurrent().getUserId());
		c.setFillDate(new Date());
		c.setLastUpdateDatetime(new Date());
		simpleDao.insertAndGet(c);
		
	}

	@Override
	public void updateMfrsCerts(ProvMfrsCertificateVo certVo) throws Exception {
		ProvMfrsCertificate certBean = new ProvMfrsCertificate();
		Tx.transform(certVo).to(certBean);
		certBean.setFillDate(new Date());
		certBean.setUxid(userOnline.getCurrent().getUserId());
		certBean.setLastUpdateDatetime(new Date());
		simpleDao.updateNotNullFields(certBean);
	}

	@Override
	public int deleteMfrsCerts(ProvMfrsCertificateVo companyCert) throws Exception {
		String sql = "DELETE from prov_mfrs_certificate_image WHERE certificate_id = ?";
		int result = simpleDao.executeSql(sql,companyCert.getId());
		if(result>-1) {
			sql = "DELETE from prov_mfrs_certificate WHERE id = ?";
			result = simpleDao.executeSql(sql,companyCert.getId());
		}
		return result;
	}

	@Override
	public int deleteImage(String id) throws Exception {
		String sql = "DELETE from prov_mfrs_certificate_image WHERE id = ?";
		int result = simpleDao.executeSql(sql,id);
		return result;
	}

	
}
