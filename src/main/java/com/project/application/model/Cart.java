package com.project.application.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/** 
* @author Yuhang Xie 
* @version Apr 23, 2019 3:01:30 PM 
*/
@Entity
@Table(name = "carts")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer itemid;
	 @OneToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	 @JoinColumn(name="item_id")
//	 @JsonIgnore
	 private Item item;
	private Integer userid;
	private ArrayList<Item> list;
	
	

	public Cart() {
		// TODO Auto-generated constructor stub
		this.list=new ArrayList<Item>();
	}
    public Integer getItemid() {
		return itemid;
	}

	public Integer getUserid() {
		return userid;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setItemid(Integer itemid) {
		this.itemid=itemid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public ArrayList<Item> getList() {
		return list;
	}
	public void setList(ArrayList<Item> list) {
		this.list = list;
	}



}
