package gyqx.mdqc.custProvManage.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import gyqx.mdqc.custProvManage.dao.mapper.IProvInfoMapper;
import gyqx.mdqc.custProvManage.vo.BasCompanyInfoVo;

@Repository
public class ProvInfoDao {
	@Resource
	private IProvInfoMapper mapper;
	public List<BasCompanyInfoVo> getProvInfoList(BasCompanyInfoVo queryObject) {
		return mapper.getProvInfoList(queryObject);
	}
}
