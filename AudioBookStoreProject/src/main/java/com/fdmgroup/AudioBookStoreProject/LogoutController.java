package com.fdmgroup.AudioBookStoreProject;



import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LogoutController {
	
	@RequestMapping(("/logoutpage"))
	public String removeLoginData(HttpSession session) {
		
		
		session.invalidate();
		return "logout";
	}
	
	

}
