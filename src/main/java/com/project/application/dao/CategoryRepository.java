package com.project.application.dao;

import org.springframework.data.repository.CrudRepository;

import com.project.application.model.Category;
import com.project.application.model.Itemdetail;

/** 
* @author Yuhang Xie 
* @version Apr 2, 2019 6:15:30 PM 
*/
public interface CategoryRepository extends CrudRepository<Category,Long>{
	Category findById(Integer id);
}
