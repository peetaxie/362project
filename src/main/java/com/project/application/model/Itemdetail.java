package com.project.application.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.junit.Ignore;

import com.fasterxml.jackson.annotation.JsonIgnore;

/** 
* @author Yuhang Xie 
* @version Mar 31, 2019 3:53:32 PM 
*/

@Entity
public class Itemdetail{ 
	 @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
     private Integer id;
	 private Integer ranking =0;
	 private String comment;
	 @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	 @JoinColumn(name="item_id")
	 @JsonIgnore
	 private Item item;
	 @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	 @JoinColumn(name="user_id")
	 private User user;
	 
	 public Itemdetail() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	 
	

}
