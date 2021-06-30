package com.school.market.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.school.market.domain.Shop;

@Mapper
public interface ShopDao {
	@Select("select * from `shop` where status=#{status}")
	public List<Shop> findShop(int status);
	
	@Update("update `shop` set `level`=#{level} where id=#{id}")
	public int modificationLevel(int id,int level);
	
	@Insert("insert into `shop`(username,password,phone,sex,account) values(#{username},#{password},#{phone},#{sex},#{account})")
	public int addShop(String username,String password,String phone,int sex,String account);
	
	@Update("update `shop` set `status`=#{status} where id=#{id}")
	public int modificationShopStatus(int id,int status);
	
	@Select("select * from `shop` where id = #{id}")
	public Shop findId(int id);
	
	@Select("select * from `shop` where username = #{username} and password = #{password}")
	public Shop find(@Param("username") String username,@Param("password") String password);
	
	@Update("update `shop` set `money`= `money`+#{money} where id=#{id}")
	public int addMoney(int id,int money);
}
