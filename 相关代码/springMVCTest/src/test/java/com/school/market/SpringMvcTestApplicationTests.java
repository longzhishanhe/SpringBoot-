package com.school.market;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.school.market.dao.AdminDao;
import com.school.market.dao.ShopCarDao;
import com.school.market.dao.ShopDao;
import com.school.market.dao.TransactionDao;
import com.school.market.domain.Admin;
import com.school.market.domain.Transaction;
import com.school.market.domain.UserGoods;

@SpringBootTest
class SpringMvcTestApplicationTests {

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private ShopDao shopDao;
	
	@Autowired
	private ShopCarDao shopCarDao;
	
	@Autowired
	private TransactionDao transactionDao;
	
	@Test
	void contextLoads() {
		List<Admin> a=adminDao.findAll();
		for (Admin admin : a) {
			System.out.println(admin.toString());
		}
		
		System.out.println("+++++++++++++++++++++");
		Admin admin=adminDao.findAdmin("root", "root");
		System.out.println(admin);
	}
	
	@Test
	void test1() {
		int i=shopDao.modificationLevel(6, 3);
		System.out.println("------------   "+i);
	}
	
	@Test
	void test2() {
		List<UserGoods> u=shopCarDao.userGoods(3);
		System.out.println("-----------2------------");
		for (UserGoods userGoods : u) {
			System.out.println(userGoods.getShopCarId());
		}
	}
	
	@Test
	void test3() {
		List<Transaction> t=transactionDao.shopTransaction(7, 0);
		System.out.println("-----------3------------");
		for (Transaction tran : t) {
			System.out.println(tran);
		}
	}

}
