<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${namespace}">
	<resultMap type="${className}" id="${resultMapId}" extends="${extendsName}">
		<#list localFields as field>
		<result column="${field}" property="${field}"/>
		</#list>
	</resultMap>

	<sql id="tableName">
		${tableName}
	</sql>
	<sql id="tableAlias">
		${className}
	</sql>

	<select id="find" resultMap="${resultMapId}">
		select * from 
		<include refid="tableName"/> <include refid="tableAlias" />
		<trim prefix="where">
			<include refid="common.aliasPropertiesCondition"/>
		</trim>
	</select>
	
	<select id="findById" resultMap="${resultMapId}">
		select * from 
		<include refid="tableName"/>
		where id = ${r"#{"}id}
	</select>
	
	<select id="list" resultMap="${resultMapId}">
		select * from
		<include refid="tableName" /> <include refid="tableAlias" />
		<trim prefix="where">
			<include refid="common.aliasPropertiesCondition" />
		</trim>
		<include refid="common.sortCondition" />
		<include refid="common.pageCondition" />
	</select>

	<insert id="add" parameterType="${className}" useGeneratedKeys="true" keyColumn="id" keyProperty="id">
		insert into
		<include refid="tableName" />
		(
			 id
			<#list fields as field> 
			<#if field != 'id'>
			,${field}
			</#if>
			</#list>
		)
		values
		(
			 ${r"#{"}id}
			<#list fields as field> 
			<#if field != 'id'>
			,${r"#{"}${field}}
			</#if>
			</#list>
		)
	</insert>
	
	<update id="update" parameterType="${className}">
		update <include refid="tableName" /> 
		<trim prefix="set" suffixOverrides=",">
			<#list fields as field><#if field != 'id'>
			<if test="${field} != null">${field}=${r"#{"}${field}},</if>
			</#if></#list>
		</trim>
		where id=${r"#{"}id}
	</update>
</mapper>