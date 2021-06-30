package com.school.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.market.dao.CommodityDao;
import com.school.market.domain.Commodity;
import com.school.market.domain.Goods;
@Service
public class CommodityService {
	@Autowired
	private CommodityDao commodityDao;
	
	public List<Commodity> showAll(){
		return commodityDao.findAll();
	}
	
	public List<Goods> showAllGoods(){
		return commodityDao.findAllGoods();
	}
	
	public List<Goods> showMyGoods(int userId){
		
		return commodityDao.userGoods(userId);
		
	}
	
	public Goods showOneGoods(int id) {
		return commodityDao.findCommodityId(id);
	}
	
	public int addCommodity(String name,int price,String type, int shopId,int number,int total,int damage_level) {
		return commodityDao.addCommodity(name, price, type, shopId, number, total, damage_level);
	};
	
	public int modificationGoodsStatus(int id,int status) {
		return commodityDao.modificationGoodsStatus(id, status);
	}
	
	public int regulationGoodsNumber(int id,int number) {
		return commodityDao.regulationGoodsNumber(id, number);
	}
}
