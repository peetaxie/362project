package com.project.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.application.dao.CategoryRepository;
import com.project.application.dao.ItemRepository;
import com.project.application.dao.ItemdetailRepository;
import com.project.application.dao.UserRepository;
import com.project.application.model.Category;
import com.project.application.model.Item;
import com.project.application.model.Itemdetail;
import com.project.application.model.User;

/** 
* @author Yuhang Xie 
* @version Mar 14, 2019 3:06:46 PM 
*/

@RestController
@CrossOrigin(origins = "http://localhost:8181")
@RequestMapping(path="/item")
public class ItemController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ItemdetailRepository itemdetailRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	
	/*
	 * add new item in databse by given userid,categoryid,title,desc and price
	 */
	@RequestMapping(path="/post")
	public	@ResponseBody Map<String, String>  addItem (@RequestParam Integer id,@RequestParam Integer categoryId,
									@RequestParam String title,@RequestParam String desc, @RequestParam double price) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("addnewitem: "+"title"+title+" desc-"+desc+" price-"+price);
		Item item = new Item();
		item.setUserid(id);
		item.setTitle(title);
		item.setDescription(desc);
		item.setPrice(price);
		try{
			//find category by id in database
			Category category = categoryRepository.findById(categoryId);
			item.setCategory(category);
			//update database
			itemRepository.save(item);
			response.put("code", "1");
			response.put("message", "item added!");
		}
		catch(Exception e) {
			response.put("code", "0");
			response.put("message", "item added fail!");
		}	
		return response;
	}
	
	/*
	 * delete item in database by given itemid
	 */
	@RequestMapping(path="/delete")
	public	@ResponseBody Map<String, String>  deleteItem (@RequestParam Integer id) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("deleteitem: "+"id"+id);
		Item item = new  Item();
		//find item by id in database
		item=itemRepository.findById(id);
		if(item != null) {
			try{
				//move item in database
				itemRepository.delete(item);
				response.put("message", "delete success!");
			}catch(Exception e) {			
				response.put("code", "0");
			}			
		}
		else {
			response.put("message", "item not found!");
			
		}

		return response;
	}
	
	//update item in database by given itemid, title, desc and price
	@RequestMapping(path="/edit")
	public	@ResponseBody Map<String, String>  edititem (@RequestParam(value="id", required = true) Integer id,@RequestParam(value="title", required = false) String title, @RequestParam(value="desc", required = false) String desc, @RequestParam(value="price", required = true, defaultValue="0") double price) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("edititem: "+"id"+id);
		Item item = new  Item();
		item=itemRepository.findById(id);
		if(item != null) {
			if(title!=null)	{
				item.setTitle(title);
			}
			if(desc!=null) {
				item.setDescription(desc);
			}
			try{
				item.setPrice(price);
			}catch(Exception e) {
				
			}
			try {
				//update item in database
				itemRepository.save(item);
				response.put("message", "edited item success!");
			}catch(Exception e) {
				
			}
		}
		else {
			response.put("message", "item not found!");
		}
		return response;
	}
	
	/*
	 * get item info from database by given item id
	 */
	@RequestMapping(path="/getitem")
	public	@ResponseBody Object  getitem (@RequestParam Integer id) {
		Map<String, Object>  response= new HashMap<>();
		System.out.println("getitem: "+"id"+id);
		Item item = new  Item();
		//find item by id in database
		item=itemRepository.findById(id);
		if(item != null) {
			//List<Itemdetail> detail=itemdetailRepository.findByItem(item);
			List<Itemdetail> detail=itemdetailRepository.findByItem(item);
			//item.setDetaillist(detail);	
			return item;
		}
		else {
			response.put("message", "item not found!");		
		}
		return response;
	}
	
	/*
	 * get all items from database
	 */
	@RequestMapping(path="/getallitems")
	public	@ResponseBody Object getallitems () {
		Map<String, Object>  response= new HashMap<>();
		System.out.println("getallitems");
		List<Item> list = new ArrayList<Item>();
		//list all items from database
		list=(List<Item>) itemRepository.findByIsSold(0);
		if(list.size()>0) {
			return list;
		}
		else {
			response.put("message", "Not any items!");	
			return response;
		}
	}
	
	//list suggest item from database by given userid
	@RequestMapping(path="/suggest")
	public	@ResponseBody Object suggestItems (@RequestParam Integer userid) {
		Map<String, Object>  response= new HashMap<>();
		System.out.println("get suggest Items");
		User user = userRepository.findById(userid);
		//find category by id in database
		Category category = categoryRepository.findById(user.getLBC());
		
		if(category !=null) {
			List<Item> list = new ArrayList<Item>();
			//find items by given category and unsold from database
			list=(List<Item>) itemRepository.findByCategoryAndIsSoldNot(category,1);
			//if item exist
			if(list.size()>0 ) {
				return list;
			}
			else {
				response.put("message", "Not any items suggest!");	
				return response;
			}	
		}
		response.put("message", "Not any items suggest!");	
		return response;
	}
	
	/*
	 * search item title and desc from database by given key
	 * return items
	 */
	@RequestMapping(path="/search")
	public	@ResponseBody Object searchitem(@RequestParam(required = true) String key) {
		Map<String, Object>  response= new HashMap<>();
		System.out.println("searchitem: "+key);
		//key exist
		if(key == null) {
			response.put("message", "searchquery cannot be empty");	
		}
		else {
			key = "%"+key+"%";
			List<Item> list = new ArrayList<Item>();
			//find items from database
			//list = (List<Item>) itemRepository.findByTitleOrDescriptionIgnoreCaseContaining(key, key);
			list = (List<Item>) itemRepository.findAllByTitleLike(key);
			
			if(list.size()>0) {
				return list;
			}
			else {
				response.put("message", "Not any items found!");	
				return response;
			}
			
		}
		return response;
	}
	
	/*
	 * add comment in item by user
	 * given itemid, userid, rank and comment
	 */
	@RequestMapping(path="/itemcomment")
	public	@ResponseBody Map<String, String> commentitem(@RequestParam Integer itemId, @RequestParam Integer userId,
										@RequestParam Integer rank,@RequestParam String comment) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("user: "+userId +" item:"+itemId);
		//find user by id in database
		User user = userRepository.findById(userId);
		//user exist
		if(user != null) {
			Item item = new  Item();
			item=itemRepository.findById(itemId);
			//item exist
			if(item != null) {
				response.put("code", "1");
				Itemdetail itemdetail = new Itemdetail();
				itemdetail.setRanking(rank);
				itemdetail.setComment(comment);
				itemdetail.setItem(item);
				itemdetail.setUser(user);
				//save item's rank and comment in database
				itemdetailRepository.save(itemdetail);	
				response.put("message", "add rank and comment success!");	
			}
			//item not exist
			else {
				response.put("message", "item not found!");		
			}
		}
		//user not exist
		else {
			response.put("message", "user not found!");		
		}
		return response;

	}
	
	
	
	/*
	 * test
	 */
	@RequestMapping("/hello")
	public @ResponseBody Map<String, String> hello() {
		Map<String, String> res = new HashMap<>();
		res.put("code", "hello");
		return res;
	
	}


}
