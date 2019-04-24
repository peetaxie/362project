package com.project.application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.application.dao.FriendRepository;
import com.project.application.dao.UserRepository;
import com.project.application.model.Friend;
import com.project.application.model.User;

/** 
* @author Yuhang Xie 
* @version Apr 21, 2019 4:26:55 PM 
*/
@Controller	
@RequestMapping(path="/friend")
public class FriendController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FriendRepository friendRepository;
	/*
	 * add friend relationship
	 */
	@RequestMapping(path="/addfriendReuqest")
	public	@ResponseBody  Map<String, String> addfriendRequest (@RequestParam String email1,@RequestParam String email2) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("add friend request"+email1+" and "+email2);
		User user1 = userRepository.findByEmail(email1);
		User user2 = userRepository.findByEmail(email2);
		Friend check = friendRepository.findByUser1AndUser2(user1, user2);
		//exist
		if(check!=null) {
			response.put("code", "0");
		}
		else {
			//add in database
			Friend f = new Friend();	
			f.setUser1(user1);
			f.setUser2(user2);
			friendRepository.save(f);
			response.put("code", "1");
		}
		return response;
		
	}
	/*
	 * get friend request
	 */
	@RequestMapping(path="/getfriendReuqest")
	public	@ResponseBody  Object getfriendRequest (@RequestParam String email) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("get friend request");
		User user2 = userRepository.findByEmail(email);
		//get in database
		List<Friend> list =  friendRepository.findByUser2AndAccpet(user2,0);
		if(list.size()>0) {
			return list;
		}
		//no request
		else {
			response.put("code", "0");
			response.put("message", "not request");
		}
		return response;
	}
	
	@RequestMapping(path="/acceptReuqest")
	public	@ResponseBody  Object acceptReuqest (@RequestParam Integer id,@RequestParam Integer accept) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("accpet friend request");
		Friend friend = friendRepository.findById(id);
		//reject
		if(accept == 0) {
			friendRepository.delete(friend);
			response.put("code", "1");
			response.put("message", "deleted");
		}
		//accept
		else if(accept==1) {
			friend.setAccpet(1);
			//add both relationship	
			friendRepository.save(friend);
			Friend friend2 = new  Friend();
			friend2.setUser1(friend.getUser2());
			friend2.setUser2(friend.getUser1());
			friend2.setAccpet(1);
			friend2.setViewed(1);
			friendRepository.save(friend2);
			response.put("code", "1");
			response.put("message", "sccuess");
		}
		else {
			response.put("code", "0");
			response.put("message", "error");
		}
		//get in database
		
		return response;
	}
	
	@RequestMapping(path="/getfriends")
	public	@ResponseBody  Object getFriends (@RequestParam String email) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("get friends");
		User user = userRepository.findByEmail(email);
		//get in database
		List<Friend> list =  friendRepository.findByUser1AndAccpet(user,1);
		if(list.size()>0) {
			return list;
		}
		//no request
		else {
			response.put("code", "0");
			response.put("message", "not friend");
		}
		return response;
	}
	
	@RequestMapping(path="/deletefriend")
	public	@ResponseBody  Object deletefriend (@RequestParam Integer id1,@RequestParam Integer id2) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("delete friend");
		User user1= userRepository.findById(id1);
		User user2 = userRepository.findById(id2);
		Friend relation1 = friendRepository.findByUser1AndUser2(user1, user2);
		Friend relation2 = friendRepository.findByUser1AndUser2(user2, user1);
		friendRepository.delete(relation1);
		friendRepository.delete(relation2);
		response.put("code", "1");
		response.put("message", "delete success");
		return response;
	}
}
