package com.school.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.market.dao.ShopCarDao;
import com.school.market.domain.UserGoods;

@Service
public class ShopCarService {
	@Autowired
	private ShopCarDao shopCarDao;
	
	public List<UserGoods> userGoods(int userId){
		return shopCarDao.userGoods(userId);
	}
	
	public int removeOneShopCar(int shopCarId) {
		return this.shopCarDao.removeOneShopCar(shopCarId);
	}
	
	public UserGoods findIdGoods(int id) {
		return shopCarDao.findIdGoods(id);
	}
}
