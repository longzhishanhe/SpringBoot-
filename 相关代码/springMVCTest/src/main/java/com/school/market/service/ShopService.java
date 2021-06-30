package com.school.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.market.dao.CommodityDao;
import com.school.market.dao.ShopDao;
import com.school.market.domain.Goods;
import com.school.market.domain.Shop;

@Service
public class ShopService {
	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private CommodityDao commodityDao;
	
	public List<Shop> showShop(int status){
		return shopDao.findShop(status);
	}
	
	public int modificationLevel(int id,int level) {
		return this.shopDao.modificationLevel(id, level);	
	}
	
	public int registerShop(String username,String password,String phone,int sex,String account) {
		return this.shopDao.addShop(username, password, phone, sex, account);
	};
	
	/**
	 * 修改对应商户的状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int modificationShopStatus(int id,int status) {
		return shopDao.modificationShopStatus(id, status);
	}
	
	/**
	 * //根据用户名和密码查找商铺
	 * @param username
	 * @param password
	 * @return
	 */
	public Shop login(String username,String password) {
		return shopDao.find(username, password);
	}
	
	//
	public List<Goods> shopGoods(int shopId){
		return commodityDao.shopGoods(shopId);
	}
	
	//给商户加钱
	public int addMoney(int id,int money) {
		return shopDao.addMoney(id, money);
	}
}
