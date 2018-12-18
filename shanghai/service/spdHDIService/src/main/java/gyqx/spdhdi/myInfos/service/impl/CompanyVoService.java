package gyqx.spdhdi.myInfos.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.db.SimpleDao;
import common.exception.ValidateException;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import common.utils.UserOnlineStateUtils;
import gyqx.spdhdi.myInfos.dao.CompanyInfoVoDao;
import gyqx.spdhdi.myInfos.service.ICompanyVoService;
import gyqx.spdhdi.myInfos.vo.CollectorProvCertificateVo;
import gyqx.spdhdi.myInfos.vo.CompanyCertificateVo;
import gyqx.spdhdi.myInfos.vo.CompanyInfoVo;
import gyqx.spdhdi.po.BasCompanyCertificate;
import gyqx.spdhdi.po.BasCompanyCertificateImage;
import gyqx.spdhdi.po.CollectorProvCertificate;
import gyqx.spdhdi.po.ProvCertificateImage;

@Service
public class CompanyVoService implements ICompanyVoService {
	@Resource
	private SimpleDao simpleDao;
	@Resource
	private CompanyInfoVoDao companyInfoVoDao;
	@Resource
	private SysConfigUtil configUtil;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private UserOnlineStateUtils userOnlineStateUtils;

	@Override
	public CompanyInfoVo getCompanyInfo(String corpId) throws Exception {
		CompanyInfoVo companyInfoVo = companyInfoVoDao.getCompanyInfo(corpId);
		List<CompanyCertificateVo> companyCertificateVos = null;
		companyCertificateVos = companyInfoVoDao.getCompanyCertificateVo(companyInfoVo.getId());
		for (CompanyCertificateVo c : companyCertificateVos) {
			List<BasCompanyCertificateImage> basCompanyCertificateImages = null;
			basCompanyCertificateImages = companyInfoVoDao.getBasCompanyCertificateImage(c.getId());
			c.setBasCompanyCertificateImages(basCompanyCertificateImages);
		}
		companyInfoVo.setCompanyCertificateVos(companyCertificateVos);
		return companyInfoVo;
	}
	
	@Override
	public List<CompanyCertificateVo> getCompanyCertificateVo(String companyId) throws Exception {
		return companyInfoVoDao.getCompanyCertificateVo(companyId);
	}
	
	@Override
	public CompanyInfoVo updateCompanyInfoVo(CompanyInfoVo companyInfoVo) throws Exception {
		companyInfoVo.setUid(userOnlineStateUtils.getCurrent().getUserId());
		companyInfoVo.setLastUpdateDatetime(new Date());
		companyInfoVoDao.updateCompanyInfoVo(companyInfoVo);
		companyInfoVo =  companyInfoVoDao.getCompanyInfo(companyInfoVo.getId());
		return companyInfoVo;
	}

	@Override
	public CompanyInfoVo insertCompanyCertificate(CompanyInfoVo companyInfoVo) throws Exception {
		List<CompanyCertificateVo> certificateVos = companyInfoVo.getCompanyCertificateVos();
		String uid = userOnlineStateUtils.getCurrent().getUserId();
		for(CompanyCertificateVo c : certificateVos){// 前台保证图片信息certificateVos不会为空
			// 清空企业某种证照(比如营业执照)的照片，以便重新添加
			companyInfoVoDao.deleteBasCertificate(c);
			companyInfoVoDao.deleteBasCertificateImg(c);
				String cid = atomUtil.newValue("sys.config");
				c.setId(cid);
				c.setUid(uid);
				c.setCompanyId(companyInfoVo.getId());
				c.setCertCode("");
				companyInfoVoDao.insertCompanyCertificate(c);
				List<BasCompanyCertificateImage> basCompanyCertificateImages = c.getBasCompanyCertificateImages();
				for(BasCompanyCertificateImage b:basCompanyCertificateImages){
					String bid = atomUtil.newValue("sys.config");
					b.setId(bid);
					b.setUid(uid);
					b.setCertificateId(c.getId());
					simpleDao.insert(b);
				}
				c.setBasCompanyCertificateImages(basCompanyCertificateImages);
		}
		companyInfoVo.setCompanyCertificateVos(certificateVos);
		return companyInfoVo;
	}

	@Override
	public CompanyInfoVo updateCompanyCertificate(CompanyInfoVo companyInfoVo) throws Exception {
		return updateCompanyInfoVo(companyInfoVo);
	}
	
	@Override
	public void updateProvCerts(BasCompanyCertificate certBean) throws Exception {
		certBean.setUxid(userOnlineStateUtils.getCurrent().getUserId());
		certBean.setLastUpdateDatetime(new Date());
		simpleDao.update(certBean);
	}
	
	@Override
	public void updateMyProvCerts(CollectorProvCertificate companyCert) throws Exception {
		companyCert.setUxid(userOnlineStateUtils.getCurrent().getUserId());
		companyCert.setLastUpdateDatetime(new Date());
		simpleDao.update(companyCert);
	}
	
	@Override
	public void addProvCerts(CompanyCertificateVo companyCert) throws Exception {
		BasCompanyCertificate certBean = new BasCompanyCertificate();
		certBean.setId(UUID.randomUUID().toString().replace("-", ""));
		certBean.setCompanyId(companyCert.getCompanyId());
		certBean.setCertKind(companyCert.getCertKind());
		certBean.setCertCode(companyCert.getCertCode());
		certBean.setExpdtBeginDate(companyCert.getExpdtBeginDate());
		certBean.setExpdtEndDate(companyCert.getExpdtEndDate());
		certBean.setIsValiding(companyCert.getIsValiding());
		certBean.setUxid(userOnlineStateUtils.getCurrent().getUserId());
		certBean.setFillDate(new Date());
		certBean.setLastUpdateDatetime(new Date());
		certBean = simpleDao.insertAndGet(certBean);
		
		List<BasCompanyCertificateImage> certImages = companyCert.getBasCompanyCertificateImages();
		for(BasCompanyCertificateImage c:certImages) {
			c.setId(UUID.randomUUID().toString().replace("-", ""));
			c.setCertificateId(certBean.getId());
			c.setUxid(userOnlineStateUtils.getCurrent().getUserId());
			c.setFillDate(new Date());
			c.setLastUpdateDatetime(new Date());
			simpleDao.insertAndGet(c);
		}
		
	}
	
	@Override
	public void addMyProvCerts(CollectorProvCertificateVo companyCert) throws Exception {
		CollectorProvCertificate certBean = new CollectorProvCertificate();
		if(companyCert.getCertKind().equals("9")) {// 如果是法人委托书，则往我的供应商证照表里存证照 
			certBean.setId(UUID.randomUUID().toString().replace("-", ""));
			certBean.setPid(companyCert.getPid());
			certBean.setProvId(companyCert.getProvId());
			certBean.setCertKind(companyCert.getCertKind());
			certBean.setCertCode(companyCert.getCertCode());
			certBean.setExpdtBeginDate(companyCert.getExpdtBeginDate());
			certBean.setExpdtEndDate(companyCert.getExpdtEndDate());
			certBean.setIsValiding(companyCert.getIsValiding());
			certBean.setUxid(userOnlineStateUtils.getCurrent().getUserId());
			certBean.setFillDate(new Date());
			certBean.setLastUpdateDatetime(new Date());
			certBean = simpleDao.insertAndGet(certBean);
			
			List<ProvCertificateImage> certImages = companyCert.getProvCertificateImage();
			for(ProvCertificateImage c:certImages) {
				c.setId(UUID.randomUUID().toString().replace("-", ""));
				c.setCertificateId(certBean.getId());
				c.setUxid(userOnlineStateUtils.getCurrent().getUserId());
				c.setFillDate(new Date());
				c.setLastUpdateDatetime(new Date());
				simpleDao.insertAndGet(c);
			}
		}else {// 如果不是法人委托书，则往基础表里存证照 
			BasCompanyCertificate bean = new BasCompanyCertificate();
			bean.setId(UUID.randomUUID().toString().replace("-", ""));
			bean.setCompanyId(companyCert.getProvId());
			bean.setCertKind(companyCert.getCertKind());
			bean.setCertCode(companyCert.getCertCode());
			bean.setExpdtBeginDate(companyCert.getExpdtBeginDate());
			bean.setExpdtEndDate(companyCert.getExpdtEndDate());
			bean.setIsValiding(companyCert.getIsValiding());
			bean.setUxid(userOnlineStateUtils.getCurrent().getUserId());
			bean.setFillDate(new Date());
			bean.setLastUpdateDatetime(new Date());
			bean = simpleDao.insertAndGet(bean);
			
			List<ProvCertificateImage> beanImages = companyCert.getProvCertificateImage();
			for(ProvCertificateImage c:beanImages) {
				BasCompanyCertificateImage beanImage = new BasCompanyCertificateImage();
				beanImage.setId(UUID.randomUUID().toString().replace("-", ""));
				beanImage.setFilePath(c.getFilePath());
				beanImage.setCertificateId(bean.getId());
				beanImage.setUxid(userOnlineStateUtils.getCurrent().getUserId());
				beanImage.setFillDate(new Date());
				beanImage.setLastUpdateDatetime(new Date());
				simpleDao.insertAndGet(beanImage);
			}
		}
	}

	@Override
	public int insertCompanyCertificateImage(BasCompanyCertificateImage certificateImageVo) throws Exception {
		return companyInfoVoDao.insertCompanyCertificateImage(certificateImageVo);
	}

	@Override
	public int updateCompanyCertificateImage(BasCompanyCertificateImage certificateImageVo) throws Exception {
		return companyInfoVoDao.updateCompanyCertificateImage(certificateImageVo);
	}

	public void checkDuplicate(CompanyInfoVo com) throws Exception {
		long num = simpleDao.queryForObject("select count(*) from bas_company_info where code= ?  and id<>?",
				Long.class, com.getCode(), com.getId());
		if (num > 0) {
			ValidateException.Throw("code", com.getCode() + "已存在！", com.getCode());
		}
		num = simpleDao.queryForObject("select count(*) from bas_company_info where cname=?  and id<>?", 
				Long.class, com.getCname(), com.getId());
		if (num > 0) {
			ValidateException.Throw("cname", com.getCname() + "已存在！", com.getCname());
		}
	}

	@Override
	public int updateLogo(CompanyInfoVo company) throws Exception {
		String uid = userOnlineStateUtils.getCurrent().getUserId();
		String id = userOnlineStateUtils.getCurrent().getCorpId();
		company.setUid(uid);
		company.setId(id);
		return companyInfoVoDao.updateLogo(company);
	}

	@Override
	public int deleteImage(String id) throws Exception {
		return companyInfoVoDao.deleteImage(id);
	}
	
	@Override
	public int deleteMyprovImage(String id) throws Exception {
		String sql = "DELETE from prov_certificate_image WHERE id = ?";
		return simpleDao.executeSql(sql,id);
	}
	
	@Override
	public int deleteProvCerts(CompanyCertificateVo companyCert) throws Exception {
		String sql = "DELETE from bas_company_certificate_image WHERE certificate_id = ?";
		int result = simpleDao.executeSql(sql,companyCert.getId());
		if(result>-1) {
			sql = "DELETE from bas_company_certificate WHERE company_id = ? and id = ?";
			result = simpleDao.executeSql(sql, companyCert.getCompanyId(),companyCert.getId());
		}
		return result;
	}
	
	@Override
	public int deleteMyProvCerts(CollectorProvCertificate companyCert) throws Exception {
		String sql = "DELETE from prov_certificate_image WHERE certificate_id = ?";
		int result = simpleDao.executeSql(sql,companyCert.getId());
		if(result>-1) {
			sql = "DELETE from collector_prov_certificate WHERE id = ?";
			result = simpleDao.executeSql(sql,companyCert.getId());
		}
		return result;
	}

	@Override
	public void addImage(BasCompanyCertificateImage c) throws Exception {
		c.setId(UUID.randomUUID().toString().replace("-", ""));
		c.setUid(userOnlineStateUtils.getCurrent().getUserId());
		c.setFillDate(new Date());
		c.setLastUpdateDatetime(new Date());
		simpleDao.insertAndGet(c);
	}
	
	@Override
	public void addMyProvImage(ProvCertificateImage c) throws Exception {
		c.setId(UUID.randomUUID().toString().replace("-", ""));
		c.setUid(userOnlineStateUtils.getCurrent().getUserId());
		c.setFillDate(new Date());
		c.setLastUpdateDatetime(new Date());
		simpleDao.insertAndGet(c);
	}

}
