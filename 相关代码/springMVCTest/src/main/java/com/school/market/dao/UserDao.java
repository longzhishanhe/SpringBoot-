package com.school.market.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.school.market.domain.User;

@Mapper
public interface UserDao {
	@Select("select * from `user` where status=#{status}")
	public List<User> findUser(int status);
	
	@Insert("insert into `user`(username,password,phone,sex,email,city,account) values(#{username},#{password},#{phone},#{sex},#{email},#{city},#{account})")
	public int addUser(String username,String password,String phone,int sex,String email,String city,String account);
	
	@Select("select * from `user` where username = #{username} and password = #{password}")
	public User find(@Param("username") String username,@Param("password") String password);
	
	@Update("update `user` set `status`=#{status} where id=#{id}")
	public int modificationUserStatus(int id,int status);
	
	@Update("update `user` set `money`=#{money} where id=#{id}")
	public int modificationMoney(int id,int money);
	
	@Select("select * from `user` where id = #{id}")
	public User findId(int id);
	
	@Update("update `user` set `integral`=#{integral} where id=#{id}")
	public int modificationIntegral(int id,int integral);
	
	@Update("update `user` set `integral`= `integral`+#{integral} where id=#{id}")
	public int addIntegral(int id,int integral);
}
