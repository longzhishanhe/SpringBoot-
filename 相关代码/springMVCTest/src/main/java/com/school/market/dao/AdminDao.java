package com.school.market.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.school.market.domain.Admin;

@Mapper
public interface AdminDao {
	@Select("select * from `admin` where username = #{username} and password = #{password}")
	public Admin findAdmin(@Param("username") String username,@Param("password") String password);
	
	@Select("select * from admin")
	public List<Admin> findAll();
}
