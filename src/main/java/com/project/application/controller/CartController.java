package com.project.application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.application.dao.CartRepository;
import com.project.application.dao.CategoryRepository;
import com.project.application.dao.ItemRepository;
import com.project.application.dao.TransactionRepository;
import com.project.application.dao.UserRepository;
import com.project.application.model.Cart;
import com.project.application.model.Item;
import com.project.application.model.Transaction;
import com.project.application.model.User;

/** 
 * @author Yuhang Xie 
 * @version Apr 2, 2019 6:16:49 PM 
 */
@Controller	
@RequestMapping(path="/cart")
public class CartController {
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	/*
	 * add item to cart in database
	 */
	@RequestMapping(path="additemtocart")
	public	@ResponseBody Map<String, String>  additemtocart (@RequestParam Integer userid, @RequestParam Integer itemid) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("add item to cart");
		Item item = itemRepository.findById(itemid);
		Cart cart = new Cart();
		cart.setUserid(userid);
		cart.setItemid(itemid);
		cart.setItem(item);
		try{
			//update database
			if(item.getUserid()!=userid) {
				Cart c = cartRepository.findByUseridAndItemid(userid, itemid);
				if(c!=null) {
					response.put("code", "0");
					response.put("message", "item already in cart");
				}
				else {
					cartRepository.save(cart);
					response.put("code", "1");
					response.put("message", "add item success");
				}
			}
			else {
				response.put("code", "0");
				response.put("message", "cannot add yours item");
			}
		}
		catch(Exception e) {
			response.put("code", "0");
			response.put("message", "add cart fail!");
		}	
		return response;
	}
	/*
	 * remove item from cart
	 */
	@RequestMapping(path="removeitemfromcart")
	public	@ResponseBody Map<String, String>  removeitemfromcart (@RequestParam Integer cardid) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("remove item from cart: "+cardid);
		Cart cart = cartRepository.findById(cardid);
		if(cart != null) {
			//add to database
			cartRepository.delete(cart);
			response.put("code", "1");
			response.put("message", "removoe item success");
		}
		else {
			response.put("code", "0");
			response.put("message", "cart not exist!");
		}
		return response;
	}
	/*
	 * get user cart items
	 */
	@RequestMapping(path="getcartitems")
	public	@ResponseBody Object getcartitems (@RequestParam Integer userid) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("get item from cart: "+userid);
		List<Cart> list = cartRepository.findByUserid(userid);
		if(list.size()> 0) {
			return list;
		}
		else {
			response.put("code", "0");
			response.put("message", "no cart item!");
		}
		return response;
	}
	/*
	 * pay for item by cart given buyerid and itemid
	 * if success calculate money for buyer's and seller's wallet
	 */
	@RequestMapping(path="paybycart")
	public @ResponseBody Map<String, String> paybycart(@RequestParam Integer buyerId){
		Map<String, String> response = new HashMap<>();
		System.out.println("user: " + buyerId + "want to pay for the cart");
		User buyer = new User();
		//find buyer by id in database
		buyer = userRepository.findById(buyerId);
		//find item by id in database
		List<Cart> list = (List<Cart>) cartRepository.findByUserid(buyerId);
		//find seller by id in database
		for(int n=0;n<list.size();n++){
			Cart cart=list.get(n);
			Integer itemid=cart.getItemid();
			Item item=itemRepository.findById(itemid);
			User seller = userRepository.findById(item.getUserid());
			if(buyer != null && item!=null && buyer.getId() != item.getUserid() && item.getIsSold()==0) {
				if(buyer.getWallet()>item.getPrice()) {
					buyer.setWallet(buyer.getWallet() - item.getPrice());
					seller.setWallet(seller.getWallet() + item.getPrice());
					item.setIsSold(1);
					//add rank score for buyer
					buyer.setRanking(buyer.getRanking()+1);
					//add rank score for seller
					seller.setRanking(seller.getRanking()+1);
					//get last bought category for buyer
					buyer.setLBC(item.getCategory().getId());
					userRepository.save(buyer);
					userRepository.save(seller);
					itemRepository.save(item);
					Transaction transaction = new Transaction();
					transaction.setBuyer(buyer);
					transaction.setSeller(seller);
					transaction.setItem(item);
					//save transction in database
					transactionRepository.save(transaction);		
					response.put("message", "Item bought successful");
					response.put("code", "1");
				}else {
					response.put("message", "user does not have enough money");
					response.put("code", "0");
				}
			}
			else {
				response.put("message", "error");
				response.put("code", "0");
			}
			cartRepository.delete(cart);
		}
		return response;
	}
}
