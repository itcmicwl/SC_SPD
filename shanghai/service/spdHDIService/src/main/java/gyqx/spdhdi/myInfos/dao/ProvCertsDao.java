package gyqx.spdhdi.myInfos.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import common.db.SimpleDao;
import gyqx.spdhdi.myInfos.dao.mapper.IProvCertsMapper;
import gyqx.spdhdi.myInfos.vo.BasMfrsInfoVo;
import gyqx.spdhdi.myInfos.vo.OutdateCertsImgVo;
import gyqx.spdhdi.myInfos.vo.OutdateCertsVo;
import gyqx.spdhdi.myInfos.vo.ProvFrwtsVoR;
import gyqx.spdhdi.myInfos.vo.ProvJxsqsVo;
import gyqx.spdhdi.myInfos.vo.ProvRegistInfoPushVo;
import gyqx.spdhdi.mycerts.vo.ProvIncorruptAgrmtVo;
import gyqx.spdhdi.mycerts.vo.ProvSalesRepVo;
import gyqx.spdhdi.po.ProvCns;
import gyqx.spdhdi.po.ProvRegistrationImage;

@Repository
public class ProvCertsDao {
	@Resource
	IProvCertsMapper mapper;
	@Resource
	SimpleDao simpleDao;

	public List<OutdateCertsVo> queryProvOutdateCerts(OutdateCertsVo outdateCertsVo) {
		List<OutdateCertsVo> provOutdateCerts = mapper.queryProvOutdateCerts(outdateCertsVo);
		provOutdateCerts = this.setOutDateCertsImgs(provOutdateCerts,outdateCertsVo);
		return provOutdateCerts;
	}

	/**
	 * 为每条过期证件记录增加路径信息
	 * @param provOutdateCerts
	 * @return
	 */
	private List<OutdateCertsVo> setOutDateCertsImgs(List<OutdateCertsVo> provOutdateCerts,OutdateCertsVo outdateCertsVo) {
		String certKind = null;
		String certType = null;
		String sql = null;
		List<OutdateCertsImgVo> resultList = new ArrayList<>();
		for(OutdateCertsVo o:provOutdateCerts) {
			o.setProvId(outdateCertsVo.getProvId());
			o.setHosId(outdateCertsVo.getHosId());
			certKind = o.getCertKind();
			certType = o.getCertType();
			if("001".equals(certKind)) {// 001，供应商资质
				sql = "SELECT file_path from bas_company_certificate_image WHERE certificate_id = ?";
				resultList = simpleDao.queryForList(sql, OutdateCertsImgVo.class, o.getId());
			}else if("002".equals(certKind)) {// 002，厂商资质
				sql = "SELECT file_path from prov_mfrs_certificate_image WHERE certificate_id = ?";
				resultList = simpleDao.queryForList(sql, OutdateCertsImgVo.class, o.getId());
			}else if("003".equals(certKind)) {// 003，商品证照
				sql = "SELECT file_path from prov_registration_image WHERE prov_id = ? and certificate_id = ?";
				resultList = simpleDao.queryForList(sql, OutdateCertsImgVo.class, o.getProvId(),o.getAboutId());
			}else {// 004，其它证照
				if("004".equals(certType)) {// 法人委托书
					sql = "SELECT p.file_path_wts as filePath from  prov_frwts p WHERE p.prov_id = ? AND p.hos_id = ?";
					resultList = simpleDao.queryForList(sql, OutdateCertsImgVo.class, o.getProvId(),o.getHosId());
				}else if("005".equals(certType)) {// 承诺书
					sql = "SELECT p.file_path_wts as filePath FROM prov_cns p WHERE p.prov_id = ? AND p.hos_id = ?";
					resultList = simpleDao.queryForList(sql, OutdateCertsImgVo.class, o.getProvId(),o.getHosId());
				}else if("006".equals(certType)) {// 销售代表
					sql = "SELECT p.file_path as filePath FROM prov_sales_rep p WHERE p.prov_id = ? AND p.hos_id = ?";
					resultList = simpleDao.queryForList(sql, OutdateCertsImgVo.class, o.getProvId(),o.getHosId());
				}else {// 廉政协议书
					sql = "SELECT p.file_path as filePath FROM prov_incorrupt_agreement p WHERE p.prov_id = ? AND p.hos_id = ?";
					resultList = simpleDao.queryForList(sql, OutdateCertsImgVo.class, o.getProvId(),o.getHosId());
				}
			}
			o.setOutdateCertsImg(resultList);
		}
		return provOutdateCerts;
	}
	
	public List<ProvRegistInfoPushVo> getPushedRegCertsList(ProvRegistInfoPushVo provRegistInfoPushVo) {
		List<ProvRegistInfoPushVo> registCerts = mapper.getPushedRegCertsList(provRegistInfoPushVo);
		for(ProvRegistInfoPushVo p:registCerts){
			List<ProvRegistrationImage> registrationImages = this.getPushedRegCertsImgList(p);
			p.setRegistrationImages(registrationImages.size()<1?new ArrayList<ProvRegistrationImage>():registrationImages);
		}
		return registCerts;
	}
	
	public List<ProvRegistrationImage> getPushedRegCertsImgList(ProvRegistInfoPushVo provRegistInfoPushVo){
		return mapper.getPushedRegCertsImgList(provRegistInfoPushVo);
	}
	
	public List<BasMfrsInfoVo> getBasMfrsList(ProvRegistInfoPushVo provRegistInfoPushVo){
		return mapper.getBasMfrsList(provRegistInfoPushVo);
	}
	
	public List<ProvJxsqsVo> jxsqsList(ProvRegistInfoPushVo qbean) throws Exception {
		return mapper.jxsqsList(qbean);
	}
	
	public List<ProvFrwtsVoR> wtsList(ProvRegistInfoPushVo qbean) throws Exception {
		return mapper.wtsList(qbean);
	}
	
	public List<ProvCns> cnsList(ProvRegistInfoPushVo qbean) throws Exception {
		return mapper.cnsList(qbean);
	}
	
	public List<ProvSalesRepVo> salesRepList(ProvRegistInfoPushVo qbean) throws Exception {
		return mapper.salesRepList(qbean);
	}
	
	public List<ProvIncorruptAgrmtVo> listIncorruptAgrmt(ProvRegistInfoPushVo qbean) throws Exception {
		return mapper.listIncorruptAgrmt(qbean);
	}

}
