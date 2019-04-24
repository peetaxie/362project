package com.project.application.dao;

import com.project.application.model.Coupon;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.application.model.Coupon;

/** 
* @author Yuhang Xie 
* @version Apr 20, 2019 6:15:30 PM 
*/
public interface CouponRepository extends CrudRepository<Coupon,Long>{
	Coupon findById(Integer id);
	List<Coupon> findByUserid(Integer id);
}
