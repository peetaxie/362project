package com.project.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.application.dao.ItemRepository;
import com.project.application.dao.UserRepository;
import com.project.application.model.Item;
import com.project.application.model.User;

/** 
* @author Yuhang Xie 
* @version Mar 14, 2019 3:06:46 PM 
*/

@Controller	
@RequestMapping(path="/item")
public class ItemController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ItemRepository itemRepository;
	
	@RequestMapping(path="post")
	public	@ResponseBody Map<String, String>  addItem (@RequestParam String title, @RequestParam String desc, @RequestParam double price) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("addnewitem: "+"title"+title+" desc-"+desc+" price-"+price);
		Item item = new  Item();
		item.setTitle(title);
		item.setDescription(desc);
		item.setPrice(price);
		try{
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
	
	@RequestMapping(path="delete")
	public	@ResponseBody Map<String, String>  deleteItem (@RequestParam Integer id) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("deleteitem: "+"id"+id);
		Item item = new  Item();
		item=itemRepository.findById(id);
		if(item != null) {
			try{
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
	
	@RequestMapping(path="edit")
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
	
	@RequestMapping(path="getitem")
	public	@ResponseBody Map<String, Object>  getitem (@RequestParam Integer id) {
		Map<String, Object>  response= new HashMap<>();
		System.out.println("getitem: "+"id"+id);
		Item item = new  Item();
		item=itemRepository.findById(id);
		if(item != null) {
			response.put("code", "1");
			response.put("Id",item.getId().toString());
			response.put("Title",item.getTitle());
			response.put("Description",item.getDescription());
			response.put("Price",item.getPrice());
		}
		else {
			response.put("message", "item not found!");		
		}
		return response;
	}
	
	@RequestMapping(path="getallitems")
	public	@ResponseBody Object getallitems () {
		Map<String, Object>  response= new HashMap<>();
		System.out.println("getallitems");
		List<Item> list = new ArrayList<Item>();
		list=(List<Item>) itemRepository.findAll();
		if(list.size()>0) {
			return list;
		}
		else {
			response.put("message", "Not any items!");	
			return response;
		}
	}
	
	@RequestMapping(path="search")
	public	@ResponseBody Object searchitem(@RequestParam(required = true) String key) {
		Map<String, Object>  response= new HashMap<>();
		System.out.println("searchitem: "+key);
		if(key == null) {
			response.put("message", "searchquery cannot be empty");	
		}
		else {
			List<Item> list = new ArrayList<Item>();
//			list = (List<Item>) itemRepository.findByTitleIgnoreCaseContaining(key);
			list = (List<Item>) itemRepository.findByTitleOrDescriptionIgnoreCaseContaining(key, key);
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
	
	
	
	
	@RequestMapping("/hello")
	public @ResponseBody Map<String, String> hello() {
		Map<String, String> res = new HashMap<>();
		res.put("code", "hello");
		return res;
	
	}


}
