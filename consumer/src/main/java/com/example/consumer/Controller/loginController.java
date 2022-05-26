package com.example.consumer.Controller;


import com.example.consumer.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class loginController {

	@Autowired
	RestTemplate restTemplate;
	//服务提供者访问前缀
	public static final String URL_PREFIX = "http://provider";

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	 public String showlogin() {
		//System.out.println("123"); 
		return "login";
	 }

	 @RequestMapping(value = "/updateClient", method = RequestMethod.GET)
	 @ResponseBody
	 public String updateClient(@RequestParam(value = "id") String id, @RequestParam(value = "password") String password, HttpServletResponse response) {
		Cookie cookie=new Cookie("userid", id);
		response.addCookie(cookie);

		User user = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+id, User.class);
		String userid=user.getUserid();
		String upassword=user.getUpassword();
		String ID="0";
		if(id.equals(userid) && password.equals(upassword))//登录判断
			if (ID.equalsIgnoreCase(user.getId())) {
				return "index";
			}
			else {
				return "index2";
			}

		else {
			return "";
		}
	}
}
