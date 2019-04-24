package com.project.application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.application.dao.CategoryRepository;
import com.project.application.dao.CouponRepository;
import com.project.application.dao.ItemRepository;
import com.project.application.dao.UserRepository;
import com.project.application.model.Coupon;


/** 
* @author Yuhang Xie 
* @version Apr 20, 2019 6:16:49 PM 
*/
@Controller	
@RequestMapping(path="/coupon")
public class CouponController {
	@Autowired
	private CouponRepository couponRepository;
	
	/*
	 * add new coupon in database
	 */
	@RequestMapping(path="addcoupon")
	public	@ResponseBody Map<String, String>  addcoupon (@RequestParam Integer userid, @RequestParam Double discount) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("add new coupon: "+discount);
		Coupon coupon = new Coupon(discount);
		coupon.setUserid(userid);
		try{
			//update database
			couponRepository.save(coupon);
			response.put("code", "1");
			response.put("message", "added new coupon");
		}
		catch(Exception e) {
			response.put("code", "0");
			response.put("message", "add fail!");
		}	
		return response;
	}
	/*
	 * get coupon by id
	 */
	@RequestMapping(path="getcoupon")
	public	@ResponseBody Object  getcoupon (@RequestParam Integer couponid) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("find coupon: "+couponid);
		Coupon coupon = couponRepository.findById(couponid);
		//coupon exist
		if(coupon != null) {		
				return coupon;
		}
		else {
			response.put("code", "0");
			response.put("message", "coupon not exist!");
		}
		return response;
	}
	/*
	 * get all coupons by user
	 */
	@RequestMapping(path="getallcoupons")
	public	@ResponseBody Object  getallcoupons (@RequestParam Integer userid) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("find all coupons: "+userid);
		List<Coupon> list = couponRepository.findByUserid(userid);
		//coupon exist
		if(list.size()>0) {		
			return list;
		}
		else {
			response.put("code", "0");
			response.put("message", "coupon not exist!");
		}
		return response;
	}
}
