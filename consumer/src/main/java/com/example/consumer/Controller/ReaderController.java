package com.example.consumer.Controller;


import com.example.consumer.Entity.Book;
import com.example.consumer.Entity.User;
import com.example.consumer.Entity.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
public class ReaderController {

	@Autowired
	RestTemplate restTemplate;
	//服务提供者访问前缀
	public static final String URL_PREFIX = "http://provider";

	@RequestMapping(value = "/index2", method = RequestMethod.GET)
	 public String showindex(@CookieValue("userid")String user,Map<String, Object> paramMap) {
        User sUser = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+user, User.class);
        assert sUser != null;
        paramMap.put("user", sUser.getUserid());
		paramMap.put("username", sUser.getUsername());
		return "index2";
	 }

	@RequestMapping(value = "/findbook",method = RequestMethod.GET)
	 public String showBook(@CookieValue("userid")String user,Map<String, Object> paramMap) {
        User sUser = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+user, User.class);
        assert sUser != null;
        paramMap.put("user", sUser.getUserid());
		paramMap.put("username", sUser.getUsername());
		return "findbook";
	}

	@RequestMapping(value = "/findbook",method = RequestMethod.POST)
	 public String findBook(Model map,@RequestParam(value = "content") String content) {
		 List<Book> list= restTemplate.getForObject(URL_PREFIX+"/searchbook?content="+content, List.class);
		 map.addAttribute("showbooks", list);
		 return "findbook::findbook";
	}

	@RequestMapping(value = "/readerinfo",method = RequestMethod.GET)
	 public String showInfo(@CookieValue("userid")String user,Map<String, Object> paramMap) {
        User sUser = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+user, User.class);
        assert sUser != null;
        paramMap.put("user", sUser.getUserid());
		paramMap.put("username", sUser.getUsername());
		paramMap.put("userid", sUser.getUserid());
		paramMap.put("name", sUser.getUsername());
		paramMap.put("phone", sUser.getPhone());
		paramMap.put("iscan", sUser.getIscan());
		return "readerinfo";
	}

	 @RequestMapping(value = "/updatereaderinfo",method = RequestMethod.PUT)
	 @ResponseBody
	 public String updateReader(@RequestParam(value = "userid")String userid,@RequestParam(value = "username")String username,@RequestParam(value = "phone")String phone) {
         restTemplate.getForObject(URL_PREFIX+"/updatereader?readername="+username+"&readerphone="+phone+"&readerid="+userid, String.class);
		 return "readerinfo";
	 }

	 @RequestMapping(value = "/readerpass",method = RequestMethod.GET)
	 public String showchangepass(@CookieValue("userid")String user,Map<String, Object> paramMap) {
         User sUser = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+user, User.class);
		 //System.out.println(user);
         assert sUser != null;
         paramMap.put("userid", sUser.getUserid());
		 paramMap.put("user", sUser.getUserid());
		 paramMap.put("username", sUser.getUsername());

		 return "readerpass";
	 }

	 @RequestMapping(value = "/readerpass",method = RequestMethod.PUT)
	 public String updatepass(@RequestParam(value = "userid")String userid,Map<String, Object> paramMap,@RequestParam(value = "oldpass")String oldpass,@RequestParam(value = "newpass")String newpass) {
         return restTemplate.getForObject(URL_PREFIX+"/upreaderpass?id="+userid,String.class);

	 }

	 @GetMapping("/record")
	 public String showRecord(@CookieValue("userid")String user,Map<String, Object> paramMap,Model map) {
         User sUser = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+user, User.class);
         assert sUser != null;
         paramMap.put("user", sUser.getUserid());
		 paramMap.put("username", sUser.getUsername());

		 List<View> list=restTemplate.getForObject(URL_PREFIX+"/showview?userid="+user, List.class);
		 //System.out.println(list.get(0).getBookname());
		 map.addAttribute("record", list);
		 return "record";
	 }
}
