package com.project.application.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.project.application.model.Message;

/** 
* @author Yuhang Xie 
* @version Apr 18, 2019 3:28:44 PM 
*/

@Controller
public class MessageController {
	   @MessageMapping("/hello")
	   @SendTo("/topic/greetings")
	    public Object greeting(Message message) throws Exception {
	        Thread.sleep(1000); // simulated delay
	        return "Hello !";
	    }
}
