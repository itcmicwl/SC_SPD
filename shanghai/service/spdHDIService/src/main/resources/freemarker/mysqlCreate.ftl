
package ${packageName};

import java.util.*;
import common.db.IBean;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

<#if bean.remark??>
/**
${bean.remark}*/
</#if>
<#list bean.annotations as ann>
@${ann.name}<#if ann.param ??>[${ann.param}]</#if>
</#list>
@Table(name="${bean.tableName}")
public class ${bean.name} implements IBean
{
<#list bean.fields as field>
	<#if field.remark??>
	/**
${field.remark}	*/
	</#if>
	<#list field.annotations as ann>
	@${ann.name}<#if ann.param ??>[${ann.param}]</#if>
	</#list>
	<#if field.pk = true >
	@Id
	</#if>
	<#if (field.fd.length > 0 && field.fd.type != "decimal" ) >
	@Size(max=${field.fd.length})
	</#if>
	@Column(name="${field.fieldName}")
	private ${field.javaType} ${field.name} ;
</#list>

<#list bean.fields as field>
	public ${field.javaType} get${field.camelName} ()
	{
		return ${field.name} ;
	}
	
	public void set${field.camelName} (${field.javaType} ${field.name} )
	{
		this.${field.name} = ${field.name};
	}
</#list>
	@Override
	public String toString() {
		return "${bean.name} [" <#list bean.fields as field>+"<#if field_index != 0 >,</#if> ${field.name}=" +  ${field.name}   </#list>
			 + "]";
	}

}