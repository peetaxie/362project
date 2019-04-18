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
* @version Mar 14, 2019 3:01:30 PM 
*/
@Entity
@Table(name = "users") 
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	private String email;
	private String password;
	private Integer phone;
	private Integer age;
	private double wallet=100.00;
	private Integer ranking=0;	
	private Integer visible=1;
	private Integer LBC;
	
	public User() {
		// TODO Auto-generated constructor stub
		this.ranking=0;
		this.visible=1;
		this.wallet=100.00;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visable) {
		this.visible = visable;
	}

	public double getWallet() {
		return wallet;
	}

	public void setWallet(double wallet) {
		this.wallet = wallet;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public Integer getLBC() {
		return LBC;
	}

	public void setLBC(Integer lBC) {
		LBC = lBC;
	}
	
	

}
