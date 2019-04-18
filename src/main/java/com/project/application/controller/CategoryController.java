package com.project.application.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.application.dao.CategoryRepository;
import com.project.application.dao.ItemRepository;
import com.project.application.dao.UserRepository;
import com.project.application.model.Category;
import com.project.application.model.Item;

/** 
* @author Yuhang Xie 
* @version Apr 2, 2019 6:16:49 PM 
*/
@Controller	
@RequestMapping(path="/category")
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ItemRepository itemRepository;
	
	/*
	 * add new category in database
	 */
	@RequestMapping(path="addcategory")
	public	@ResponseBody Map<String, String>  addItem (@RequestParam String name) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("add new category: "+name);
		Category category = new Category();
		category.setName(name);
		try{
			//update database
			categoryRepository.save(category);
			response.put("code", "1");
			response.put("message", "added new category");
		}
		catch(Exception e) {
			response.put("code", "0");
			response.put("message", "add fail!");
		}	
		return response;
	}
	/*
	 * set category for item by given itemid and categoryid
	 */
	@RequestMapping(path="setcategory")
	public	@ResponseBody Map<String, String>  setcategory (@RequestParam Integer itemid, @RequestParam Integer cateid) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("set new category for item: "+itemid);
		Item item = itemRepository.findById(itemid);
		Category category = categoryRepository.findById(cateid);
		//item exist
		if(item != null) {
			//category exist
			if(category != null) {
				item.setCategory(category);
				//add to database
				itemRepository.save(item);
				response.put("code", "1");
				response.put("message", "set category success");
			}
			else {
				response.put("code", "0");
				response.put("message", "category not exist!");
			}
		}
		else {
			response.put("code", "0");
			response.put("message", "item not exist!");
		}
		return response;
	}
}
