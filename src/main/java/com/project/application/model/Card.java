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
@Table(name = "cards")
public class Card {
	@Id
	private Integer id=0;
	private String password;
	private Double balance=60000.00;
	private Integer userid;
	
	public Card() {
		
	}
	
	public Card(Integer id) {
		// TODO Auto-generated constructor stub
		this.balance=60000.00;
		this.id=id;
	}
	public Card(Integer id,Integer userid) {
		// TODO Auto-generated constructor stub
		this.balance=60000.00;
		this.id=id;
		this.userid=userid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}


}
