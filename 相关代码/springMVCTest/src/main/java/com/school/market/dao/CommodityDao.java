package com.school.market.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.school.market.domain.Commodity;
import com.school.market.domain.Goods;
@Mapper
public interface CommodityDao {
	@Select("select * from commodity")
	public List<Commodity> findAll();
	
	@Select("select * from commodity,shop where commodity.shopId=shop.id and `commodity`.`status` = 1")
	public List<Goods> findAllGoods();
	
	@Select("select * from commodity,shop_car,shop where commodity.id=shop_car.commodityId and shop.id=commodity.shopId and shop_car.userId = #{userId}")
	public List<Goods> userGoods(int userId);
	
	@Select("select * from commodity,shop where  shop.id=commodity.shopId and shop.id=#{shopId} and `commodity`.`status` = 1")
	public List<Goods> shopGoods(int shopId);
	
	@Select("select * from commodity where id = #{id}")
	public Goods findCommodityId(int id);
	
	@Insert("insert into `commodity`(name,price,type,shopId,number,total,damage_level) values(#{name},#{price},#{type},#{shopId},#{number},#{total},#{damage_level})")
	public int addCommodity(String name,int price,String type, int shopId,int number,int total,int damage_level);
	
	
	//根据id修改商品状态
	@Update("update `commodity` set `status`=#{status} where id=#{id}")
	public int modificationGoodsStatus(int id,int status);
	
	//增减商品数量
	@Update("update `commodity` set `number`=`number`+#{number} where id=#{id}")
	public int regulationGoodsNumber(int id,int number);
}
