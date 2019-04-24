package com.project.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.project.application.dao.UserRepository;
import com.project.application.model.Message;
import com.project.application.model.User;

/** 
* @author Yuhang Xie 
* @version Apr 18, 2019 3:28:44 PM 
*/

@Controller
public class MessageController {
	
	  @Autowired
	  private SimpMessagingTemplate template;
	  @Autowired
	  private UserRepository userRepository;
	
	   @MessageMapping("/hello")
	   @SendTo("/topic/{id}")
	    public Object greeting(Message message) throws Exception {
	        System.out.println("this is topic hello:"+message);
	        return message;
	    }
	   
	   
//	   @MessageMapping("/send")
//	   @SendTo("/topic/hello")
//	    public Object send(Message message) throws Exception {
//	        System.out.println("this is send:"+message.getMessage());
//	        return "send say Hello !";
//	    }
	   
	   @MessageMapping("/chat")
	   public void handleChat(Message message) {
		   System.out.println("sender:"+message.getSenderId() + " to receiver:"+message.getReceiverId());
		   System.out.println("say:"+message.getMessage());
		   int sendId = message.getSenderId();
		   int receiverId =message.getReceiverId();
		   String content = message.getMessage();
		   if(message.getSenderId()==0) {
			   User admin= new User();
			   message.setSender("root");
			   message.setReceiver("none");
		   }
		   else {
		       try {
		    	   User user = userRepository.findById(message.getSenderId());
		    	   message.setSender(user.getName());
		    	   User receiver = userRepository.findById(message.getReceiverId());
		    	   message.setReceiver(receiver.getName());
	       }catch(Exception e) {
	    	   
	       }
		   }

		   template.convertAndSend("/topic/"+message.getReceiverId(),message);
		   
	   }
	   
}
