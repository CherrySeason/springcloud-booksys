package com.example.consumer.Controller;


import com.example.consumer.Entity.*;
import org.bouncycastle.cert.ocsp.UnknownStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class AdminController {

	@Autowired
	RestTemplate restTemplate;
	//服务提供者访问前缀
	public static final String URL_PREFIX = "http://provider";

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	 public String showindex(@CookieValue("userid")String user,Map<String, Object> paramMap) {
        User sUser = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+user, User.class);
        assert sUser != null;
        paramMap.put("user", sUser.getUserid());
        paramMap.put("username", sUser.getUsername());
		return "index";

	 }

	 @GetMapping("/book")
	 public String showbook(@CookieValue("userid")String user,Map<String, Object> paramMap,ModelMap map) {
         User sUser = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+user, User.class);
//         User sUser=userService.findByUserid(user);
         assert sUser != null;
         paramMap.put("user", sUser.getUserid());
         paramMap.put("username", sUser.getUsername());
//         List<Book> books=bookService.findAll();
         List<Book> books = restTemplate.getForObject(URL_PREFIX+"/findAllBook", List.class);
         map.addAttribute("showbooks", books);
         return "book";
	 }

	 @RequestMapping(value = "/insertbook", method = RequestMethod.GET)
	 public String showinbook(@CookieValue("userid")String user,Map<String, Object> paramMap,ModelMap map) {
         User sUser = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+user, User.class);
         assert sUser != null;
         paramMap.put("user", sUser.getUserid());
         paramMap.put("username", sUser.getUsername());
         List classes=restTemplate.getForObject(URL_PREFIX+"/findAllClass", List.class);
         map.addAttribute("showclass", classes);
         return "insertbook";
	 }

	 @RequestMapping(value = "/deletebook",method = RequestMethod.PUT)
	 @ResponseBody
	 public String deleteBook(@RequestParam(value = "bookid") String bookid) {
		 //System.out.println(user);
//		 Book book=bookService.findByBookid(bookid);
		 Boolean result = restTemplate.getForObject(URL_PREFIX+"/deletebook?bookid="+bookid, Boolean.class);
		 if(Boolean.TRUE.equals(result)) {
			 return "book";
		 }
		 else {
			 return "";
		 }
	 }
//
	 public String bookstr;
	 public String setbookString(String str) {
		bookstr=str;
		return bookstr;
	}

	 @RequestMapping(value = "/editbookinfo",method = RequestMethod.PUT)
	 public String editBook(@RequestParam(value = "bookid")String id) {
		 setbookString(id);
//		 System.out.println(id);
		 return "editbook";
	 }
//
	 @RequestMapping(value = "/editbook",method = RequestMethod.GET)
	 public String showbookinfo(@CookieValue("userid") String user, Map<String, Object> paramMap) {
         User sUser = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+user, User.class);
		 if(sUser!=null){
			 paramMap.put("user", sUser.getUserid());
			 paramMap.put("username", sUser.getUsername());

			 String id = setbookString(bookstr);
//			 System.out.println(bookstr);
			 Book book = restTemplate.getForObject(URL_PREFIX+"/findbyBookid?bookid="+id, Book.class);
			 paramMap.put("bookid", id);
			 assert book != null;
			 paramMap.put("bookname", book.getBookname());
			 paramMap.put("author", book.getAuthor());
			 paramMap.put("publichouse", book.getPublichouse());
			 paramMap.put("introduction", book.getIntroduction());

			 //获取类型名
			 String classString= id.substring(0, 1);
			 //System.out.println(classString);
//			 Classific c=classService.findByClassid(classString);
			 Classific c = restTemplate.getForObject(URL_PREFIX+"/findbyClassid?classid="+classString, Classific.class);
			 assert c != null;
			 paramMap.put("classification", c.getClassname());
		 }

		 return "editbook";
	 }

	 @RequestMapping(value = "/insertbooks",method = RequestMethod.PUT)
	 @ResponseBody
	 public String insertBook(@RequestParam(value = "bookname")String bookname,@RequestParam(value = "author")String author,@RequestParam(value = "publichouse")String publichouse,@RequestParam(value = "introduction")String introduction,@RequestParam(value = "classification")String classification) {
		 Book book=new Book();
		 book.setAuthor(author);
		 book.setBookname(bookname);
		 book.setIntroduction(introduction);
		 book.setPublichouse(publichouse);
		 book.setin(true);
		 book.setBookid(classification);
		 restTemplate.put(URL_PREFIX+"/insertbook", book);
		 return "insertbook";
	 }

	 @RequestMapping(value = "/updatebook",method = RequestMethod.PUT)
	 @ResponseBody
	 public String updateBook(@RequestParam(value = "bookname")String name,@RequestParam(value = "bookid")String id,@RequestParam(value = "author")String author,@RequestParam(value = "publichouse")String publichouse,@RequestParam(value = "introduction")String introduction) {
//		 Book book=bookService.findByBookid(id);
		 Book book = restTemplate.getForObject(URL_PREFIX+"/findbyBookid?bookid="+id, Book.class);
		 assert book != null;
		 book.setBookname(name);
		 book.setAuthor(author);
		 book.setIntroduction(introduction);
		 book.setPublichouse(publichouse);
//		 bookService.saveAndFlush(book);
		 restTemplate.put(URL_PREFIX+"/updatebook", book);
		 return "editbook";
	 }

	 @RequestMapping(value = "/searchbook",method = RequestMethod.POST)
	 public String searchBook(Model map,@RequestParam(value = "content") String content) {
		 List list = restTemplate.getForObject(URL_PREFIX+"/searchbook?content="+content, List.class);
		 map.addAttribute("showbooks", list);
		 //System.out.println(list);
	     return "book::searchbook";
	 }

	 @RequestMapping(value = "/searchborrow",method = RequestMethod.POST)
	 public String searchBorrow(Model map,@RequestParam(value = "content") String content) {
		 List<Record> list = restTemplate.getForObject(URL_PREFIX+"/searchborrow?content="+content, List.class);
		 map.addAttribute("showborrow", list);
		 //System.out.println(list);
	     return "borrow::borrow";
	 }

	@RequestMapping(value ="/borrow",method = RequestMethod.GET)
	 public String showborrow(@CookieValue("userid")String user,Map<String, Object> paramMap,ModelMap map) {
		 User sUser = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+user, User.class);
		 assert sUser != null;
		 paramMap.put("user", sUser.getUserid());
		 paramMap.put("username", sUser.getUsername());
		 List<Record> newlist = restTemplate.getForObject(URL_PREFIX+"/borrow", List.class);
		 map.addAttribute("showborrow", newlist);
		 return "borrow";
	 }

	 @RequestMapping(value = "/deleteborrow",method = RequestMethod.PUT)
	 @ResponseBody
	 public String deleteBorrow(@RequestParam(value = "userid") String userid, @RequestParam(value = "bookid") String bookid) {
	     return restTemplate.getForObject(URL_PREFIX+"/deleteborrow?userid="+userid+"&bookid="+bookid, String.class);
	 }

	 @RequestMapping(value="/editborrow",method = RequestMethod.POST)
	 public String returnfine(Model map,@RequestParam(value = "userid") String userid,@RequestParam(value = "bookid") String bookid) {
		 restTemplate.getForObject(URL_PREFIX+"/returnfine?userid="+userid+"&bookid="+bookid, String.class);
		 List<Record> newlist=restTemplate.getForObject(URL_PREFIX+"/findallRecord", List.class);
		 map.addAttribute("showborrow", newlist);
	     return "borrow::borrow";
	 }

	 @RequestMapping(value = "/borrowbook",method = RequestMethod.PUT)
	 @ResponseBody
	 public String borrowBook(@RequestParam(value = "usersid")String usersid,@RequestParam(value = "booksid")String booksid) {
		 restTemplate.put(URL_PREFIX+"/borrowbook?usersid="+usersid+"&booksid="+booksid,null);
		 return "borrow";
	 }

	 @RequestMapping(value = "/returnbook",method = RequestMethod.PUT)
	 @ResponseBody
	 public String returnBook(@RequestParam(value = "usersid")String usersid,@RequestParam(value = "booksid")String booksid) {
		 return restTemplate.getForObject(URL_PREFIX+"/returnbook?usersid="+usersid+"&booksid="+booksid, String.class);
	 }

	 @GetMapping("/reader")
	 public String Readers(@CookieValue("userid")String user,ModelMap map,Map<String, Object> paramMap) {
		 User sUser = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+user, User.class);
		 assert sUser != null;
		 paramMap.put("user", sUser.getUserid());
		 paramMap.put("username", sUser.getUsername());
		 List list=restTemplate.getForObject(URL_PREFIX+"/findbyIdlike?userid="+user, List.class);
		 map.addAttribute("list", list);
	     return "reader";
	 }
	 @RequestMapping(value = "/deletereader",method = RequestMethod.PUT)
	 @ResponseBody
	 public String deleteReader(@RequestParam(value = "userid") String userid) {
		 return restTemplate.getForObject(URL_PREFIX+"/deletereader?userid="+userid,String.class);
	 }

	 @RequestMapping(value = "/searchreader",method = RequestMethod.POST)
	 public String searchReader(Model map,@RequestParam(value = "content") String content) {
		 List users=restTemplate.getForObject(URL_PREFIX+"/searchreader?content="+content, List.class);
		 map.addAttribute("list", users);
	     return "reader::reader";
	 }

	 @RequestMapping(value = "/insertreader",method = RequestMethod.PUT)
	 @ResponseBody
	 public String insertReader(@RequestParam(value = "username") String username, @RequestParam(value = "userphone") String userphone) {
         //判断电话号码位数
		 if(userphone.length()!=11)
			 return "";
		 User iUser=new User();
         iUser.setId("1");
         iUser.setIscan(true);
         iUser.setUpassword("123456");
         iUser.setUsername(username);
         iUser.setPhone(userphone);
		 restTemplate.put(URL_PREFIX+"/insertreader", iUser);
	     return "reader";
	 }

	 @RequestMapping(value = "/editreaderinfo",method = RequestMethod.PUT)
	 @ResponseBody
	 public String editReader(@RequestParam(value = "userid")String id) {
		 getString(id);
		 return "editreader";
	 }

	 public String userstring;
	 public String getString(String str) {
		 userstring=str;
		 return userstring;
	}

	 @RequestMapping(value = "/editreader",method = RequestMethod.GET)
	 public String showread(@CookieValue("userid") String user, Map<String, Object> paramMap) {
		 User sUser = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+user, User.class);
		 assert sUser != null;
		 paramMap.put("user", sUser.getUserid());
		 paramMap.put("username", sUser.getUsername());
		 String id = getString(userstring);
		 User s = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+id, User.class);
		 //System.out.println(id);
		 paramMap.put("userid", id);
		 assert s != null;
		 paramMap.put("name", s.getUsername());
		 paramMap.put("phone", s.getPhone());
		 return "editreader";
	 }

	 @RequestMapping(value = "/updatereader",method = RequestMethod.PUT)
	 @ResponseBody
	 public String updateReader(@RequestParam(value = "readername")String name,@RequestParam(value = "readerphone")String phone,@RequestParam(value = "readerid")String id) {
		 return restTemplate.getForObject(URL_PREFIX+"/updatereader?readername="+name+"&readerphone="+phone+"&readerid="+id, String.class);
	 }

	 @RequestMapping(value = "/upreaderpass",method = RequestMethod.PUT)
	 @ResponseBody
	 public String upReaderpass(@RequestParam(value = "id")String id) {
		 return restTemplate.getForObject(URL_PREFIX+"/upreaderpass?id="+id,String.class);
	 }

	 @RequestMapping(value = "/selfinfo",method = RequestMethod.GET)
	 public String showselfinfo(@CookieValue("userid")String user,Map<String, Object> paramMap) {
//		 User sUser=userService.findByUserid(user);
		 User sUser = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+user, User.class);
		 assert sUser != null;
		 paramMap.put("user", sUser.getUserid());
		 paramMap.put("username", sUser.getUsername());
		 paramMap.put("userid", sUser.getUserid());
		 paramMap.put("usersname", sUser.getUsername());
		 paramMap.put("phone", sUser.getPhone());
		 paramMap.put("iscan", sUser.getIscan());
		 paramMap.put("password", sUser.getUpassword());
		 return "selfinfo";
	 }

	 @RequestMapping(value = "/updateinfo",method = RequestMethod.PUT)
	 @ResponseBody
	 public String updateInfo(@RequestParam(value = "username")String username,@RequestParam(value = "userid")String userid,@RequestParam(value = "phone")String phone) {
		 return restTemplate.getForObject(URL_PREFIX+"/updateinfo?username="+username+"&userid="+userid+"&phone="+phone,String.class);
	 }

	 @RequestMapping(value = "/selfchange",method = RequestMethod.GET)
	 public String showchangepass(@CookieValue("userid")String user,Map<String, Object> paramMap) {
		 User sUser = restTemplate.getForObject(URL_PREFIX+"/findbyUserid?userid="+user, User.class);
		 assert sUser != null;
		 paramMap.put("userid", sUser.getUserid());
		 paramMap.put("user", sUser.getUserid());
		 paramMap.put("username", sUser.getUsername());
		 return "selfchange";
	 }

	 @RequestMapping(value = "/updatepass",method = RequestMethod.PUT)
	 @ResponseBody
	 public String updatepass(@RequestParam(value = "userid") String userid, @RequestParam(value = "oldpass") String oldpass, @RequestParam(value = "newpass") String newpass) {
		 return restTemplate.getForObject(URL_PREFIX+"/updatepass?userid="+userid+"&oldpass="+oldpass+"&newpass="+newpass,String.class);
	 }
}
