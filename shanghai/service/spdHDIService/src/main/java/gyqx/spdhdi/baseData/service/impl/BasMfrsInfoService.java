package gyqx.spdhdi.baseData.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

import common.db.SimpleDao;
import common.db.query.QueryInfo;
import common.db.query.QueryResult;
import common.utils.PageUtils;
import common.utils.SysAtomUtil;
import common.utils.SysConfigUtil;
import common.utils.UserOnlineStateUtils;
import gyqx.spdhdi.baseData.dao.BasMfrsInfoDao;
import gyqx.spdhdi.baseData.service.IBasMfrsInfoService;
import gyqx.spdhdi.baseData.vo.BasMfrsInfoVo;
import gyqx.spdhdi.po.BasMfrsInfo;
import gyqx.spdhdi.po.ProvMfrsInfo;

@Service
@Slf4j
public class BasMfrsInfoService implements IBasMfrsInfoService {
	@Resource
	BasMfrsInfoDao basMfrsInfoDao;
	@Resource
	SimpleDao simpleDao;
	@Resource
	private SysAtomUtil atomUtil;
	@Resource
	private SysConfigUtil configUtil;
	@Resource
	private UserOnlineStateUtils  userOnlineStateUtils;
	
	@Override
	public void saveMfrsCertsInfo(Map map) throws Exception {
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
			this.read2007ExcelMfrs(new File(localFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 读取厂商模板  Office 2007 excel
	 */
	private void read2007ExcelMfrs (File file) throws Exception {
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		String provId = userOnlineStateUtils.getCurrent().getCorpId();
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(1);// 供应商信息sheet
		String value = null;
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
			cell = row.getCell(2);
			if(cell==null||cell.toString()=="") {
				break;
			}
			value = cell.getStringCellValue();
			// 将厂商名称中的单引号替换成双单引号，以处理SQL条件赋值问题
			value = value.replace("'", "''");
			value = value.trim();
			log.info(i + "行" + 1 + " 列 is :" + value);
			if (value == null || "".equals(value)) {
				break;
			}
			String sql = "SELECT * from bas_mfrs_info WHERE mfrs_name = ?";
			List<BasMfrsInfo> basMfrsInfos = simpleDao.queryForList(sql, BasMfrsInfo.class, value);
			BasMfrsInfo basMfrsInfo = null;
			if(basMfrsInfos!=null && basMfrsInfos.size()>0) {// 如果生产厂商信息存在 ，则更新
				basMfrsInfo = basMfrsInfos.get(0);
				basMfrsInfo.setAgentName(row.getCell(5)==null?"":row.getCell(5).toString());
				basMfrsInfo.setFillDate(new Date());
				basMfrsInfo.setLastUpdateDatetime(new Date());
				basMfrsInfo.setMfrsKind(row.getCell(4)==null?"":row.getCell(4).toString());
				basMfrsInfo.setProductCode(row.getCell(9)==null?"":row.getCell(9).toString());// 生产许可证
				basMfrsInfo.setRecordCard(row.getCell(19)==null?"":row.getCell(19).toString());// 生产备案证
				basMfrsInfo.setIsThreeInOne("1");
				basMfrsInfo.setUxid(userOnlineStateUtils.getCurrent().getUserId());
				try {
					basMfrsInfo = simpleDao.updateAndGet(basMfrsInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {// 如果没有生产厂商信息，则新增
				basMfrsInfo = new BasMfrsInfo();
				basMfrsInfo.setId(UUID.randomUUID().toString().replace("-", ""));
				basMfrsInfo.setAgentName(row.getCell(5)==null?"":row.getCell(5).toString());
				basMfrsInfo.setFillDate(new Date());
				basMfrsInfo.setFlag("1");
				basMfrsInfo.setLastUpdateDatetime(new Date());
				basMfrsInfo.setMfrsName(value);
				basMfrsInfo.setMfrsKind(row.getCell(4)==null?"":row.getCell(4).toString());
				basMfrsInfo.setProductCode(row.getCell(9)==null?"":row.getCell(9).toString());// 生产许可证
				basMfrsInfo.setRecordCard(row.getCell(19)==null?"":row.getCell(19).toString());// 生产备案证
				basMfrsInfo.setIsThreeInOne("1");
				basMfrsInfo.setUxid(userOnlineStateUtils.getCurrent().getUserId());
				this.insertBasMfrsInfo(basMfrsInfo);
			}
			
			sql = "SELECT * from prov_mfrs_info WHERE prov_id = ? and mfrs_id = ?";
			List<ProvMfrsInfo> provMfrsInfos = simpleDao.queryForList(sql, ProvMfrsInfo.class, provId, basMfrsInfo.getId());
			ProvMfrsInfo provMfrsInfo = null;
			// 如果供应商生产厂商信息不存在，则新增；如存在，则不用管，在基本信息里已有，两者关系已维护
			if (provMfrsInfos==null||provMfrsInfos.size()<1) {
	        	provMfrsInfo = new ProvMfrsInfo();
	        	provMfrsInfo.setId(UUID.randomUUID().toString().replace("-", ""));
	        	provMfrsInfo.setFillDate(new Date());
	        	provMfrsInfo.setFlag("1");
	        	provMfrsInfo.setLastUpdateDatetime(new Date());
	        	provMfrsInfo.setMfrsId(basMfrsInfo.getId());
	        	provMfrsInfo.setProvId(provId);
	        	provMfrsInfo.setUxid(userOnlineStateUtils.getCurrent().getUserId());
	        	provMfrsInfo.setVersion(0);
	        	provMfrsInfo = simpleDao.insertAndGet(provMfrsInfo);
	        }
		}
		xwb.close();
	}
	
	@Override
	public QueryResult<BasMfrsInfoVo> getBasMfrsInfoList(QueryInfo<BasMfrsInfo> queryInfo) throws Exception {
		PageUtils.startPage(queryInfo);
		return PageUtils.endPage(basMfrsInfoDao.getBasMfrsInfoList(queryInfo.getQueryObject()));
	}

	@Override
	public BasMfrsInfo getBasMfrsInfoByid(String id) {
		return basMfrsInfoDao.getBasMfrsInfoByid(id);
	}

	@Override
	public int insertBasMfrsInfo(BasMfrsInfo basMfrsInfo) throws Exception {
		String id = atomUtil.newValue("mfrs_info_id");
		basMfrsInfo.setId(id);
		basMfrsInfo.setUid(userOnlineStateUtils.getCurrent().getUserId());
//		simpleDao.insert(basMfrsInfo);
		return basMfrsInfoDao.insertBasMfrsInfo(basMfrsInfo);
	}

//	@Override
//	public int setStatus(BasMfrsInfo basMfrsInfo) throws Exception {
////		return simpleDao.update(basMfrsInfo);
//		return basMfrsInfoDao.setStatus(basMfrsInfo);
//	}
	
	@Override
	public int setStatus(BasMfrsInfo basMfrsInfo) throws Exception {
		String sql = "update bas_company_info set flag = ? where id = ?";
		return simpleDao.executeSql(sql, basMfrsInfo.getFlag(),basMfrsInfo.getId());
	}

	@Override
	public int setBasMfrsInfo(BasMfrsInfo basMfrsInfo) throws Exception {
//		return simpleDao.update(basMfrsInfo);
		basMfrsInfo.setUid(userOnlineStateUtils.getCurrent().getUserId());
		return basMfrsInfoDao.setBasMfrsInfo(basMfrsInfo);
	}

	@Override
	public boolean existBasMfrsInfo(BasMfrsInfo basMfrsInfo) throws Exception {
		return basMfrsInfoDao.existBasMfrsInfo(basMfrsInfo);
	}

	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		return basMfrsInfoDao.deleteByPrimaryKey(id);
	}

}
