package gyqx.platform.base.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.base.PinYin;
import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.exception.ValidateException;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import common.utils.UserOnlineStateUtils;
import common.web.UserOnlineInfo;
import gyqx.mdqc.po.CollectorProvCert;
import gyqx.platform.base.dao.BasCompanyForJxsqsDao;
import gyqx.platform.base.service.IBasCompanyService;
import gyqx.platform.base.vo.BasCompanyInfoVo;
import gyqx.platform.base.vo.CollectorProvCertificateVo;
import gyqx.platform.base.vo.ProvVo;
import gyqx.spdhdi.po.BasCompanyCertificate;
import gyqx.spdhdi.po.BasCompanyCertificateImage;
import gyqx.spdhdi.po.BasCompanyInfo;
import gyqx.spdhdi.po.CollectorProvCertificate;
import gyqx.spdhdi.po.ProvCertificateImage;
import gyqx.spdhdi.po.ProvMfrsCertificate;
import gyqx.spdhdi.po.ProvMfrsCertificateImage;
import gyqx.spdhdi.po.ProvMfrsInfo;
import gyqx.spdherp.po.HosCollectorProv;
@Service
@Slf4j
public class BasCompanyService implements IBasCompanyService {
	@Resource
	private SimpleDao simpleDao;
	@Resource
	private BasCompanyForJxsqsDao basCompanyDao;
	@Resource
	private SysConfigUtil configUtil;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private UserOnlineStateUtils userOnlineStateUtils;

	@Override
	public void saveCompanyCertsInfo(Map map) throws Exception {
		String basePath = configUtil.getValue("upload.basepath");
        if (basePath.indexOf(";") > 0) {
            String[] ss = basePath.split(";");
            if (System.getProperty("os.name").toLowerCase().indexOf("window") >= 0) {
                basePath = ss[0];
            } else {
                basePath = ss[1];
            }
        }
		String localFilePath = basePath + "/" + map.get("path")+"/"+map.get("id") + "." + map.get("ext");
		try {
			this.read2007ExcelProv(new File(localFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 读取供应商模板  Office 2007 excel
	 */
	private void read2007ExcelProv (File file) throws Exception {
		UserOnlineInfo user = userOnlineStateUtils.getCurrent();
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(1);// 供应商信息sheet
//		XSSFSheet sheet = xwb.getSheet("供应商基本信息");// 供应商信息sheet
		String value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		int counter = 0;
		int beginLine = 4;
		String mfrsType = null;// mfrsType，厂商类型：1-境内，2-境外，0-代表供应商
		String kind = null;// 公司类型，2-供应商；3-厂商
		for (int i = beginLine; counter + beginLine < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			} else {
				counter++;
			}
			mfrsType = row.getCell(28).getStringCellValue().trim();
			kind = mfrsType.equals("0")?"2":"3";// 机构类型，2-供应商；3-厂商
			cell = row.getCell(3);
			if(cell==null||cell.toString()=="") {
				continue;
			}
			value = cell.getStringCellValue();
			value = value.trim();
			// 将客户名称中的单引号替换成双单引号，以处理SQL条件赋值问题
			value = value.replaceFirst("'", "''");
			log.info(i + "行" + 1 + " 列 is :" + value);
			if (value == null || "".equals(value)) {
				break;
			}
			String sql = "select * from bas_company_info where kind!=? and cname=?";
			List<BasCompanyInfo> basCompanyInfos = simpleDao.queryForList(sql, BasCompanyInfo.class,mfrsType,value);
			BasCompanyInfo basCompanyInfo = null;
			if(basCompanyInfos!=null && basCompanyInfos.size()>0) {// 如果存在该供应商/厂商信息，则更新信息
				basCompanyInfo = basCompanyInfos.get(0);
				basCompanyInfo.setAnotherName(row.getCell(4)==null?"":row.getCell(4).toString().trim());
				basCompanyInfo.setAddress(row.getCell(11)==null?"":row.getCell(11).toString().trim());
				basCompanyInfo.setArea(row.getCell(26)==null?"":row.getCell(26).toString().trim());
				basCompanyInfo.setAreaCode(row.getCell(27)==null?"":row.getCell(27).toString().trim());
				basCompanyInfo.setCity(row.getCell(25)==null?"":row.getCell(25).toString().trim());
				basCompanyInfo.setContactFax(row.getCell(14)==null?"":row.getCell(14).toString().trim());
				basCompanyInfo.setContactWay(row.getCell(13)==null?"":row.getCell(13).toString().trim());
				basCompanyInfo.setEmail(row.getCell(17)==null?"":row.getCell(17).toString().trim());
				basCompanyInfo.setFlag("1");
//				basCompanyInfo.setIsSupply(row.getCell(21)==null?"":row.getCell(21).toString());
				basCompanyInfo.setIsThreeInOne(row.getCell(21)==null?"":row.getCell(21).toString().trim());
				basCompanyInfo.setJyxkCode(row.getCell(7)==null?"":row.getCell(7).toString().trim());
				basCompanyInfo.setLegal(row.getCell(10)==null?"":row.getCell(10).toString().trim()); 
				basCompanyInfo.setLinkman(row.getCell(12)==null?"":row.getCell(12).toString().trim());
				basCompanyInfo.setNsrsbCode(row.getCell(8)==null?"":row.getCell(8).toString().trim());
				basCompanyInfo.setPostCode(row.getCell(16)==null?"":row.getCell(16).toString().trim());
				basCompanyInfo.setErpCode(row.getCell(19)==null?"":row.getCell(19).toString().trim());
				basCompanyInfo.setProvince(row.getCell(24)==null?"":row.getCell(24).toString().trim());
				basCompanyInfo.setRegCode(row.getCell(6)==null?"":row.getCell(6).toString().trim());// 营业执照注册号
				basCompanyInfo.setRemark(row.getCell(18)==null?"":row.getCell(18).toString().trim());
				basCompanyInfo.setLastUpdateDatetime(new Date());
				
				basCompanyInfo.setMfrsKind(mfrsType=="0"?null:mfrsType);// 厂商类型
				basCompanyInfo.setProductCode(row.getCell(29)==null?"":row.getCell(29).toString().trim());// 生产许可证号
				basCompanyInfo.setRecordCard(row.getCell(30)==null?"":row.getCell(30).toString().trim());// 生产备案证号
				basCompanyInfo.setAgentName(row.getCell(31)==null?"":row.getCell(31).toString().trim());// 全国总代理名称
				try {
					simpleDao.updateNotNullFields(basCompanyInfo);
//					simpleDao.update(basCompanyInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {// 如果不存在该供应商/厂商，则添加，
				basCompanyInfo = new BasCompanyInfo();
				String id = atomUtil.newValue(kind=="3"?"mfrs_info_id":"p_company_id");
				basCompanyInfo.setId(id);
				basCompanyInfo.setCode(id);
				basCompanyInfo.setCname(value);
				basCompanyInfo.setAnotherName(row.getCell(4)==null?"":row.getCell(4).toString().trim());
				basCompanyInfo.setShortPinyin(PinYin.getShortPinyin(value));
				basCompanyInfo.setAnotherName(row.getCell(4)==null?"":row.getCell(4).toString().trim());
				basCompanyInfo.setAddress(row.getCell(11)==null?"":row.getCell(11).toString().trim());
				basCompanyInfo.setArea(row.getCell(26)==null?"":row.getCell(26).toString().trim());
				basCompanyInfo.setAreaCode(row.getCell(27)==null?"":row.getCell(27).toString().trim());
				basCompanyInfo.setCity(row.getCell(25)==null?"":row.getCell(25).toString().trim());
				basCompanyInfo.setContactFax(row.getCell(14)==null?"":row.getCell(14).toString().trim());
				basCompanyInfo.setContactWay(row.getCell(13)==null?"":row.getCell(13).toString().trim());
				basCompanyInfo.setEmail(row.getCell(17)==null?"":row.getCell(17).toString().trim());
				basCompanyInfo.setFlag("1");
//				basCompanyInfo.setIsSupply(row.getCell(21)==null?"":row.getCell(21).toString().trim());
				basCompanyInfo.setIsThreeInOne(row.getCell(21)==null?"":row.getCell(21).toString().trim());
				basCompanyInfo.setJyxkCode(row.getCell(7)==null?"":row.getCell(7).toString().trim());
				basCompanyInfo.setLegal(row.getCell(10)==null?"":row.getCell(10).toString().trim()); 
				basCompanyInfo.setLinkman(row.getCell(12)==null?"":row.getCell(12).toString().trim());
				basCompanyInfo.setNsrsbCode(row.getCell(8)==null?"":row.getCell(8).toString().trim());
				basCompanyInfo.setPostCode(row.getCell(16)==null?"":row.getCell(16).toString().trim());
				basCompanyInfo.setErpCode(row.getCell(19)==null?"":row.getCell(19).toString().trim());
				basCompanyInfo.setProvince(row.getCell(24)==null?"":row.getCell(24).toString().trim());
				basCompanyInfo.setRegCode(row.getCell(6)==null?"":row.getCell(6).toString().trim());// 营业执照注册号
				basCompanyInfo.setRemark(row.getCell(18)==null?"":row.getCell(18).toString().trim());
				
				basCompanyInfo.setMfrsKind(mfrsType=="0"?null:mfrsType);// 厂商类型
				basCompanyInfo.setProductCode(row.getCell(29)==null?"":row.getCell(29).toString().trim());// 生产许可证号
				basCompanyInfo.setRecordCard(row.getCell(30)==null?"":row.getCell(30).toString().trim());// 生产备案证号
				basCompanyInfo.setAgentName(row.getCell(31)==null?"":row.getCell(31).toString().trim());// 全国总代理名称
				
				basCompanyInfo.setKind(kind);
				basCompanyInfo.setFillDate(new Date());
				basCompanyInfo.setLastUpdateDatetime(new Date());
				try {
					simpleDao.insertAndGet(basCompanyInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// 处理"我的供应商"或"我的厂商"
				String collectorId = user.getCorpId();// 获取集配商ID
				String provId = basCompanyInfo.getId();// // 获取供应商ID
				if(kind=="2") {// 处理供应商
					sql = "select * from collector_prov_cert where collector_id = ? and prov_id = ?";
					List<CollectorProvCert> collectorProvCerts = simpleDao.queryForList(sql, CollectorProvCert.class,collectorId,provId);
					if(collectorProvCerts==null || collectorProvCerts.size()==0) {// 建立集配商与供应商关系
						CollectorProvCert bean = new CollectorProvCert();
						bean.setId(atomUtil.newValue("collector_prov_cert_id"));
						bean.setCollectorId(collectorId);
						bean.setProvId(provId);
						bean.setFlag("1");
						bean.setFillDate(new Date());
						bean.setLastUpdateDatetime(new Date());
						bean.setUxid(user.getUserId());
						bean = simpleDao.insertAndGet(bean);
					}
				}else {// 处理厂商
					sql = "select * from prov_mfrs_info where prov_id = ? and mfrs_id = ?";
					List<ProvMfrsInfo> provMfrsInfos = simpleDao.queryForList(sql, ProvMfrsInfo.class,collectorId,provId);
					if(provMfrsInfos==null || provMfrsInfos.size()==0) {// 建立集配商与供应商关系
						ProvMfrsInfo bean = new ProvMfrsInfo();
						bean.setId(UUID.randomUUID().toString().replace("-", ""));
						bean.setProvId(collectorId);
						bean.setMfrsId(provId);
						bean.setFlag("1");
						bean.setFillDate(new Date());
						bean.setLastUpdateDatetime(new Date());
						bean.setUxid(user.getUserId());
						bean = simpleDao.insertAndGet(bean);
					}
				}
			}
		}
		
		counter = 0;
		sheet = xwb.getSheetAt(2);// 证照sheet
		for (int i = beginLine; counter + beginLine < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			} else {
				counter++;
			}
			mfrsType = row.getCell(6).getStringCellValue().trim();
			kind = mfrsType.equals("0")?"2":"3";// 机构类型，2-供应商；3-厂商
			cell = row.getCell(1);
			if(cell==null||cell.toString()=="") {
				continue;
			}
			value = cell.getStringCellValue();
			// 将客户名称中的单引号替换成双单引号，以处理SQL条件赋值问题
			value = value.replaceFirst("'", "''");
			value = value.trim();
			log.info(i + "行" + 1 + " 列 is :" + value);
			if (value == null || "".equals(value)) {
				break;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String beginDate = row.getCell(4)==null?"":row.getCell(4).toString();
			String endDate = row.getCell(5)==null?"":row.getCell(5).toString();
			
			String certKind = row.getCell(2)==null?"":row.getCell(2).toString();
			String certCode = row.getCell(3)==null?"":row.getCell(3).toString();
			certCode = certCode.trim();
			String filePath = row.getCell(8)==null?"":row.getCell(8).toString().trim();
			
			String sql = "select * from bas_company_info where kind=? and cname= ?";
			List<BasCompanyInfo> basCompanyInfos = simpleDao.queryForList(sql, BasCompanyInfo.class,kind,value);
			BasCompanyInfo basCompanyInfo = null;
			if(basCompanyInfos!=null && basCompanyInfos.size()>0) {
				basCompanyInfo = basCompanyInfos.get(0);
			}else {
				continue;
			}
			
			sql = "SELECT * from bas_company_certificate WHERE company_id = ? and cert_code= ?";
			List<BasCompanyCertificate> basCompanyCertificates = simpleDao.queryForList(sql, BasCompanyCertificate.class, 
					basCompanyInfo.getId(),certCode);
			BasCompanyCertificate basCompanyCertificate = null;
			if(basCompanyCertificates==null || basCompanyCertificates.size()==0) {
				basCompanyCertificate = new BasCompanyCertificate();
				basCompanyCertificate.setId(UUID.randomUUID().toString().replace("-", ""));
				basCompanyCertificate.setCertCode(certCode);
				log.info(basCompanyInfo.getCname() + certKind +"类型长度："+certKind.length());
				basCompanyCertificate.setCertKind(certKind);
				basCompanyCertificate.setCompanyId(basCompanyInfo.getId());
				
				basCompanyCertificate.setExpdtBeginDate(sdf.parse(beginDate.replace("/", "-")));
				basCompanyCertificate.setExpdtEndDate(sdf.parse(endDate.replace("/", "-")));
				basCompanyCertificate.setIsValiding("0");
				
				basCompanyCertificate.setFillDate(new Date());
				basCompanyCertificate.setLastUpdateDatetime(new Date());
				basCompanyCertificate.setUxid(user.getUserId());
				basCompanyCertificate = simpleDao.insertAndGet(basCompanyCertificate);
				
				BasCompanyCertificateImage basCompanyCertificateImage = new BasCompanyCertificateImage();
				basCompanyCertificateImage.setId(UUID.randomUUID().toString().replace("-", ""));
				basCompanyCertificateImage.setCertificateId(basCompanyCertificate.getId());
				basCompanyCertificateImage.setFilePath(filePath);
//				basCompanyCertificateImage.setFileName(row.getCell(7)==null?"":row.getCell(7).toString());
				basCompanyCertificateImage.setFillDate(new Date());
				basCompanyCertificateImage.setLastUpdateDatetime(new Date());
				basCompanyCertificateImage.setUxid(user.getUserId());
				simpleDao.insert(basCompanyCertificateImage);
			}else {// 如果证照信息存在，则进一步确认是否要追加附件信息
				basCompanyCertificate = basCompanyCertificates.get(0);
				sql = "SELECT * from bas_company_certificate_image WHERE certificate_id = ? and file_path = ?";
				List<BasCompanyCertificateImage> basCompanyCertificateImages = simpleDao.queryForList(sql, 
						BasCompanyCertificateImage.class,basCompanyCertificate.getId(),filePath);
				if(basCompanyCertificateImages==null||basCompanyCertificateImages.size()==0) {
					BasCompanyCertificateImage basCompanyCertificateImage = new BasCompanyCertificateImage();
					basCompanyCertificateImage.setId(UUID.randomUUID().toString().replace("-", ""));
					basCompanyCertificateImage.setCertificateId(basCompanyCertificate.getId());
					basCompanyCertificateImage.setFilePath(filePath);
//					basCompanyCertificateImage.setFileName(row.getCell(7)==null?"":row.getCell(7).toString());
					basCompanyCertificateImage.setFillDate(new Date());
					basCompanyCertificateImage.setLastUpdateDatetime(new Date());
					simpleDao.insert(basCompanyCertificateImage);
				}
			}
			
			String collectorId = user.getCorpId();// 获取集配商ID
			String provId = basCompanyInfo.getId(); // 获取供应商ID
			if(kind=="2") {// 我的供应商
				sql = "select * from collector_prov_cert where collector_id = ? and prov_id = ?";
				List<CollectorProvCert> collectorProvCerts = simpleDao.queryForList(sql, CollectorProvCert.class,collectorId,provId);
				CollectorProvCert collectorProvCert = null;
				if(collectorProvCerts!=null && collectorProvCerts.size()>0) {// 已建立集配商与供应商关系
					collectorProvCert = collectorProvCerts.get(0);
					sql = "select * from collector_prov_certificate where p_id = ? and cert_kind = ? and cert_code = ?";
					List<CollectorProvCertificate> collectorProvCertificates = simpleDao.queryForList(sql, CollectorProvCertificate.class, 
							collectorProvCert.getId(),certKind,certCode);
					CollectorProvCertificate collectorProvCertificate = null;
					if(collectorProvCertificates==null || collectorProvCertificates.size()==0){// "我的供应商"查无该证照信息，则添加
						collectorProvCertificate = new CollectorProvCertificate();
						collectorProvCertificate.setId(UUID.randomUUID().toString().replace("-", ""));
						collectorProvCertificate.setPid(collectorProvCert.getId());
						collectorProvCertificate.setCertCode(certCode);
						collectorProvCertificate.setCertKind(certKind);
						collectorProvCertificate.setExpdtBeginDate(sdf.parse(beginDate.replace("/", "-")));
						collectorProvCertificate.setExpdtEndDate(sdf.parse(endDate.replace("/", "-")));
						collectorProvCertificate.setFillDate(new Date());
						collectorProvCertificate.setLastUpdateDatetime(new Date());
						collectorProvCertificate.setIsValiding("0");
						collectorProvCertificate.setProvId(provId);
						collectorProvCertificate.setUxid(user.getUserId());
						simpleDao.insert(collectorProvCertificate);
						
						ProvCertificateImage provCertificateImage = new ProvCertificateImage();
						provCertificateImage.setId(UUID.randomUUID().toString().replace("-", ""));
						provCertificateImage.setCertificateId(collectorProvCertificate.getId());
						provCertificateImage.setFilePath(filePath);
						provCertificateImage.setFillDate(new Date());
						provCertificateImage.setLastUpdateDatetime(new Date());
						provCertificateImage.setUxid(user.getUserId());
						simpleDao.insert(provCertificateImage);
					}else {// "我的供应商"查出证照信息，则进一步确认是否要追加附件信息
						collectorProvCertificate = collectorProvCertificates.get(0);
						sql = "select * from prov_certificate_image where certificate_id = ? and file_path = ?";
						List<ProvCertificateImage> provCertificateImages = simpleDao.queryForList(sql, ProvCertificateImage.class, 
								collectorProvCertificate.getId(),filePath);
						if(provCertificateImages==null || provCertificateImages.size()==0) {
							ProvCertificateImage provCertificateImage = new ProvCertificateImage();
							provCertificateImage.setId(UUID.randomUUID().toString().replace("-", ""));
							provCertificateImage.setCertificateId(collectorProvCertificate.getId());
							provCertificateImage.setFilePath(filePath);
							provCertificateImage.setFillDate(new Date());
							provCertificateImage.setLastUpdateDatetime(new Date());
							provCertificateImage.setUxid(user.getUserId());
							simpleDao.insert(provCertificateImage);
						}
					}
				}else {
					continue;
				}
			}else {//　我的厂商
				sql = "select * from prov_mfrs_info where prov_id = ? and mfrs_id = ?";
				List<ProvMfrsInfo> provMfrsInfos = simpleDao.queryForList(sql, ProvMfrsInfo.class,collectorId,provId);
				ProvMfrsInfo provMfrsInfo = null;
				if(provMfrsInfos!=null && provMfrsInfos.size()>0) {// 已建立集配商与厂商关系
					provMfrsInfo = provMfrsInfos.get(0);
					sql = "select * from prov_mfrs_certificate where p_id = ? and cert_kind = ? and cert_code = ?";
					List<ProvMfrsCertificate> provMfrsCertificates = simpleDao.queryForList(sql, ProvMfrsCertificate.class, 
							provMfrsInfo.getId(),certKind,certCode);
					ProvMfrsCertificate provMfrsCertificate = null;
					if(provMfrsCertificates==null || provMfrsCertificates.size()==0){// "我的厂商"查无证照信息，则添加
						provMfrsCertificate = new ProvMfrsCertificate();
						provMfrsCertificate.setId(UUID.randomUUID().toString().replace("-", ""));
						provMfrsCertificate.setPId(provMfrsInfo.getId());
						provMfrsCertificate.setCertCode(certCode);
						provMfrsCertificate.setCertKind(certKind);
						provMfrsCertificate.setExpdtBeginDate(sdf.parse(beginDate.replace("/", "-")));
						provMfrsCertificate.setExpdtEndDate(sdf.parse(endDate.replace("/", "-")));
						provMfrsCertificate.setFillDate(new Date());
						provMfrsCertificate.setLastUpdateDatetime(new Date());
						provMfrsCertificate.setIsValiding("0");
						provMfrsCertificate.setMfrsId(provId);
						provMfrsCertificate.setUxid(user.getUserId());
						simpleDao.insert(provMfrsCertificate);
						
						ProvMfrsCertificateImage provMfrsCertificateImage = new ProvMfrsCertificateImage();
						provMfrsCertificateImage.setId(UUID.randomUUID().toString().replace("-", ""));
						provMfrsCertificateImage.setCertificateId(provMfrsCertificate.getId());
						provMfrsCertificateImage.setFilePath(filePath);
						provMfrsCertificateImage.setFillDate(new Date());
						provMfrsCertificateImage.setLastUpdateDatetime(new Date());
						provMfrsCertificateImage.setUxid(user.getUserId());
						simpleDao.insert(provMfrsCertificateImage);
					}else {// "我的供应商"查出证照信息，则进一步确认是否要追加附件信息
						provMfrsCertificate = provMfrsCertificates.get(0);
						sql = "select * from prov_mfrs_certificate_image where certificate_id = ? and file_path = ?";
						List<ProvMfrsCertificateImage> provMfrsCertificateImages = simpleDao.queryForList(sql, ProvMfrsCertificateImage.class, 
								provMfrsCertificate.getId(),filePath);
						if(provMfrsCertificateImages==null || provMfrsCertificateImages.size()==0) {
							ProvMfrsCertificateImage provMfrsCertificateImage = new ProvMfrsCertificateImage();
							provMfrsCertificateImage.setId(UUID.randomUUID().toString().replace("-", ""));
							provMfrsCertificateImage.setCertificateId(provMfrsCertificate.getId());
							provMfrsCertificateImage.setFilePath(filePath);
							provMfrsCertificateImage.setFillDate(new Date());
							provMfrsCertificateImage.setLastUpdateDatetime(new Date());
							provMfrsCertificateImage.setUxid(user.getUserId());
							simpleDao.insert(provMfrsCertificateImage);
						}
					}
				}else {
					continue;
				}
			}
		}
		
		xwb.close();
	}
	
	@Override
	public BasCompanyInfo addBasCompany(BasCompanyInfo com) throws Exception {
	   long num = simpleDao.queryForObject("select count(*) from bas_company_info where cname= ? ", Long.class, com.getCname());
	   if(num>0){
		   ValidateException.Throw("cname", "【"+com.getCname() +"】已存在！",com.getCname());
	   }
	   String id = null;
		switch (com.getKind()) {
			case "1":
				id= atomUtil.newValue("h_company_id");
				break;
			case "2":
				id= atomUtil.newValue("p_company_id");
				break;
			case "3":
				id= atomUtil.newValue("mfrs_info_id");
				break;
		    case "4":
				id= atomUtil.newValue("p_mfrs_company_id");
				break;
			default:
				break;
		}
		com.setCode(id);
		com.setShortPinyin(PinYin.getShortPinyin(com.getCname()));
		com.setId(id);
		com.setFillDate(new Date());
		com.setVersion(0);
		return simpleDao.insertAndGet(com);
	}
	
	@Override
	public BasCompanyInfo insertMyProv(BasCompanyInfo com,String hosId) throws Exception {
		String collectId = userOnlineStateUtils.getCurrent().getCorpId();
		String sql = "SELECT * from bas_company_info WHERE cname = ?";
		List<BasCompanyInfo> list = new ArrayList<BasCompanyInfo>();
		list = simpleDao.queryForList(sql, BasCompanyInfo.class, com.getCname());
		if(list.size()==0){// bas表中如没有，则添加
			String id = atomUtil.newValue("p_company_id");
			com.setCode(id);
			com.setShortPinyin(PinYin.getShortPinyin(com.getCname()));
			com.setId(id);
			com.setFillDate(new Date());
			com.setVersion(0);
			simpleDao.insertAndGet(com);
		}else {
			com = list.get(0);
		}
		
		sql = "SELECT * from hos_collector_prov WHERE collector_id = ? and hos_id = ? and prov_id = ?";
		List<HosCollectorProv> hosCollectorProvs = new ArrayList<HosCollectorProv>();
		hosCollectorProvs = simpleDao.queryForList(sql, HosCollectorProv.class, collectId,hosId,com.getId());
		if(hosCollectorProvs.size()==0){// 集配商表中如没有，则添加
			HosCollectorProv bean = new HosCollectorProv();
			String id = UUID.randomUUID().toString().replace("-", "");
			bean.setId(id);
			bean.setHosId(hosId);
			bean.setCollectorId(collectId);
			bean.setProvId(com.getId());
			bean.setKind(3);
			bean.setIsUseSubprov(0);
			bean.setLastUpdateDatetime(new Date());
			bean.setVersion(0);
			simpleDao.insertAndGet(bean);
		}else {
			ValidateException.Throw("cname", com.getCname() +  "已存在！",com.getCname() );
		}
		return com;
	}
	

	@Override
	public BasCompanyInfo updateBasCompany(BasCompanyInfo com) throws Exception {
		long num = simpleDao.queryForObject("select count(*) from bas_company_info where code= ?  and id<>?", Long.class, com.getCode(),com.getId());
		if(num>0){
			ValidateException.Throw("code", com.getCode() +  "已存在！",com.getCode());
		}
		num=simpleDao.queryForObject("select count(*) from bas_company_info where  cname=?  and id<>?", 
				Long.class, com.getCname(),com.getId());
		if(num>0){
			ValidateException.Throw("cname", com.getCname() + "已存在！",com.getCname());
		}
		com.setShortPinyin(PinYin.getShortPinyin(com.getCname()));
		com.setLastUpdateDatetime(new java.sql.Date(new Date().getTime()));
		return simpleDao.updateAndGet(com);
	}

	@Override
	public QueryResult<BasCompanyInfo> listBasCompany(QueryInfo<BasCompanyInfo> queryInfo) throws Exception {
		 QueryResult<BasCompanyInfo> re = new  QueryResult<BasCompanyInfo>();
			PageHelper.startPage(queryInfo.getPage(), queryInfo.getRows());
			List<BasCompanyInfo> dataList = basCompanyDao.getBasCompanyInfoList(queryInfo.getQueryObject());
			PageInfo<BasCompanyInfo> sysConfigPage = new PageInfo<BasCompanyInfo>(dataList);
			re.setData(sysConfigPage.getList());
			re.setPageNum(sysConfigPage.getPageNum());
			re.setPageSize(sysConfigPage.getPageSize());
			re.setTotal(sysConfigPage.getTotal());
		   	return re; 
	}
	
	@Override
	public List<CollectorProvCertificateVo> getMyProvCerts(String hcpId,String id) throws Exception {
		String sql = "select id,p_id,prov_id,cert_kind,cert_code,expdt_begin_date,expdt_end_date,is_validing,uxid,"
				+ "fill_date,last_update_datetime,version from collector_prov_certificate WHERE p_id = ? "
				+ "union all "
				+ "select id,id as P_ID,company_id as prov_id,cert_kind,cert_code,expdt_begin_date,expdt_end_date,"
				+ "is_validing,uxid,fill_date,last_update_datetime,version from bas_company_certificate where company_id = ?";
		List<CollectorProvCertificateVo> collectorProvCertificateVos = new ArrayList<>();
		collectorProvCertificateVos = simpleDao.queryForList(sql, CollectorProvCertificateVo.class,hcpId,id);
		if(collectorProvCertificateVos.size()>0) {
			for(CollectorProvCertificateVo c:collectorProvCertificateVos) {
				List<ProvCertificateImage> provCertificateImages = new ArrayList<>();
				sql = "select id,certificate_id,file_path,fill_date,uxid,last_update_datetime,version from "
						+ "bas_company_certificate_image  WHERE certificate_id = ? "
						+ "union all "
						+ "select id,certificate_id,file_path,fill_date,uxid,last_update_datetime,version from "
						+ "prov_certificate_image  WHERE certificate_id = ?";
				provCertificateImages = simpleDao.queryForList(sql, ProvCertificateImage.class, c.getId(),c.getId());
				c.setProvCertificateImage(provCertificateImages);
			}
		}
		return collectorProvCertificateVos;
	}
	
	@Override
	public QueryResult<BasCompanyInfoVo> getMyProvs(QueryInfo<ProvVo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		String sql = "";
		List<BasCompanyInfoVo> basCompanyInfoVos = basCompanyDao.getMyProvs(queryInfo.getQueryObject());
		List<CollectorProvCertificateVo> collectorProvCertificateVos = new ArrayList<>();
		for(BasCompanyInfoVo b:basCompanyInfoVos) {
			sql = "select id,p_id,prov_id,cert_kind,cert_code,expdt_begin_date,expdt_end_date,is_validing,uxid,"
			+ "fill_date,last_update_datetime,version from collector_prov_certificate WHERE p_id = ? "
			+ "union all "
			+ "select id,id as P_ID,company_id as prov_id,cert_kind,cert_code,expdt_begin_date,expdt_end_date,"
			+ "is_validing,uxid,fill_date,last_update_datetime,version from bas_company_certificate where company_id = ?";
			collectorProvCertificateVos = simpleDao.queryForList(sql, CollectorProvCertificateVo.class, b.getHcpId(),b.getId());
			List<ProvCertificateImage> provCertificateImages = new ArrayList<>();
			for(CollectorProvCertificateVo c:collectorProvCertificateVos) {
				sql = "select id,certificate_id,file_path,fill_date,uxid,last_update_datetime,version from "
						+ "bas_company_certificate_image  WHERE certificate_id = ? "
						+ "union all "
						+ "select id,certificate_id,file_path,fill_date,uxid,last_update_datetime,version from "
						+ "prov_certificate_image  WHERE certificate_id = ?";
				
				provCertificateImages = simpleDao.queryForList(sql, ProvCertificateImage.class, c.getId(),c.getId());
				c.setProvCertificateImage(provCertificateImages);
			}
			b.setProvCertificates(collectorProvCertificateVos);
		}
		
		return PageUtils.endPage(basCompanyInfoVos);
	}

	@Override
	public void deleteBasCompany(BasCompanyInfo com) throws Exception {
		//查询该com 下面是否有机构
		long num = simpleDao.queryForObject("select count(*) from sys_org where  corp_id=? ", Long.class,com.getId());
  	  	if(num>0){
	  		throw new Exception(com.getCname()+"有下属机构，不能停用！");
  	  	}
	  	com.setFlag("0");
	  	simpleDao.update(com);
	}
	
	@Override
	public void openCompany(BasCompanyInfo com) throws Exception {
		com.setFlag("1");
		simpleDao.update(com);
	}
	
	public BasCompanyInfo getBasCompany(BasCompanyInfo com) throws Exception{
		com=(BasCompanyInfo)simpleDao.getByFieldName(BasCompanyInfo.class, "id", com.getId());
		return com;
	}

	@Override
	public List<BasCompanyInfo> getListBasCompany(BasCompanyInfo basCompanyInfo) throws Exception {
		List<BasCompanyInfo> dataList = basCompanyDao.getBasCompanyInfoList(basCompanyInfo);
		return dataList;
	}

	@Override
	public void delMyProv(BasCompanyInfoVo companyVo) throws Exception {
		String sql = "DELETE from hos_collector_prov WHERE hos_id = ? and collector_id = ? and prov_id = ?";
		simpleDao.executeSql(sql, companyVo.getHosId(),companyVo.getCollectorId(),companyVo.getId());
	}

}
