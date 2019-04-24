package com.project.application.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/** 
* @author Yuhang Xie 
* @version Apr 21, 2019 4:58:48 PM 
*/
@Entity
public class Friend {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="user1_id")
	private User user1;
	 
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="user2_id")
	private User user2;	 
	private Integer viewed=0;
	private Integer accpet=0;
	
	public Friend() {}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser1() {
		return user1;
	}
	public void setUser1(User user1) {
		this.user1 = user1;
	}
	public User getUser2() {
		return user2;
	}
	public void setUser2(User user2) {
		this.user2 = user2;
	}
	public Integer getViewed() {
		return viewed;
	}
	public void setViewed(Integer viewed) {
		this.viewed = viewed;
	}
	public Integer getAccpet() {
		return accpet;
	}
	public void setAccpet(Integer accpet) {
		this.accpet = accpet;
	}
	
	
}
