package gyqx.spdhdi.mycerts.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.db.SimpleDao;
import common.db.query.QueryResult;
import common.exception.ParameterException;
import common.transform.Tx;
import common.utils.DictionaryUtils;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import common.utils.UtilsContext;
import common.web.UserOnlineInfo;
import gyqx.spdhdi.baseData.dao.BasRegInfoDao;
import gyqx.spdhdi.baseData.vo.BasMfrsInfoVo;
import gyqx.spdhdi.mycerts.dao.ProvRegInfoDao;
import gyqx.spdhdi.mycerts.service.ProvRegInfoService;
import gyqx.spdhdi.mycerts.vo.DlgBasRegInfoVo;
import gyqx.spdhdi.mycerts.vo.ProvGoodsInfo4CertVo;
import gyqx.spdhdi.mycerts.vo.ProvRegImageVo;
import gyqx.spdhdi.mycerts.vo.ProvRegInfoVo;
import gyqx.spdhdi.mycerts.vo.ProvRegistrationInfoVo;
import gyqx.spdhdi.po.BasCompanyInfo;
import gyqx.spdhdi.po.BasGoodsInfo;
import gyqx.spdhdi.po.BasRegistGoods;
import gyqx.spdhdi.po.BasRegistrationImage;
import gyqx.spdhdi.po.BasRegistrationInfo;
import gyqx.spdhdi.po.ProvGoodsInfo;
import gyqx.spdhdi.po.ProvRegistGoods;
import gyqx.spdhdi.po.ProvRegistGoodsPush;
import gyqx.spdhdi.po.ProvRegistInfoPush;
import gyqx.spdhdi.po.ProvRegistrationImage;
import gyqx.spdhdi.po.ProvRegistrationInfo;

/**
 * Created by liangwu on 17-6-12.
 */
@Service
@Slf4j
public class ProvRegInfoServiceImpl implements ProvRegInfoService {
    @Resource
    private ProvRegInfoDao provRegInfoDao;
    @Resource
    private BasRegInfoDao basRegInfoDao;
    @Resource
    private SimpleDao simpleDao;
    @Resource
    private UtilsContext utilsContext;
    @Resource
	private SysAtomUtil atomUtil;
	@Resource
	private SysConfigUtil configUtil;

    @Override
	public void importRegCerts(Map map) throws Exception {
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
		String hosId = (String) map.get("hosId");
		try {
			this.read2007ExcelReg(new File(localFilePath),hosId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
	 * 读取厂商模板  Office 2007 excel
	 */
	private void read2007ExcelReg (File file,String hosId) throws Exception {

		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
		String userId = utilsContext.getUserStateUtils().getCurrent().getUserId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(1);// 商品证照信息sheet
		String sql = "";
		String erpCode = "";// erp编码
		String regCode = "";// 注册证号
		String productName = "";// 产品名称
		String filePath = "";//　附件路径
		String mfrsId = "";
		String mfrsName = "";
		String regKind = "0";// 注册证类型，默认为0-注册证
		
		XSSFRow row = null;
		XSSFCell cell = null;
		int counter = 0;
		int beginLine = 4;
		for (int i = beginLine; counter + beginLine < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			} else {
				counter++;
			}
			cell = row.getCell(10);// 注册证号
			if(cell==null||cell.toString()=="") {
				break;
			}
			erpCode = cell.getStringCellValue();
			// 将注册证号中的单引号替换成双单引号，以处理SQL条件赋值问题
			erpCode = erpCode.replace("'", "''");
			erpCode = erpCode.trim();
			sql = "SELECT * from PROV_GOODS_INFO WHERE ERP_CODE = ?";
			List<ProvGoodsInfo> provGoodsList = simpleDao.queryForList(sql, ProvGoodsInfo.class, erpCode);
			List<BasGoodsInfo> basGoodsInfos = null;
			if(provGoodsList!=null && provGoodsList.size()>0) {
				try {
					sql = "SELECT * from BAS_GOODS_INFO WHERE ID = (" +
							"SELECT SPD_GOODS_CODE from PROV_GOODS_INFO WHERE ERP_CODE = ?)";
					basGoodsInfos = simpleDao.queryForList(sql, BasGoodsInfo.class, erpCode);
				} catch (Exception e) {
					sql = "SELECT * from BAS_GOODS_INFO WHERE ID = (" +
							"SELECT SPD_GOODS_CODE from PROV_GOODS_INFO WHERE CODE = ?)";
					basGoodsInfos = simpleDao.queryForList(sql, BasGoodsInfo.class, erpCode);
				}
			}else {// 不存在该条商品，则路过继续下一条
				log.info("第"+i+"行数据异常：");
				log.info("不存在的产品信息："+erpCode);
				continue;
			}
			
			cell = row.getCell(11);// 注册证号
			if(cell==null||cell.toString()=="") {
				break;
			}
			regCode = cell.getStringCellValue();
			// 将注册证号中的单引号替换成双单引号，以处理SQL条件赋值问题
			regCode = regCode.replace("'", "''");
			regCode = regCode.trim();
			
			cell = row.getCell(3);// 产品名称
			if(cell!=null && cell.toString()!="") {
				productName = cell.getStringCellValue();
				// 将产品名称中的单引号替换成双单引号，以处理SQL条件赋值问题
				productName = productName.trim();
			}
			
			cell = row.getCell(12);// 注册证类型
			if(cell!=null && cell.toString()!="") {
				regKind = cell.getStringCellValue();
			}
			
			cell = row.getCell(22);// 获取附件路径
			if(cell==null||cell.toString()=="") {
				filePath = "";
			}else {
				filePath = cell.getStringCellValue();
				// 将注册证号中的单引号替换成双单引号，以处理SQL条件赋值问题
				filePath = filePath.replaceFirst("'", "''");
				filePath = filePath.trim();
			}
			// 获取生产厂商ID
			if(row.getCell(7)!=null && row.getCell(7).toString()!="") {
				mfrsName = row.getCell(7).toString();
				mfrsName = mfrsName.replace("'", "''");
				mfrsName = mfrsName.trim();
				sql = "select * from bas_company_info where (kind = '3' or kind = '4') and cname = ?";
				List<BasCompanyInfo> mfrsList = simpleDao.queryForList(sql, BasCompanyInfo.class, mfrsName);
				if(mfrsList!=null && mfrsList.size()>0) {
					mfrsId = mfrsList.get(0).getId();
				}else {
					log.info("==================异常厂商为："+mfrsName);
				}
			}
			
			/****************************************************************************************************/
			sql = "SELECT * from bas_registration_info WHERE CERTIFICATE_CODE = ?";
			List<BasRegistrationInfo> basRegInfos = simpleDao.queryForList(sql, BasRegistrationInfo.class, regCode);
			BasRegistrationInfo basRegInfo = null;
			if(basRegInfos!=null && basRegInfos.size()>0) {// 如果注册证信息存在 ，则检查附件是否一样，如一样，则不用理会，否则，新增附件表记录
				basRegInfo = basRegInfos.get(0);
				sql = "SELECT * from bas_registration_image WHERE CERTIFICATE_ID =? and FILE_PATH=?";
				List<BasRegistrationImage> basRegImages = simpleDao.queryForList(
						sql, BasRegistrationImage.class, basRegInfo.getId(),filePath);
				if(basRegImages==null ||basRegImages.size()==0) {// 如注册证附件不存在，则追加保存
					BasRegistrationImage basRegImage = new BasRegistrationImage();
					basRegImage.setId(UUID.randomUUID().toString().replace("-", ""));
					basRegImage.setCertificateId(basRegInfo.getId());
					basRegImage.setFilePath(filePath);
					basRegImage.setFillDate(new Date());
					basRegImage.setLastUpdateDatetime(new Date());
					basRegImage.setUxid(userId);
					try {
						simpleDao.insert(basRegImage);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else {// 如果注册证信息不存在，则新增注册证信息及附件信息
				basRegInfo = new BasRegistrationInfo();
				basRegInfo.setId(UUID.randomUUID().toString().replace("-", ""));
				basRegInfo.setCertificateCode(regCode);
				basRegInfo.setExpdtBeginDate(row.getCell(18)==null?null:sdf.parse(row.getCell(18).toString()));
				basRegInfo.setExpdtEndDate(row.getCell(19)==null?null:sdf.parse(row.getCell(19).toString()));
				basRegInfo.setFillDate(new Date());
				basRegInfo.setIsOldInfo("0");// 是否历史证件
				basRegInfo.setLastUpdateDatetime(new Date());
				basRegInfo.setMfrsId(mfrsId);
				basRegInfo.setProductName(productName);
				basRegInfo.setRegKind(regKind);
				basRegInfo.setUxid(userId);
				try {
					simpleDao.insert(basRegInfo);
				} catch (Exception e) {
					log.info("第"+i+"行数据异常：");
					log.info("==================异常注册证号："+regCode);
					log.info("==================异常产品名称："+productName);
					log.info("==================异常厂商为："+mfrsName);
					e.printStackTrace();
				}
				
				BasRegistrationImage basRegImage = new BasRegistrationImage();
				basRegImage.setId(UUID.randomUUID().toString().replace("-", ""));
				basRegImage.setCertificateId(basRegInfo.getId());
				basRegImage.setFilePath(filePath);
				basRegImage.setFillDate(new Date());
				basRegImage.setLastUpdateDatetime(new Date());
				basRegImage.setUxid(userId);
				simpleDao.insert(basRegImage);
			}
			
			if(basGoodsInfos!=null&&basGoodsInfos.size()>0) {
				for(BasGoodsInfo basGoodsInfo:basGoodsInfos) {
					log.info("+++++++++++++++++++++产品erpCode为："+erpCode);
					log.info("+++++++++++++++++++++产品ID为："+basGoodsInfo==null?"":basGoodsInfo.getId());
					log.info("+++++++++++++++++++++产品注册证对象为："+basGoodsInfo+";产品注册证ID为："+basGoodsInfo==null?"":basGoodsInfo.getId());
					sql = "SELECT * from bas_regist_goods WHERE GOODS_ID = ? and CERTIFICATE_ID = ?";
					List<BasRegistGoods> regGoodsList = simpleDao.queryForList(sql, 
							BasRegistGoods.class, basGoodsInfo.getId(),basRegInfo.getId());
					// 如模板某行数据，注册证与产品未建立关系，则建立并保存两者关系
					if(regGoodsList==null || regGoodsList.size()==0) {
						BasRegistGoods regGoods = new BasRegistGoods();
						regGoods.setId(UUID.randomUUID().toString().replace("-", ""));
						regGoods.setCertificateId(basRegInfo.getId());
						regGoods.setGoodsId(basGoodsInfo.getId());
						regGoods.setFillDate(new Date(0));
						regGoods.setLastUpdateDatetime(new Date());
						regGoods.setUxid(userId);
						simpleDao.insert(regGoods);
					}
				}
			}
			
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			sql = "SELECT * from prov_registration_info WHERE PROV_ID = ? and CERTIFICATE_CODE = ?";
			List<ProvRegistrationInfo> provRegInfos = simpleDao.queryForList(sql, ProvRegistrationInfo.class,provId,regCode);
			ProvRegistrationInfo provRegInfo = null;
			if(provRegInfos!=null && provRegInfos.size()>0) {// 如果供应商注册证信息存在 ，则检查附件是否一样，如一样，不用理会；否则，新增附件表记录
				provRegInfo = provRegInfos.get(0);
				sql = "SELECT * from prov_registration_image WHERE PROV_ID = ? and CERTIFICATE_ID = ? and FILE_PATH=?";
				List<ProvRegistrationImage> provRegImages = simpleDao.queryForList(
						sql, ProvRegistrationImage.class, provId,provRegInfo.getId(),filePath);
				if(provRegImages==null ||provRegImages.size()==0) {// 如注册证附件不存在，则追加保存；存在，则不管
					ProvRegistrationImage provRegImage = new ProvRegistrationImage();
					provRegImage.setProvId(provId);
					provRegImage.setId(UUID.randomUUID().toString().replace("-", ""));
					provRegImage.setCertificateId(provRegInfo.getId());
					provRegImage.setFilePath(filePath);
					provRegImage.setFillDate(new Date());
					provRegImage.setLastUpdateDatetime(new Date());
					provRegImage.setUxid(userId);
					simpleDao.insert(provRegImage);
				}
			}else {// 如果供应商注册证信息不存在，则新增注册证信息及附件信息
				provRegInfo = new ProvRegistrationInfo();
				provRegInfo.setId(UUID.randomUUID().toString().replace("-", ""));
				provRegInfo.setProvId(provId);
				provRegInfo.setCertificateCode(regCode);
				provRegInfo.setExpdtBeginDate(row.getCell(18)==null?null:sdf.parse(row.getCell(18).toString()));
				provRegInfo.setExpdtEndDate(row.getCell(19)==null?null:sdf.parse(row.getCell(19).toString()));
				provRegInfo.setFillDate(new Date());
				provRegInfo.setIsOldInfo("0");
				provRegInfo.setLastUpdateDatetime(new Date());
				provRegInfo.setMfrsId(mfrsId);
				provRegInfo.setProductName(productName);
				provRegInfo.setRegKind(regKind);
				provRegInfo.setUxid(userId);
				simpleDao.insert(provRegInfo);
				
				ProvRegistrationImage provRegImage = new ProvRegistrationImage();
				provRegImage.setId(UUID.randomUUID().toString().replace("-", ""));
				provRegImage.setProvId(provId);
				provRegImage.setCertificateId(provRegInfo.getId());
				provRegImage.setFilePath(filePath);
				provRegImage.setFillDate(new Date());
				provRegImage.setLastUpdateDatetime(new Date());
				provRegImage.setUxid(userId);
				simpleDao.insert(provRegImage);
			}
			
			for(ProvGoodsInfo provGoods:provGoodsList) {
				sql = "SELECT * from prov_regist_goods WHERE PROV_ID=? and CERTIFICATE_ID=? and GOODS_ID = ?";
				List<ProvRegistGoods> provRegGoodsList = simpleDao.queryForList(sql, 
						ProvRegistGoods.class, provId,provRegInfo.getId(),provGoods.getId());
				// 如模板某行数据，供应商注册证与产品未建立关系，则建立并保存两者关系
				if(provRegGoodsList==null || provRegGoodsList.size()==0) {
					ProvRegistGoods provRegGoods = new ProvRegistGoods();
					provRegGoods.setId(UUID.randomUUID().toString().replace("-", ""));
					provRegGoods.setProvId(provId);
					provRegGoods.setCertificateId(provRegInfo.getId());
					provRegGoods.setGoodsId(provGoods.getId());
					provRegGoods.setFillDate(new Date(0));
					provRegGoods.setLastUpdateDatetime(new Date());
					provRegGoods.setUxid(userId);
					simpleDao.insert(provRegGoods);
				}
				
				// 推送信息
				sql = "SELECT * from PROV_REGIST_INFO_PUSH WHERE PROV_ID = ? and HOS_ID = ? and CERTIFICATE_CODE = ?";
				List<ProvRegistInfoPush> provRegistInfoPushs = simpleDao.queryForList(sql, 
						ProvRegistInfoPush.class, provId,hosId,regCode);
				ProvRegistInfoPush provRegistInfoPush = null;
				if(provRegistInfoPushs!=null && provRegistInfoPushs.size()>0) {
					provRegistInfoPush = provRegistInfoPushs.get(0);
					sql = "SELECT * from PROV_REGIST_GOODS_PUSH WHERE PROV_ID = ? and hos_id = ? and GOODS_ID = ?";
					List<ProvRegistGoodsPush> provRegistGoodsInfoPushs = simpleDao.queryForList(sql, 
							ProvRegistGoodsPush.class, provId,hosId,provGoods.getId());
					ProvRegistGoodsPush provRegistGoodsPush = null;
					if(provRegistGoodsInfoPushs==null || provRegistGoodsInfoPushs.size()==0) {
						provRegistGoodsPush = new ProvRegistGoodsPush();
						provRegistGoodsPush.setId(UUID.randomUUID().toString().replace("-", ""));
						provRegistGoodsPush.setPushId(provRegistInfoPush.getId());
						provRegistGoodsPush.setCertificateId(provRegInfo.getId());
						provRegistGoodsPush.setGoodsId(provGoods.getId());
						provRegistGoodsPush.setProvId(provId);
						provRegistGoodsPush.setHosId(hosId);
						provRegistGoodsPush.setFillDate(new Date());
						provRegistGoodsPush.setLastUpdateDatetime(new Date());
						provRegistGoodsPush.setUxid(userId);
						simpleDao.insert(provRegistGoodsPush);
					}
				}else {
					provRegistInfoPush = new ProvRegistInfoPush();
					provRegistInfoPush.setAuditDate(new Date());
					provRegistInfoPush.setCertificateCode(regCode);
					provRegistInfoPush.setExpdtBeginDate(row.getCell(18)==null?null:sdf.parse(row.getCell(18).toString()));
					provRegistInfoPush.setExpdtEndDate(row.getCell(19)==null?null:sdf.parse(row.getCell(19).toString()));
					provRegistInfoPush.setFillDate(new Date());
					provRegistInfoPush.setHosId(hosId);
					provRegistInfoPush.setId(UUID.randomUUID().toString().replace("-", ""));
					provRegistInfoPush.setLastUpdateDatetime(new Date());
					provRegistInfoPush.setMfrsId(mfrsId);
					provRegistInfoPush.setProductName(productName);
					provRegistInfoPush.setProvId(provId);
					provRegistInfoPush.setProvRegistId(provRegInfo.getId());
					provRegistInfoPush.setPushStatus("30");//推送状态：已审核
					provRegistInfoPush.setPushUid(userId);
					provRegistInfoPush.setRegKind(regKind);
					simpleDao.insert(provRegistInfoPush);
					
					ProvRegistGoodsPush provRegistGoodsPush = new ProvRegistGoodsPush();
					provRegistGoodsPush.setId(UUID.randomUUID().toString().replace("-", ""));
					provRegistGoodsPush.setPushId(provRegistInfoPush.getId());
					provRegistGoodsPush.setCertificateId(provRegInfo.getId());
					provRegistGoodsPush.setGoodsId(provGoods.getId());
					provRegistGoodsPush.setProvId(provId);
					provRegistGoodsPush.setHosId(hosId);
					provRegistGoodsPush.setFillDate(new Date());
					provRegistGoodsPush.setLastUpdateDatetime(new Date());
					provRegistGoodsPush.setUxid(userId);
					simpleDao.insert(provRegistGoodsPush);
				}
			}
		}
		xwb.close();
	}
    
    
    @Override
    public QueryResult<ProvRegInfoVo> findItemsCerts(String certificateCode, String productName, int pageNum, int pageSize) {
        String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        PageUtils.startPage(pageNum, pageSize);
        QueryResult<ProvRegInfoVo> result = PageUtils.endPage(provRegInfoDao.getItemsCerts(provId, certificateCode, productName));
        return result;
    }

    @Override
    public List<BasMfrsInfoVo> findMfrs(String mfrsName) {
        return basRegInfoDao.getMfrsInfoLikeName(mfrsName, 0, 10);
    }

    @Override
    @Transactional
    public String deleteCert(String id, int version) throws Exception {
        ProvRegistrationInfo regInfo = new ProvRegistrationInfo();
        regInfo.setId(id);
        regInfo.setVersion(version);
        simpleDao.delete(regInfo);
        provRegInfoDao.delAllRelatedGoods(utilsContext.getUserStateUtils().getCurrent().getCorpId(), id);
        return "OK";
    }

    @Override
    public List<DictionaryUtils.DictionaryValue> findDicts() {
        List<DictionaryUtils.DictionaryValue> dicts = utilsContext.getDictUtils().getDictItems("ZCZLX");
        return dicts;
    }

    @Override
    @Transactional
    public String addCert(ProvRegInfoVo regInfo) throws Exception {
        if (compareDate(regInfo.getExpdtBeginDate(), regInfo.getExpdtEndDate()) < 0) {
            throw new ParameterException("有效期开始日期不能大于结束日期！");
        }
        String regId = utilsContext.getSysAtomUtil().newValue("prov_reg_id");
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();

        if (simpleDao.checkExists(ProvRegistrationInfo.class, "provId,certificateCode", user.getCorpId(), regInfo.getCertificateCode()) > 0) {
            throw new ParameterException("已存在此证照编号，请核对。");
        }
        ProvRegistrationInfo provRegistrationInfo = new ProvRegistrationInfo();
        Tx.transform(regInfo).to(provRegistrationInfo);

        provRegistrationInfo.setId(regId);
        provRegistrationInfo.setUid(user.getUserId());
        provRegistrationInfo.setFillDate(new Date());
        provRegistrationInfo.setLastUpdateDatetime(new Date());
        provRegistrationInfo.setIsOldInfo("0");
        provRegistrationInfo.setVersion(0);
        simpleDao.insert(provRegistrationInfo);

        if (regInfo.getAddGoods().size() > 0)
            provRegInfoDao.addGoods(user.getCorpId(), regId, regInfo.getAddGoods(), user.getUserId());
        syncMfrsCert(provRegistrationInfo);
        return regId;
    }

    @Override
    public List<DlgBasRegInfoVo> findBasRegInfo(String mfrsId, String certCode, String regKind) {
        certCode = certCode.replaceAll("%", "\\\\%");
        return provRegInfoDao.getBasRegInfo(mfrsId, certCode, regKind);
    }

    @Override
    @Transactional
    public Integer updateCert(ProvRegInfoVo regInfo) throws Exception {
        if (compareDate(regInfo.getExpdtBeginDate(), regInfo.getExpdtEndDate()) < 0) {
            throw new ParameterException("有效期开始日期不能大于结束日期！");
        }
        String id = regInfo.getId();
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();

        if (!(regInfo.getCertificateCode().equals(regInfo.getOldCertificateCode()))) {
            ProvRegistrationInfo checkRegInfo = new ProvRegistrationInfo();
            checkRegInfo.setProvId(regInfo.getProvId());
            checkRegInfo.setCertificateCode(regInfo.getCertificateCode());
            if (simpleDao.checkExists(ProvRegistrationInfo.class, "provId, certificateCode", user.getCorpId(), checkRegInfo.getCertificateCode()) > 0) {
                throw new ParameterException("证照编号重复，请重新输入！");
            }
        }
        ProvRegistrationInfo registrationInfo = new ProvRegistrationInfo();
        Tx.transform(regInfo).to(registrationInfo);

        String uid = user.getUserId();
        String provId = user.getCorpId();

        registrationInfo.setUid(utilsContext.getUserStateUtils().getCurrent().getUserId());
        registrationInfo.setLastUpdateDatetime(new Date());
        syncMfrsCert(registrationInfo);
        simpleDao.updateNotNullFields(registrationInfo);

        if (regInfo.getAddGoods().size() > 0)
            provRegInfoDao.addGoods(provId, id, regInfo.getAddGoods(), uid);
        if (regInfo.getDelGoods().size() > 0)
            provRegInfoDao.delGoods(provId, id, regInfo.getDelGoods());
        return regInfo.getVersion() + 1;
    }

    @Override
    public List<ProvRegImageVo> findCertImg(String certId) {
        String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        return provRegInfoDao.getCertImg(provId, certId);
    }

    @Override
    public String deleteCertImg(String id, String version) throws Exception {
        int iversion;
        try {
            iversion = Integer.parseInt(version);
        } catch (NumberFormatException e) {
            throw new ParameterException("版本号错误，请刷新后再尝试删除！");
        }
        ProvRegistrationImage provRegistrationImage = new ProvRegistrationImage();
        provRegistrationImage.setId(id);
        provRegistrationImage.setVersion(iversion);
        simpleDao.delete(provRegistrationImage);
        return "OK";
    }

    @Override
    public ProvRegImageVo addProvRegImage(ProvRegistrationImage provRegImage) throws Exception {
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        String provId = user.getCorpId();
        ProvRegImageVo result = new ProvRegImageVo();

        if (simpleDao.checkExists(ProvRegistrationImage.class, "provId,certificateId,filePath", provId,
                provRegImage.getCertificateId(), provRegImage.getFilePath()) > 0) {
            throw new ParameterException("相关产品注册证照片已存在！");
        }

        provRegImage.setId(utilsContext.getSysAtomUtil().newValue("prov_reg_img_id"));
        provRegImage.setProvId(provId);
        provRegImage.setUid(user.getUserId());
        provRegImage.setFillDate(new Date());
        provRegImage.setLastUpdateDatetime(new Date());
        provRegImage.setVersion(0);

        ProvRegistrationImage img = simpleDao.insertAndGet(provRegImage);
        Tx.transform(img).to(result);

        return result;
    }

    @Override
    public List<ProvGoodsInfo4CertVo> findGoodsInfo(String mfrsId) {
        String provId = utilsContext.getUserStateUtils().getCurrent().getCorpId();
        return provRegInfoDao.getGoodsInfo(provId, mfrsId);
    }

    @Override
    public String replaceCert(ProvRegInfoVo regInfo) throws Exception {
        ProvRegistrationInfo newRegInfo = new ProvRegistrationInfo();
        ProvRegistrationInfo oldRegInfo = simpleDao.get(ProvRegistrationInfo.class, regInfo.getId());
        Tx.transform(oldRegInfo).to(newRegInfo);

        newRegInfo.setCertificateCode(regInfo.getCertificateCode());
        newRegInfo.setExpdtBeginDate(regInfo.getExpdtBeginDate());
        newRegInfo.setExpdtEndDate(regInfo.getExpdtEndDate());

        String regId = utilsContext.getSysAtomUtil().newValue("prov_reg_id");
        UserOnlineInfo user = utilsContext.getUserStateUtils().getCurrent();
        newRegInfo.setId(regId);
        newRegInfo.setVersion(0);
        newRegInfo.setUid(user.getUserId());
        newRegInfo.setFillDate(new Date());
        newRegInfo.setLastUpdateDatetime(new Date());

        oldRegInfo.setIsOldInfo("1");
        simpleDao.update(oldRegInfo);
        simpleDao.insert(newRegInfo);

        // 关联旧注册证商品
//        Set<String> goodsIds = new HashSet<>(simpleDao.queryForList("SELECT goods_id FROM prov_regist_goods WHERE prov_id=? AND certificate_id=?", String.class, oldRegInfo.getProvId(), oldRegInfo.getId()));
        Set<String> goodsIds = provRegInfoDao.listProvRegistGoodsId(oldRegInfo.getProvId(), oldRegInfo.getId());
        if(goodsIds.size() > 0){
            provRegInfoDao.addGoods(user.getCorpId(), regId, goodsIds, user.getUserId());
        }
        return "OK";
    }

    private void syncMfrsCert(ProvRegistrationInfo regInfo) throws Exception {
        List<BasRegistrationInfo> list = (List<BasRegistrationInfo>) simpleDao.queryByFieldNames(BasRegistrationInfo.class, "certificateCode", regInfo.getCertificateCode());
        BasRegistrationInfo basRegistrationInfo = new BasRegistrationInfo();
        Tx.transform(regInfo).to(basRegistrationInfo);
        if (list.isEmpty()) {
            basRegistrationInfo.setId(utilsContext.getSysAtomUtil().newValue("bas_reg_info_id"));
            basRegistrationInfo.setUid(utilsContext.getUserStateUtils().getCurrent().getUserId());
            basRegistrationInfo.setFillDate(new Date());
            basRegistrationInfo.setLastUpdateDatetime(new Date());
            basRegistrationInfo.setVersion(0);
            simpleDao.insert(basRegistrationInfo);
        } else if (list.size() > 1) {
            throw new ParameterException("基础数据库存在多条重复数据，请联系管理员进行处理！");
        } else if (list.size() > 0) {
            BasRegistrationInfo oldBasRegInfo = list.get(0);
            basRegistrationInfo.setId(oldBasRegInfo.getId());
            basRegistrationInfo.setVersion(oldBasRegInfo.getVersion());
            basRegistrationInfo.setLastUpdateDatetime(new Date());
            simpleDao.updateNotNullFields(basRegistrationInfo);
        }
    }

    private int compareDate(Date start, Date end) {
        return end.getTime() > start.getTime() ? 1 : end.getTime() == start.getTime() ? 0 : -1;
    }

	@Override
	public List<ProvRegistrationInfoVo> getProvRegsBySpdGoodsCode(String spdGoodsCode) throws Exception {
		String sql = "SELECT pri.*,bmi.MFRS_NAME as cname from PROV_REGISTRATION_INFO pri LEFT JOIN BAS_MFRS_INFO bmi "
				+ "on pri.MFRS_ID = bmi.id "
				+ "WHERE CERTIFICATE_CODE = (SELECT pgi.CERTIFICATE_CODE from PROV_GOODS_INFO pgi WHERE SPD_GOODS_CODE = ?)";
//		String sql = "SELECT pgi.CERTIFICATE_CODE from PROV_GOODS_INFO pgi WHERE SPD_GOODS_CODE = ?";
//		String certificateCode = simpleDao.queryForObject(sql, String.class, spdGoodsCode);
		
//		sql = "SELECT id,prov_id as provId,reg_kind as regKind,product_name as productName,certificate_code as certificateCode"
//				+ " from PROV_REGISTRATION_INFO WHERE CERTIFICATE_CODE = ?";
		
		List<ProvRegistrationInfoVo> result = simpleDao.queryForList(sql, ProvRegistrationInfoVo.class, spdGoodsCode);
		
		for(ProvRegistrationInfoVo p:result) {
			sql = "SELECT * from prov_registration_image WHERE certificate_id = ?";
			List<ProvRegistrationImage> regImages = simpleDao.queryForList(sql, ProvRegistrationImage.class, p.getId());
			p.setProvRegImageVos(regImages);
		}
		return result;
	}
	
//	@Override
//	public List<ProvRegistrationInfoVo> getProvRegsBySpdGoodsCode(String spdGoodsCode) throws Exception {
////		String sql = "SELECT * from PROV_REGISTRATION_INFO WHERE CERTIFICATE_CODE = "
////				+ "(SELECT pgi.CERTIFICATE_CODE from PROV_GOODS_INFO pgi WHERE SPD_GOODS_CODE = ?)";
//		String sql = "SELECT pgi.CERTIFICATE_CODE from PROV_GOODS_INFO pgi WHERE SPD_GOODS_CODE = ?";
//		String certificateCode = simpleDao.queryForObject(sql, String.class, spdGoodsCode);
//		
//		sql = "SELECT id,prov_id as provId,reg_kind as regKind,product_name as productName,certificate_code as certificateCode"
//				+ " from PROV_REGISTRATION_INFO WHERE CERTIFICATE_CODE = ?";
//		
//		List<ProvRegistrationInfoVo> result = simpleDao.queryForList(sql, ProvRegistrationInfoVo.class, certificateCode);
//		
//		for(ProvRegistrationInfoVo p:result) {
//			List<ProvRegImageVo> regImgs = this.findCertImg(p.getId());
//			p.setProvRegImageVos(regImgs);
//		}
//		return result;
//	}

}
