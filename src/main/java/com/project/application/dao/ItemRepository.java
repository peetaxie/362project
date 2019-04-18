package com.project.application.dao;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.project.application.model.Category;
import com.project.application.model.Item;

/** 
* @author Yuhang Xie 
* @version Mar 14, 2019 3:56:58 PM 
*/
public interface ItemRepository extends CrudRepository<Item,Long>{
	
	Item findById(Integer id);
//	@Query("select i from Item i where i.title like %:key%")
	public List<Item> findByTitleOrDescriptionIgnoreCaseContaining(String key1,String key2);
	public List<Item> findAllByTitleLike(String key);
	public List<Item> findByCategoryAndIsSoldNot(Category category,Integer id);
	
	public List<Item> findByIsSold(Integer id);
}
