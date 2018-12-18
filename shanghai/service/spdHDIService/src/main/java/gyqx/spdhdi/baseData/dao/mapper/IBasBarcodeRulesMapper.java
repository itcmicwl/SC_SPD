package gyqx.spdhdi.baseData.dao.mapper;

import java.util.List;

import gyqx.spdhdi.po.BasBarcodeRules;
 
public interface IBasBarcodeRulesMapper {
	int update(BasBarcodeRules bean);
	List<BasBarcodeRules> list(BasBarcodeRules queryInfo) ;
}

