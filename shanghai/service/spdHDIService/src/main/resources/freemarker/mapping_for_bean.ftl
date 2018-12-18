<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${groupPackageName}.dao.mapper.I${className}Mapper">
    <sql id="baseSelect">
        select <#list tm.fields as field ><#if (field_index >0)>,</#if>${field.name}</#list> from ${tableName}
    </sql>
	<select id="list"  resultMap="${pname}Map" parameterType="${basePackageName}.po.${className}">
    	<include refid="baseSelect"></include>
		<where>
		<#list tm.fields as field >
		<#if field.pk = true >
			and ${field.name} = ${'#{'}${field.lowerCamelName}}
		</#if>
		</#list>
		</where>
	</select>
 	<resultMap type="${basePackageName}.po.${className}" id="${pname}Map">
 		<#list tm.fields as field >
 		<#if  field.pk = true >
		<id property="${field.lowerCamelName}" column="${field.name}"/>
		<#else>
		<result property="${field.lowerCamelName}"  column="${field.name}"/>
		</#if>
		</#list>
	</resultMap> 
	
	<update id="update" parameterType="${basePackageName}.po.${className}" >
		update ${tableName}
		<set>
		<#list tm.fields as field >
			<#if field.name == "version" >
			version=version+1,
			<#elseif field.pk != true >
			<if test='${field.lowerCamelName} != null'>${field.name} = ${'#{'}${field.lowerCamelName}},</if>
			</#if> 
		</#list>	
		</set>
		<where>
		<#list tm.fields as field >
		<#if field.name == "version" >
			and version= ${'#{'}${field.lowerCamelName}}
		<#elseif field.pk = true >
			and ${field.name} = ${'#{'}${field.lowerCamelName}}
		<#else>			
		</#if>
		</#list>
		</where>
	</update>
	
	<insert id="insert" parameterType="${basePackageName}.po.${className}" >
		insert into ${tableName} 
		(<#list tm.fields as field ><#if (field_index >0) >,</#if> ${field.name} </#list> )
		values
		(<#list tm.fields as field ><#if (field_index >0) >,</#if>${'#{'}${field.lowerCamelName}}</#list>)
	</insert>
</mapper>