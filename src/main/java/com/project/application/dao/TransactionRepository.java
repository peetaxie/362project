package com.project.application.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.application.model.Item;
import com.project.application.model.Itemdetail;
import com.project.application.model.Transaction;
import com.project.application.model.User;

/** 
* @author Yuhang Xie 
* @version Apr 1, 2019 1:41:01 PM 
*/
public interface TransactionRepository extends CrudRepository<Transaction,Long> {
	public Transaction findById(Integer id);
	public List<Transaction> findByBuyer(User buyer);
}
