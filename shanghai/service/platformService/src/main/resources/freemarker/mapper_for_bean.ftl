package ${groupPackageName}.dao.mapper;

<#list imports as item>
import ${item.packageName}.${item.className};
</#list>

import ${basePackageName}.po.${className};
 
public interface I${className}Mapper {
	int update(${className} bean);
	int insert(${className} bean);
	List<${className}> list(${className} queryInfo) ;
}

