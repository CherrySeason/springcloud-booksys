package com.example.provider.function;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.provider.Entity.User;
import com.example.provider.Service.UserService;

@Controller
public class loginController {
	@Autowired
	UserService userService;
//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	 public String showlogin() {
//		//System.out.println("123");
//		return "login";
//	 }

	 @RequestMapping(value = "/updateClient",method = RequestMethod.GET)
	 public String updateClient(@RequestParam(value = "id") String id, @RequestParam(value = "password") String password) {
		//System.out.println(id);
		//……数据库根据user得到密码
        User user=userService.findByUserid(id);
        String userid=user.getUserid();
        String upassword=user.getUpassword();
        String ID="0";
		if(id.equals(userid) && password.equals(upassword))//登录判断
			if (ID.equalsIgnoreCase(user.getId())) {
				return "/index";
			}
		    else {
				return "/index2";
			}

		else {
			return "";
		}
	 }
}
