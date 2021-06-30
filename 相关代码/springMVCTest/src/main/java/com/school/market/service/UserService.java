package com.school.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.market.dao.ShopCarDao;
import com.school.market.dao.ShopDao;
import com.school.market.dao.TransactionDao;
import com.school.market.dao.UserDao;
import com.school.market.domain.User;
import com.school.market.domain.UserGoods;
@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ShopCarDao shopCarDao;
	
	@Autowired
	private TransactionDao transactionDao;
	
	@Autowired
	private ShopDao shopDao;
	
	public List<User> showUser(int status){
		return userDao.findUser(status);
	}
	
	public int registerUser(String username,String password,String phone,int sex,String email,String city,String account) {
		return userDao.addUser(username, password, phone, sex, email, city, account);
	} 
	
	//扣钱，加积分
	public int buy(int id) {
		//获取用户
		User user=userDao.findId(id);
		//获取购物车表单
		List<UserGoods> goodsCarList=shopCarDao.userGoods(id);
		
		//计算需付金额
		int money=0;
		for (UserGoods goods : goodsCarList) {
			money+=goods.getPrice();
		}	
		
		money=user.getMoney()-money;
		
		//钱不够，返回0
		if(money<0) {
			return 0;
		}
		
		//将购物车数据加入交易表
		for (UserGoods goods : goodsCarList) {
			transactionDao.addTransaction(goods.getId(), id);
			
			//商户得钱
			shopDao.addMoney(goods.getShopId(), goods.getPrice());
		}
		
		//清空购物车
		shopCarDao.removeShopCar(id);
		//加积分
		userDao.addIntegral(id, money);
		
		

		return userDao.modificationMoney(id, money);
	}
	//
	public User user(int id) {
		return userDao.findId(id);
	}
	
	
	/**
	 * //根据用户名和密码查找用户
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username,String password) {
		return userDao.find(username, password);
	}
	
	/**
	 * 修改对应用户的状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int modificationUserStatus(int id,int status) {
		return userDao.modificationUserStatus(id, status);
	}
	
	/**
	 * 加钱
	 * @param id
	 * @param money
	 * @return
	 */
	public int addMoney(int id,int money) {
		User user=userDao.findId(id);
		
		money+=user.getMoney();
		
		return userDao.modificationMoney(id, money);
	}
	
	/**
	 * 加购物车
	 * @param userId
	 * @param commodityId
	 * @return
	 */
	public int addShopCar(int userId,int commodityId) {
		return shopCarDao.addShopCar(userId, commodityId);
	}
}
