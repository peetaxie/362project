package com.project.application.model;



import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/** 
* @author Yuhang Xie 
* @version Mar 14, 2019 3:04:18 PM 
*/
@Entity
@Table(name = "items") 
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	private String title;
	private String description;
	private double price;
	private Integer userid;
	@OneToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="category_id")
	private Category category;
	private Integer isSold=0;
	@OneToMany(mappedBy = "item",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Itemdetail> detaillist;	
	
	public Item() {
		// TODO Auto-generated constructor stub
		this.isSold=0;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Itemdetail> getDetaillist() {
		return detaillist;
	}

	public void setDetaillist(List<Itemdetail> detaillist) {
		this.detaillist = detaillist;
	}

	public Integer getIsSold() {
		return isSold;
	}

	public void setIsSold(Integer isSold) {
		this.isSold = isSold;
	}


}
