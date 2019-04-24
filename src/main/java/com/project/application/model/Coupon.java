package com.project.application.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** 
* @author Yuhang Xie 
* @version April 20, 2019 3:01:30 PM 
*/
@Entity
@Table(name = "coupon")
public class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Double discount=0.0;
	private Integer userid;
	
	public Coupon() {
		
	}
	
	public Coupon(Double discount) {
		// TODO Auto-generated constructor stub
		this.discount=discount;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getDiscount() { return discount; }

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	

}
