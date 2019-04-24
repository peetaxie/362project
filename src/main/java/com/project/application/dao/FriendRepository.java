package com.project.application.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.application.model.Friend;
import com.project.application.model.User;



/** 
* @author Yuhang Xie 
* @version Apr 21, 2019 4:32:37 PM 
*/
public interface FriendRepository extends CrudRepository<Friend,Long>{
	List<Friend> findByUser1(User user);
	List<Friend> findByUser2(User user);
	Friend findByUser1AndUser2(User user1,User user2);
	List<Friend> findByUser2AndAccpet(User user,Integer num);
	List<Friend> findByUser1AndAccpet(User user,Integer num);
	Friend findById(Integer id);
}
