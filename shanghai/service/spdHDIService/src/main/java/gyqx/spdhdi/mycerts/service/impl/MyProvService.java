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
import gyqx.mdqc.mycerts.vo.CollectorProvCertVo;
import gyqx.mdqc.mycerts.vo.MyProvParamVo;
import gyqx.mdqc.po.CollectorProvCert;
import gyqx.spdhdi.myInfos.dao.CompanyInfoVoDao;
import gyqx.spdhdi.myInfos.vo.CollectorProvCertificateVo;
import gyqx.spdhdi.myInfos.vo.CompanyCertificateVo;
import gyqx.spdhdi.mycerts.dao.MyProvDao;
import gyqx.spdhdi.mycerts.service.IMyProvService;
import gyqx.spdhdi.po.BasCompanyCertificateImage;
import gyqx.spdhdi.po.CollectorProvCertificate;
import gyqx.spdhdi.po.ProvCertificateImage;
@Service
public class MyProvService implements IMyProvService {
	@Resource
	private MyProvDao myProvDao;
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
	public QueryResult<CollectorProvCertVo> getMyProvInfo(QueryInfo<MyProvParamVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(myProvDao.getMyProvInfo(queryInfo.getQueryObject()));
	}

	@Override
	public void addMyProvInfo(List<BasCompanyInfoVo> provInfos) throws Exception {
		UserOnlineInfo user = userOnline.getCurrent();
		for(BasCompanyInfoVo b : provInfos) {
			CollectorProvCert collectorProvCert = new CollectorProvCert();
			collectorProvCert.setId(atomUtil.newValue("collector_prov_cert_id"));
			collectorProvCert.setCollectorId(user.getCorpId());
			collectorProvCert.setProvId(b.getId());
			collectorProvCert.setFlag("1");
			collectorProvCert.setFillDate(new Date());
			collectorProvCert.setLastUpdateDatetime(new Date());
			collectorProvCert.setUxid(user.getUserId());
			collectorProvCert.setVersion(0);
			collectorProvCert = simpleDao.insertAndGet(collectorProvCert);
			// 将公司表供应商证照存放到“我的供应商”证照里。
			List<CompanyCertificateVo> companyCertificateVos = new ArrayList<CompanyCertificateVo>();
			companyCertificateVos =	companyInfoVoDao.getCompanyCertificateVo(collectorProvCert.getProvId());
			for (CompanyCertificateVo c : companyCertificateVos) {
				CollectorProvCertificate cert = new CollectorProvCertificate();
				Tx.transform(c).to(cert);
				cert.setId(UUID.randomUUID().toString().replace("-", ""));
				cert.setPid(collectorProvCert.getId());
				cert.setProvId(collectorProvCert.getProvId());
				
				cert.setFillDate(new Date());
				cert.setLastUpdateDatetime(new Date());
				cert.setUxid(user.getUserId());
				cert.setVersion(0);
				cert = simpleDao.insertAndGet(cert);
				
				List<BasCompanyCertificateImage> basCompanyCertificateImages = new ArrayList<BasCompanyCertificateImage>();
				basCompanyCertificateImages = companyInfoVoDao.getBasCompanyCertificateImage(c.getId());
				for(BasCompanyCertificateImage image:basCompanyCertificateImages) {
					ProvCertificateImage pImage = new ProvCertificateImage();
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
	public void updateMyProvFlag(CollectorProvCertVo vo) throws Exception {
		String sql = "update COLLECTOR_PROV_CERT set flag = ? where id = ?";
		simpleDao.executeSql(sql,vo.getFlag(),vo.getId());
	}

	@Override
	public CollectorProvCertificateVo addMyProvCerts(CollectorProvCertificateVo certVo) throws Exception {
		UserOnlineInfo user = userOnline.getCurrent();
		CollectorProvCertificate certBean = new CollectorProvCertificate();
		certBean.setId(UUID.randomUUID().toString().replace("-", ""));
		certBean.setPid(certVo.getPid());
		certBean.setProvId(certVo.getProvId());
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
		
		List<ProvCertificateImage> certImages = certVo.getProvCertificateImage();
		for(ProvCertificateImage c:certImages) {
			c.setId(UUID.randomUUID().toString().replace("-", ""));
			c.setCertificateId(certBean.getId());
			c.setUxid(user.getUserId());
			c.setFillDate(new Date());
			c.setLastUpdateDatetime(new Date());
			c.setVersion(0);
			c = simpleDao.insertAndGet(c);
			certVo.setProvCertificateImage(certImages);
		}
		return certVo;
	}

	@Override
	public void addImage(ProvCertificateImage c) throws Exception {
		c.setId(UUID.randomUUID().toString().replace("-", ""));
		c.setUid(userOnline.getCurrent().getUserId());
		c.setFillDate(new Date());
		c.setLastUpdateDatetime(new Date());
		simpleDao.insertAndGet(c);
		
	}

	@Override
	public void updateProvCerts(CollectorProvCertificate certBean) throws Exception {
		certBean.setUxid(userOnline.getCurrent().getUserId());
		certBean.setLastUpdateDatetime(new Date());
		simpleDao.update(certBean);
		
	}

	@Override
	public int deleteProvCerts(CollectorProvCertificateVo companyCert) throws Exception {
		String sql = "DELETE from prov_certificate_image WHERE certificate_id = ?";
		int result = simpleDao.executeSql(sql,companyCert.getId());
		if(result>-1) {
			sql = "DELETE from collector_prov_certificate WHERE id = ?";
			result = simpleDao.executeSql(sql,companyCert.getId());
		}
		return result;
	}

	@Override
	public int deleteImage(String id) throws Exception {
		String sql = "DELETE from prov_certificate_image WHERE id = ?";
		int result = simpleDao.executeSql(sql,id);
		return result;
	}

}
