package gyqx.platform.sys.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import gyqx.platform.po.SysDict;
import gyqx.platform.po.SysDictValue;
import gyqx.platform.sys.vo.SysDictVo;




public interface SysDictMapper {
	
	List query(Map<String,Object> map);
	SysDict insert(SysDict dict);
	SysDict update(SysDict dict);
	void delete(String id);
	
	List queryDictValueVos (Map<String,Object> map);
    SysDictValue updateDictValue(SysDictValue dv);
    SysDictValue insertDictValue(SysDictValue dv);
    void  deleteDictValue(String id);
    
    /**
     * 分页查询字典信息
     * @param vo
     * @return
     */
    List<SysDict> getDictListPage(@Param("queryParam")String queryParam);
	
	
}
