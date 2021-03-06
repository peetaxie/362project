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
import com.project.application.dao.CouponRepository;
import com.project.application.dao.ItemRepository;
import com.project.application.dao.TransactionRepository;
import com.project.application.dao.UserRepository;
import com.project.application.model.Cart;
import com.project.application.model.Coupon;
import com.project.application.model.Item;
import com.project.application.model.Transaction;
import com.project.application.model.User;

/** 
 * @author Yuhang Xie 
 * @version Apr 1, 2019 1:42:29 PM 
 */
@Controller	
@RequestMapping(path="/transaction")
public class TransactionController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private CouponRepository couponRepository;
	@Autowired
	private CartRepository cartRepository;

	/*
	 * pay for item buy given buyerid and itemid
	 * if success calculate money for buyer's and seller's wallet
	 */
	@RequestMapping(path="pay")
	public @ResponseBody Map<String, String> payforitem(@RequestParam Integer buyerId, @RequestParam Integer ItemId){
		Map<String, String> response = new HashMap<>();
		System.out.println("user: " + buyerId + "want to buy item" + ItemId);
		User buyer = new User();
		//find buyer by id in database
		buyer = userRepository.findById(buyerId);
		//find item by id in database
		Item item = itemRepository.findById(ItemId);
		//find seller by id in database
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
			}else {
				response.put("message", "user doesnot have enough money");
			}
		}
		else {
			response.put("message", "error");
		}

		return response;
	}

	/*
	 * return item by given buyerid and transsctionid
	 * if success set item unsold , return money to buyer and delete transaction
	 */
	@RequestMapping(path="return")
	public @ResponseBody Map<String, String> returnitem(@RequestParam Integer buyerId, @RequestParam Integer transId){
		Map<String, String> response = new HashMap<>();
		System.out.println("user: " + buyerId + "want to return item" + transId);
		User buyer = new User();
		buyer = userRepository.findById(buyerId);
		Transaction tc = transactionRepository.findById(transId);
		Item item = itemRepository.findById(tc.getItem().getId());
		//user exist
		if(buyer != null ) {
			//transaction exist
			if(tc != null) {
				if(buyer.getId() == tc.getBuyer().getId()) {
					User seller = userRepository.findById(item.getUserid());
					//return money for buyer
					buyer.setWallet(buyer.getWallet()+item.getPrice());
					//return money for seller
					seller.setWallet(seller.getWallet()-item.getPrice());	
					//minus rank score for buyer
					buyer.setRanking(buyer.getRanking()-1);
					//minus rank score for seller
					seller.setRanking(seller.getRanking()-1);
					userRepository.save(buyer);
					userRepository.save(seller);
					item.setIsSold(0);
					itemRepository.save(item);
					transactionRepository.delete(tc);
					response.put("message", "return successful");
				}
				else {
					response.put("message", "error");
				}
			}
			else {
				response.put("message", "transaction not exist");
			}
		}
		else {
			response.put("message", "user not exist");
		}

		return response;
	}
	@RequestMapping(path="getorder")
	public @ResponseBody Object gettransaction(@RequestParam Integer buyerId){
		Map<String, String> response = new HashMap<>();
		System.out.println("user: " + buyerId + "want to get transaction ");
		User buyer = userRepository.findById(buyerId);
		List<Transaction> list = transactionRepository.findByBuyer(buyer);
		if(list.size()>0) {
			return list;
		}
		else {
			response.put("code", "0");
			response.put("message", "you do not have any orders");
		}
		return response;
	}
	/*
	 * pay for item with coupon
	 */
	@RequestMapping(path="paycoupon")
	public @ResponseBody Map<String, String> paycoupon(@RequestParam Integer buyerId, @RequestParam Integer ItemId,  @RequestParam Integer couponid){
		Map<String, String> response = new HashMap<>();
		System.out.println("user: " + buyerId + "want to buy item" + ItemId);
		User buyer = new User();
		//find buyer by id in database
		buyer = userRepository.findById(buyerId);
		//find item by id in database
		Item item = itemRepository.findById(ItemId);
		//find seller by id in database
		User seller = userRepository.findById(item.getUserid());
		if(buyer != null && item!=null && buyer.getId() != item.getUserid() && item.getIsSold()==0) {
			Double price=item.getPrice();
			Coupon coupon=couponRepository.findById(couponid);
			if(coupon!=null){
				Double discount=coupon.getDiscount();
				price=price*(1-discount);
				if(buyer.getWallet()>price) {
					buyer.setWallet(buyer.getWallet() - price);
					seller.setWallet(seller.getWallet() + price);
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
					//delete coupon in database
					couponRepository.delete(coupon);	
					response.put("message", "Item bought successful");
				}else {
					response.put("message", "user doesnot have enough money");
				}
			}else{
				response.put("message", "Coupon does not exist");
			}
		}
		else {
			response.put("message", "error");
		}

		return response;
	}
	/*
	 * pay with coupon
	 */
	@RequestMapping(path="paywithcoupon")
	public @ResponseBody Map<String, String> paywithcoupon(@RequestParam Integer couponid,@RequestParam Integer buyerId){
		Map<String, String> response = new HashMap<>();
		System.out.println("user: " + buyerId + "want to pay with coupon");
		User buyer = new User();
		//find buyer by id in database
		buyer = userRepository.findById(buyerId);
		//find item by id in database
		List<Cart> list = (List<Cart>) cartRepository.findByUserid(buyerId);
		Coupon coupon=couponRepository.findById(couponid);
		//find seller by id in database
		for(int n=0;n<list.size();n++){
			Cart cart=list.get(n);
			Integer itemid=cart.getItemid();
			Item item=itemRepository.findById(itemid);
			User seller = userRepository.findById(item.getUserid());
			//use coupon for the first item
			if(n==0) {
				Double discount=coupon.getDiscount();
				Double price=cart.getItem().getPrice()*(1-(discount/100));
				System.out.println("discount price:"+price);
				if(buyer.getWallet()>price) {
					buyer.setWallet(buyer.getWallet() - price);
					seller.setWallet(seller.getWallet() + price);
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
					//delete coupon in database
					couponRepository.delete(coupon);	
					response.put("code", "1");
					response.put("message", "Item bought successful");
				}else {
					response.put("code", "0");
					response.put("message", "user doesnot have enough money");
				}
			}
			else {
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
			}
			cartRepository.delete(cart);
		}
		return response;
	}
}
