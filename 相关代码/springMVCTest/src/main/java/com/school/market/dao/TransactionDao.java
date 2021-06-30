package com.school.market.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.school.market.domain.Transaction;

@Mapper
public interface TransactionDao {
	//获取交易表数据
	@Select("select `transaction`.id,`transaction`.commodityId,`transaction`.userId,`transaction`.status,`commodity`.name,price,`type`,`shop`.username shopName,`user`.username username,damage_level from `transaction`,`commodity`,`shop`,`user` where `transaction`.commodityId=`commodity`.id and `commodity`.shopId=`shop`.id and `user`.id=`transaction`.userId and `transaction`.userId=#{userId} and `transaction`.status=#{status}")
	public List<Transaction> userTransaction(int userId,int status);
	
	@Select("select `transaction`.id,`transaction`.commodityId,`transaction`.userId,`transaction`.status,`commodity`.name,price,`type`,`shop`.username shopName,`user`.username username,damage_level from `transaction`,`commodity`,`shop`,`user` where `transaction`.commodityId=`commodity`.id and `commodity`.shopId=`shop`.id and `user`.id=`transaction`.userId and `shop`.id=#{shopId} and `transaction`.status=#{status}")
	public List<Transaction> shopTransaction(int shopId,int status);
	
	//插入新数据
	@Insert("insert transaction(commodityId,userId) value (#{commodityId},#{userId})")
	public int addTransaction(int commodityId,int userId);
	
	//修改商品状态
	@Update("update `transaction` set `status`=#{status} where id=#{id}")
	public int modificationTransactionStatus(int id,int status);
}
