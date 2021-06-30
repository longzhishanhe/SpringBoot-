package com.school.market.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.school.market.domain.ShopCar;
import com.school.market.domain.UserGoods;

@Mapper
public interface ShopCarDao {
	@Select("select * from shop_car where userId=#{userId}")
	public List<ShopCar> find(int userId);
	
	@Insert("insert shop_car(userId,commodityId) values (#{userId},#{commodityId})")
	public int addShopCar(int userId,int commodityId);
	
	
	@Delete("delete from shop_car where userId=#{userId}")
	public int removeShopCar(int userId);
	
	@Select("select `commodity`.id,name,price,type,`commodity`.shopId,damage_level,`commodity`.status,number,total,`shop`.username,shop_car.id shopCarId from commodity,shop_car,shop where commodity.id=shop_car.commodityId and shop.id=commodity.shopId and shop_car.userId = #{userId}")
	public List<UserGoods> userGoods(int userId);
	
	@Delete("delete from shop_car where id=#{shopCarId}")
	public int removeOneShopCar(int shopCarId);
	
	@Select("select `commodity`.id,name,price,type,`commodity`.shopId,damage_level,`commodity`.status,number,total,`shop`.username,shop_car.id shopCarId from commodity,shop_car,shop where commodity.id=shop_car.commodityId and shop.id=commodity.shopId and shop_car.id = #{id}")
	public UserGoods findIdGoods(int id);
}
