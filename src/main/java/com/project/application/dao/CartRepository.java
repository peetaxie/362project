package com.project.application.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.application.model.Cart;

/** 
* @author Yuhang Xie 
* @version Apr 23, 2019 6:15:30 PM 
*/
public interface CartRepository extends CrudRepository<Cart,Long>{
	Cart findById(Integer id);
	public List<Cart> findByUserid(Integer userid);
	public Cart findByUseridAndItemid(Integer userid,Integer itemid);
}
