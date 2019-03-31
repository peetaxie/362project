package com.project.application.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.application.model.User;
import com.project.application.dao.UserRepository;

/** 
* @author Yuhang Xie 
* @version Mar 14, 2019 3:06:31 PM 
*/
@Controller	
@RequestMapping(path="/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(path="signup")
	public	@ResponseBody Map<String, String>  addNewUser (@RequestParam String name, @RequestParam String email, @RequestParam String password) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("addnewuser: "+"name-"+name+" email-"+email+" password-"+password);
		User checkuser = new User();
		checkuser = userRepository.findByEmail(email);
		//if user exist
		if(checkuser != null) {
			System.out.println(" email-"+email+" exist!");
			response.put("code", "0");
			response.put("message", "user exist!");
		}
		//add to database
		else {
			User user = new User();
			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);
			userRepository.save(user);
			response.put("code", "1");
			response.put("message", "signup success");
		}
		return response;
	}
	
	@RequestMapping(path="/login")
	public	@ResponseBody  Map<String, String> login (@RequestParam String email, @RequestParam String password) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("userlogin: "+" email-"+email+" password-"+password);
		User checkuser = new User();
		checkuser = userRepository.findByEmail(email);
		//if user exist
		if(checkuser != null) {
			if(password.equals(checkuser.getPassword())){
				//login success
				response.put("code", "1");
				response.put("message", "login success");
			}
			else {
				response.put("code", "0");
				//wrong password
				response.put("message", "login fail");
			}
			
		}
		else {
			//user not exist
			response.put("code", "0");
			response.put("message", "user not exist!");
		}
		return response;
	}
	
	@RequestMapping(path="/getuser")
	public	@ResponseBody  Map<String, String> getuserinfo (@RequestParam String email) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("userlogin: "+" email-"+email);
		User checkuser = new User();
		checkuser = userRepository.findByEmail(email);
		//if user exist
		if(checkuser != null) {
			response.put("code", "1");
			response.put("Id",checkuser.getId().toString());
			response.put("name",checkuser.getName());
			response.put("Email",checkuser.getEmail());
			
		}
		else {
			//user not exist
			response.put("code", "0");
			response.put("message", "user not exist!");
			
		}
		return response;
	}
	
	@RequestMapping(path="/deleteuser")
	public	@ResponseBody  Map<String, String> deleteuser (@RequestParam String email) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("deleteuser: "+" email-"+email);
		User checkuser = new User();
		checkuser = userRepository.findByEmail(email);
		//if user exist
		if(checkuser != null) {
			userRepository.delete(checkuser);		
			response.put("message", "delete success");
		}
		else {
			//user not exist
			response.put("code", "0");
			response.put("message", "user not exist!");
		}
		return response;
	}
	
	@RequestMapping("/logout")
	public @ResponseBody Map<String, String> hello() {
		Map<String, String> res = new HashMap<>();
		res.put("message", "logout success!");
		return res;
	
	}
	
	
	@RequestMapping("/resetpass")
	public @ResponseBody Map<String, String> resetpass(@RequestParam String email,@RequestParam String oldpass,@RequestParam String newpass) {
		Map<String, String> res = new HashMap<>();
		System.out.println("user: "+" email-"+email+"want to reset password");
		User user = new User();
		user = userRepository.findByEmail(email);
		//user exist
		if(user != null) {	
			//password correct
			if(oldpass == user.getPassword()) {
				user.setPassword(newpass);
				userRepository.save(user);
				res.put("message", "password reset!");
			}
			//incorrect password
			else {
				res.put("message", "incorrect password");
			}
		}
		else {
			//user not exist
			res.put("code", "0");
			res.put("message", "user not exist!");
		}
		return res;
	
	}

}
