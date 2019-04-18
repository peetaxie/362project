package com.project.application.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.project.application.model.Item;
import com.project.application.model.Itemdetail;

/** 
* @author Yuhang Xie 
* @version Mar 31, 2019 4:49:09 PM 
*/
public interface ItemdetailRepository extends CrudRepository<Itemdetail,Long>{
	//public List<Itemdetail> findByitem_id(Integer item_id);
	
	public List<Itemdetail> findByItem(Item item);
}
