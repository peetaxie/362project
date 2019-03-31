package com.project.application.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.project.application.model.User;

/** 
* @author Yuhang Xie 
* @version Mar 14, 2019 3:19:26 PM 
*/
public interface UserRepository extends CrudRepository<User,Long>{
	User findByEmail(String email);
	User findById(Integer id);
}
