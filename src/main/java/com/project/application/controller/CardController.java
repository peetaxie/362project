package com.project.application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.application.dao.CardRepository;
import com.project.application.dao.CartRepository;
import com.project.application.dao.CategoryRepository;
import com.project.application.dao.ItemRepository;
import com.project.application.dao.TransactionRepository;
import com.project.application.dao.UserRepository;
import com.project.application.model.Card;
import com.project.application.model.Cart;
import com.project.application.model.Item;
import com.project.application.model.Transaction;
import com.project.application.model.User;

/** 
 * @author Yuhang Xie 
 * @version April 20, 2019 6:16:49 PM 
 */
@Controller	
@RequestMapping(path="/card")
public class CardController {
	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	/*
	 * add new card in database
	 */
	@RequestMapping(path="addcard")
	public	@ResponseBody Map<String, String>  addcard (@RequestParam Integer id,@RequestParam Integer userid, @RequestParam String password) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("add new card: "+id);
		Card card = new Card(id,userid);
		card.setPassword(password);
		User user = userRepository.findById(userid);
		try{
			//update database
			cardRepository.save(card);
			user.setCard(card);
			userRepository.save(user);
			response.put("code", "1");
			response.put("message", "added new card");
		}
		catch(Exception e) {
			response.put("code", "0");
			response.put("message", "add fail!");
		}	
		return response;
	}
	/*
	 * set password for card
	 */
	@RequestMapping(path="setpassword")
	public	@ResponseBody Map<String, String>  setpassword (@RequestParam Integer cardid, @RequestParam String password) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("set new password for card: "+cardid);
		Card card = cardRepository.findById(cardid);

		if(card != null) {
			card.setPassword(password);
			//add to database
			cardRepository.save(card);
			response.put("code", "1");
			response.put("message", "set password success");
		}
		else {
			response.put("code", "0");
			response.put("message", "card not exist!");
		}
		return response;
	}
	/*
	 * pay by card
	 */
	@RequestMapping(path="paybycard")
	public	@ResponseBody Map<String, String>  paybycard (@RequestParam Integer buyerId) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("pay by card: "+buyerId);
		User buyer = new User();
		//find buyer by id in database
		buyer = userRepository.findById(buyerId);
		if(buyer.getCard()==null) {
			response.put("code", "0");
			response.put("message", "you do not have a card");
		}
		else {
			Card card = cardRepository.findById(buyer.getCard().getId());
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
						card.setBalance(card.getBalance()-item.getPrice());
						buyer.setCard(card);
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
						cardRepository.save(card);
						Transaction transaction = new Transaction();
						transaction.setBuyer(buyer);
						transaction.setSeller(seller);
						transaction.setItem(item);
						//save transction in database
						transactionRepository.save(transaction);		
						response.put("message", "Item bought successful");
						response.put("code", "1");
					}else {
						response.put("message", "user' card does not have enough money");
						response.put("code", "0");
					}
				}
				else {
					response.put("message", "error");
					response.put("code", "0");
				}
				cartRepository.delete(cart);
			}
		}
		return response;
	}
	/*
	 * set balance for card
	 */
	@RequestMapping(path="setbalance")
	public	@ResponseBody Map<String, String>  setbalance (@RequestParam Double balance, @RequestParam Integer cardid, @RequestParam String password) {
		Map<String, String>  response= new HashMap<>();
		System.out.println("set new balance for item: "+password);
		Card card = cardRepository.findById(cardid);
		if(card != null) {
			card.setBalance(balance);
			//add to database
			cardRepository.save(card);
			response.put("code", "1");
			response.put("message", "set balance success");
		}
		else {
			response.put("code", "0");
			response.put("message", "card not exist!");
		}
		return response;
	}
	/*
	 * get card info from database by given card id
	 */
	@RequestMapping(path="/getcard")
	public	@ResponseBody Object  getcard (@RequestParam Integer id) {
		Map<String, Object>  response= new HashMap<>();
		System.out.println("getcard: "+"id"+id);
		Card card = cardRepository.findById(id);
		//find item by id in database
		if(card != null) {
			return card;
		}
		else {
			response.put("message", "card not found!");		
		}
		return response;
	}
}
