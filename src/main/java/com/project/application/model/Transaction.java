package com.project.application.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

/** 
* @author Yuhang Xie 
* @version Apr 1, 2019 1:25:41 PM 
*/

@Entity
public class Transaction {
	 @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
     private Integer id;
	 @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	 @JoinColumn(name="buyer_id")
	 private User buyer;
	 
	 @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	 @JoinColumn(name="seller_id")
	 private User seller;	 

	 @OneToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	 @JoinColumn(name="item_id")
//	 @JsonIgnore
	 private Item item;
	 
	 @CreationTimestamp
	 private Date dateTime;
	 
	 public Transaction() {
		 
	 }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	 
	 

}
