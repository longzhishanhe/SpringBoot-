package com.school.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.school.market.dao.TransactionDao;
import com.school.market.domain.Transaction;

@Service
public class TransactionService {
	@Autowired
	private TransactionDao transactionDao;
	
	public int addTransaction(int commodityId,int userId) {
		return transactionDao.addTransaction(commodityId, userId);
	}
	
	//根据用户id与表单状态查找
	public List<Transaction> userTransaction(int userId,int status){
		return transactionDao.userTransaction(userId, status);
	}
	
	//根据商家id与表单状态查找
	public List<Transaction> shopTransaction(int shopId,int status){
		return transactionDao.shopTransaction(shopId, status);
	}
	
	//修改交易表状态
	public int modificationTransactionStatus(int id,int status) {
		return transactionDao.modificationTransactionStatus(id, status);
	}
}
