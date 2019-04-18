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
	
	/*
	 * register new user by given name, email and passowrd
	 * if success return "signup success" message
	 */
	@RequestMapping(path="signup")
	public	@ResponseBody Map<String, String>  addNewUser (@RequestParam String name, @RequestParam String email, @RequestParam String password) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("addnewuser: "+"name-"+name+" email-"+email+" password-"+password);
		User checkuser = new User();
		checkuser = userRepository.findByEmail(email);
		//if user exist in database
		if(checkuser != null) {
			System.out.println(" email-"+email+" exist!");
			response.put("code", "0");
			response.put("message", "user exist!");
		}	
		else {
			User user = new User();
			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);
			//add new user to database
			userRepository.save(user);
			response.put("code", "1");
			response.put("message", "signup success");
		}
		return response;
	}
	
	/*
	 * user login by given email and password
	 */
	@RequestMapping(path="/login")
	public	@ResponseBody  Map<String, Object> login (@RequestParam String email, @RequestParam String password) {
		Map<String, Object>  response= new HashMap<>();
		System.out.println("userlogin: "+" email-"+email+" password-"+password);
		User checkuser = new User();
		//find user from database
		checkuser = userRepository.findByEmail(email);
		//if user exist
		if(checkuser != null) {
			if(password.equals(checkuser.getPassword())){
				//login success
				response.put("code", "1");
				response.put("message", "login success");
				response.put("user", checkuser);
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
	
	/*
	 * return user info from database by given user's email
	 */
	@RequestMapping(path="/getuser")
	public	@ResponseBody  Map<String, String> getuserinfo (@RequestParam String email) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("userlogin: "+" email-"+email);
		User checkuser = new User();
		//get user from database
		checkuser = userRepository.findByEmail(email);
		//if user exist
		if(checkuser != null) {	
			response.put("code", "1");
			//if private
			if(checkuser.getVisible()==0) {
				response.put("Id",checkuser.getId().toString());
				response.put("Email",checkuser.getEmail());
			}
			//if public
			else {
				response.put("Id",checkuser.getId().toString());
				response.put("name",checkuser.getName());
				response.put("email",checkuser.getEmail());
				if(checkuser.getAge()!=null) {
					response.put("age",checkuser.getAge().toString());
				}
				else {
					response.put("age","null");
				}
				if(checkuser.getPhone()!=null) {
					response.put("phone",checkuser.getPhone().toString());
				}
				else {
					response.put("phone","null");
				}
				response.put("rank",checkuser.getRanking().toString());
			}
			
		}
		else {
			//user not exist
			response.put("code", "0");
			response.put("message", "user not exist!");		
		}
		return response;
	}
	
	
	/*
	 * return user info by given userid
	 */
	@RequestMapping(path="/user")
	public	@ResponseBody  Object getuserinfo (@RequestParam Integer id) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("getuser: "+" id-"+id);
		User checkuser = new User();
		checkuser = userRepository.findById(id);
		//if user exist
		if(checkuser != null) {
			response.put("code", "1");
			return checkuser;	
		}
		else {
			//user not exist
			response.put("code", "0");
			response.put("message", "user not exist!");		
		}
		return response;
	}
	
	/*
	 * delete user in database by given user' email
	 */
	@RequestMapping(path="/deleteuser")
	public	@ResponseBody  Map<String, String> deleteuser (@RequestParam String email) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("deleteuser: "+" email-"+email);
		User checkuser = new User();
		//get user from database
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
	
	/*
	 * reset user's password by given user email, old password and new password
	 * if old password correct then replace with new passwird
	 */
	@RequestMapping("/resetpass")
	public @ResponseBody Map<String, String> resetpass(@RequestParam String email,@RequestParam String oldpass,@RequestParam String newpass) {
		Map<String, String> res = new HashMap<>();
		System.out.println("user: "+" email-"+email+"want to reset password:"+ oldpass + "to:" + newpass);
		User user = new User();
		user = userRepository.findByEmail(email);
		//user exist
		if(user != null) {	
			System.out.println("userpass:"+user.getPassword());
			//password correct
			if(oldpass.equals(user.getPassword())) {
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
	
	/*
	 * set peronal profile in database by given userid, name, phone number and age
	 */
	@RequestMapping(path="/setprofile")
	public	@ResponseBody  Map<String, String> setprofile (@RequestParam(value="id", required = true) Integer id,
															@RequestParam(value="name", required = false) String name,
															@RequestParam(value="phone", required = false) Integer phone,
															@RequestParam(value="age", required = false) Integer age) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("setprofile: "+" userid-"+id);
		User user = new User();
		user = userRepository.findById(id);
		//if user exist
		if(user != null) {
			if(name != null) {
				user.setName(name);
			}
			if(phone !=null ) {
				user.setPhone(phone);
			}
			if(age != null) {
				user.setAge(age);
			}
			//update user's info in data
			userRepository.save(user);
			response.put("message", "updated user profile!");
		}
		else {
			//user not exist
			response.put("code", "0");
			response.put("message", "user not exist!");
			
		}
		return response;
	}
	
	/*
	 * set privacy for user by given userid and user's privacy
	 * if visible equal 0 then cannot see part of user's info, otherwise.
	 */
	@RequestMapping("/setprivacycontrol")
	public @ResponseBody Map<String, String> setprivacycontrol(@RequestParam Integer id, @RequestParam Integer visible) {
		Map<String, String> res = new HashMap<>();
		System.out.println("user: "+ id +"want to set privacy control");
		User user = new User();
		//get user from database
		user = userRepository.findById(id);
		//user exist
		if(user != null) {	
			//set visible
			user.setVisible(visible);
			userRepository.save(user);
			res.put("message", "set privacy control success");
		}
		else {
			//user not exist
			res.put("code", "0");
			res.put("message", "user not exist!");
		}
		return res;
	
	}
	
	

}
