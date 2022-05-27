package com.example.provider.function;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.provider.Entity.*;
import com.example.provider.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
	@Autowired
	UserService userService;
	@Autowired
	BookService bookService;
	@Autowired
	RecordService recordService;
	@Autowired
	ClassService classService;
	@Autowired
	ViewService viewService;

	@RequestMapping(value = "/findbyUserid", method = RequestMethod.GET)
	public User findByUserid(@RequestParam("userid")String user){
		return userService.findByUserid(user);
	}

	@RequestMapping(value = "/findAllBook", method = RequestMethod.GET)
	public List<Book> findAllBook(){
		return bookService.findAll();
	}

	@RequestMapping(value = "/findAllClass",method = RequestMethod.GET)
	public List<Classific> findAllClass(){
		return classService.findAll();
	}

	@RequestMapping(value = "/findbyBookid", method = RequestMethod.GET)
	public Book findByBookid(@RequestParam(value = "bookid") String bookid){
		return bookService.findByBookid(bookid);
	}

	@RequestMapping(value = "/findbyClassid",method = RequestMethod.GET)
	public Classific findByClassid(@RequestParam(value = "classid") String classid){
		return classService.findByClassid(classid);
	}



//	@RequestMapping(value = "/index", method = RequestMethod.GET)
//	 public User showindex(@CookieValue("userid")String user, Map<String, Object> paramMap) {
//		//从数据库读取用户信息
//		User sUser=userService.findByUserid(user);
//		paramMap.put("user", sUser.getUserid());
//		paramMap.put("username", sUser.getUsername());
//		return sUser;
//	 }
	
//	 @GetMapping("/book")
//	 public String showbook(@CookieValue("userid")String user,Map<String, Object> paramMap,ModelMap map) {
//		 User sUser=userService.findByUserid(user);
//		 paramMap.put("user", sUser.getUserid());
//		 paramMap.put("username", sUser.getUsername());
//		 List<Book> books=bookService.findAll();
//		 map.addAttribute("showbooks", books);
//		 return "book";
//	 }
	 
//	 @RequestMapping(value = "/insertbook", method = RequestMethod.GET)
//	 public String showinbook(@CookieValue("userid")String user,Map<String, Object> paramMap,ModelMap map) {
//		 User sUser=userService.findByUserid(user);
//		 paramMap.put("user", sUser.getUserid());
//		 paramMap.put("username", sUser.getUsername());
//		 List<Classific> classes=classService.findAll();
//		 map.addAttribute("showclass", classes);
//		 return "insertbook";
//	 }
	 
	 @RequestMapping(value = "/deletebook",method = RequestMethod.GET)
	 public Boolean deleteBook(@RequestParam(value = "bookid") String bookid) {
		 //System.out.println(user);
		 Book book=bookService.findByBookid(bookid);
		 System.out.println(bookid);
		 if(book.isin()) {
		     recordService.deleteByBorrowidBooksid(bookid);
		     bookService.deleteByBookid(bookid);		 
	         return true;
		 }
		 else {
			return false;
		}
	 }

//	 public String bookstr;
//	 public String setbookString(String str) {
//		bookstr=str;
//		return bookstr;
//	}
//
//	 @RequestMapping(value = "/editbookinfo",method = RequestMethod.PUT)
//	 public String editBook(@RequestParam(value = "bookid")String id) {
//		 setbookString(id);
//		 //System.out.println(id);
//		 return "editbook";
//	 }
//
//	 @RequestMapping(value = "/editbook",method = RequestMethod.GET)
//	 public String showbookinfo(@CookieValue("userid") String user, Map<String, Object> paramMap) {
//		 User sUser=userService.findByUserid(user);
//		 paramMap.put("user", sUser.getUserid());
//		 paramMap.put("username", sUser.getUsername());
//		 //System.out.println(user);
//		 String id = setbookString(bookstr);
//		 Book book=bookService.findByBookid(id);
//		 //System.out.println(id);
//		 paramMap.put("bookid", id);
//		 paramMap.put("bookname", book.getBookname());
//		 paramMap.put("author", book.getAuthor());
//		 paramMap.put("publichouse", book.getPublichouse());
//		 paramMap.put("introduction", book.getIntroduction());
//
//		 //获取类型名
//		 String classString= id.substring(0, 1);
//		 //System.out.println(classString);
//		 Classific c=classService.findByClassid(classString);
//		 paramMap.put("classification", c.getClassname());
//		 return "editbook";
//	 }
	 
	 @RequestMapping(value = "/insertbook",method = RequestMethod.PUT)
	 @ResponseBody
	 public void insertBook(@RequestBody Book book) {
//		 Book book=new Book();
		 String bookid;
		 do {
        	 Random random = new Random();
        	 StringBuilder id= new StringBuilder();
        	 for (int i=0;i<6;i++)
        	 {
        	     id.append(random.nextInt(10));
        	 }
        	 //System.out.println(id);
             bookid=book.getBookid()+id;
         }while (bookService.existsByBookid(bookid));
		 book.setBookid(bookid);
		 bookService.saveAndFlush(book);
	 }
	 
	 @RequestMapping(value = "/updatebook",method = RequestMethod.PUT)
	 @ResponseBody
	 public void updateBook(@RequestBody Book book) {
		 bookService.saveAndFlush(book);
	 }
	 
	 @RequestMapping(value = "/searchbook",method = RequestMethod.GET)
	 public List<Book> searchBook(@RequestParam(value = "content") String content) {
		 //System.out.println(list);
	     return bookService.findByBooknameLikeOrAuthorLikeOrPublichouseLikeOrIntroductionLikeOrBookidLike( content, content, content, content, content);
	 }
	 
	 @RequestMapping(value = "/searchborrow",method = RequestMethod.GET)
	 public List<Record> searchBorrow(@RequestParam(value = "content") String content) {
	     return recordService.findByBorrowidUsersidLikeOrBorrowidBooksidLike(content, content);
	 }

	 @RequestMapping(value = "/findbyReturntime", method = RequestMethod.GET)
	 public List<Record> findByReturntimeisNull(){
		return recordService.findByReturntimeIsNull();
	 }

	 @RequestMapping(value = "/findallRecord",method = RequestMethod.GET)
	 public List<Record> findAllRecord(){
		return recordService.findAll();
	 }

	 @RequestMapping(value = "/borrow",method = RequestMethod.GET)
	 public List<Record> showborrow() {
		 List<Record> list=recordService.findByReturntimeIsNull();
		 Timestamp ts=new Timestamp(new Date().getTime());
		 Date now=new Date(ts.getTime());
		 for (Record record : list) {
			 // System.out.println(list.get(i).getBorrowtime());
			 if (record.getShouldtime().before(ts)) {
				 Date should = new Date(record.getShouldtime().getTime());
				 long day = (now.getTime() - should.getTime()) / (24 * 60 * 60 * 1000);
				 int fine = (int) (day * 0.5);
				 record.setFine(fine);
				 recordService.saveAndFlush(record);
			 }
		 }

		 return recordService.findAll();
	 }
	 
	 @RequestMapping(value = "/deleteborrow",method = RequestMethod.GET)
	 @ResponseBody 
	 public String deleteBorrow(@RequestParam(value = "userid") String userid, @RequestParam(value = "bookid") String bookid) {
		 //System.out.println(user);
		 Borrowid idsId=new Borrowid();
		 idsId.setBooksid(bookid);
		 idsId.setUsersid(userid);
		 if(recordService.findByBorrowid(idsId).getReturntime()==null)
			 return "";
		 else{
			 recordService.deleteByBorrowid(idsId);
		 }
	     return "borrow";
	 }
	 
	 @RequestMapping(value="/returnfine",method = RequestMethod.GET)
	// @ResponseBody
	 public String returnfine(@RequestParam(value = "userid") String userid,@RequestParam(value = "bookid") String bookid) {
		 Borrowid idsId=new Borrowid();
		 idsId.setBooksid(bookid);
		 idsId.setUsersid(userid);
		 Record record=recordService.findByBorrowid(idsId);
		 if(record.getFine()>0) {
		     record.setFine((float) 0.0);
		     recordService.saveAndFlush(record);
		 }
	     return "borrow::borrow";
	 }
	 
	 @RequestMapping(value = "/borrowbook",method = RequestMethod.PUT)
	 @ResponseBody
	 public void borrowBook(@RequestParam(value = "usersid")String usersid,@RequestParam(value = "booksid")String booksid) {
		 
		 Borrowid sid=new Borrowid();
		 Book book=bookService.findByBookid(booksid);
		 User user=userService.findByUserid(usersid);
		 List<Record> list=recordService.findByBorrowidUsersid(usersid);
		 float temp=(float) 0.0;
		 for (Record value : list) {
			 temp += value.getFine();
		 }
		 if (!book.isin() || user.getBorrownum()>=10 || temp>0) {
			 System.out.println(book.isin());
			 user.setIscan(false);
			 return;
		}
		 else if(user.getIscan()){
//			 System.out.println(booksid);
//			 System.out.println(usersid);
//			 System.out.println(sid);	
			
			//保存日期和主键
			Timestamp ts=new Timestamp(new Date().getTime());
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(ts);
			sid.setBooksid(booksid);
			sid.setUsersid(usersid);
			//record.setBorrowid(sid);
			Record record=new Record();
			if(recordService.existByBorrowid(sid)) {
				record=recordService.findByBorrowid(sid);
			}
			else 
				record.setBorrowid(sid);
			calendar.add(Calendar.DATE, 30);//加30天
			Timestamp shouldTime=new Timestamp(calendar.getTimeInMillis());
			
			record.setBorrowtime(ts);
			record.setShouldTime(shouldTime);
			//record.setReturntime(null);
			record.setFine(0);
			
			recordService.saveAndFlush(record);
			book.setin(false);
			bookService.saveAndFlush(book);
			if(user.getBorrownum()<10) {
			    int num=user.getBorrownum()+1;
			    user.setBorrownum(num);
			    if(num==10)
			    	user.setIscan(false);
			}
			userService.saveAndFlush(user);
			
		}
//		 return;
	 }
	 
	 @RequestMapping(value = "/returnbook",method = RequestMethod.GET)
	 @ResponseBody
	 public String returnBook(@RequestParam(value = "usersid")String usersid,@RequestParam(value = "booksid")String booksid) {
		 Record record=new Record();
		 //Borrowid sid=new Borrowid();
		 Book book=bookService.findByBookid(booksid);
		 User user=userService.findByUserid(usersid);
		 List<Record> list=recordService.findByReturntimeIsNull();
		 for (Record value : list) {
			 String useridString = value.getBorrowid().getUsersid();
			 String bookidString = value.getBorrowid().getBooksid();
			 if (usersid.equals(useridString) && booksid.equals(bookidString)) {
				 record = value;
			 }
		 }
		// System.out.println(book.isin());
		 if (book.isin()) 
			return "1";	
		 else if(record.getFine()>0)
			 return "2";
		 else {
			//保存日期和主键
			Timestamp ts=new Timestamp(new Date().getTime());
			record.setReturntime(ts);
			recordService.saveAndFlush(record);
			book.setin(true);
			bookService.saveAndFlush(book);
			
			int num=user.getBorrownum()-1;
			if(num<=9)
				user.setIscan(true);
			user.setBorrownum(num);
				
			userService.saveAndFlush(user);
			return "borrow";
		}
		 
	 }
	 
//	 @GetMapping("/reader")
//	 public String Readers(@CookieValue("userid")String user,ModelMap map,Map<String, Object> paramMap) {
//		 User sUser=userService.findByUserid(user);
//		 paramMap.put("user", sUser.getUserid());
//		 paramMap.put("username", sUser.getUsername());
//		 List<User> list=userService.findByIdLike("1");
//		 map.addAttribute("list", list);
//	     return "reader";
//	 }
	@GetMapping("/findbyIdlike")
	public List<User> findByIdLike(@RequestParam(value = "userid") String userid){
		return userService.findByIdLike("1");
	}

	 @RequestMapping(value = "/deletereader",method = RequestMethod.GET)
	 @ResponseBody
	 public String deleteReader(@RequestParam(value = "userid") String userid) {
		 //System.out.println(user);
		 List<Record> record=recordService.findByBorrowidUsersid(userid);
		 float temp=0;
		 for (Record value : record) {
			 temp += value.getFine();
		 }
		 User user=userService.findByUserid(userid);
		 if(temp>0 || user.getBorrownum()>0) return "";
		 else {
		     recordService.deleteByBorrowidUsersid(userid);
		     userService.deleteByUserid(userid); 		 
	         return "reader";
		 }
	 }
	 
	 @RequestMapping(value = "/searchreader",method = RequestMethod.GET)
	 public List<User> searchReader(@RequestParam(value = "content") String content) {
		 List<User> list=userService.findByUsernameLikeOrPhoneLikeOrUseridLike(content, content, content);
		 List<User> users= new ArrayList<>();
		 String idString="1";
		 for (User user : list) {
			 if (idString.equals(user.getId())) {
				 users.add(user);
			 }
		 }
	     return users;
	 }

	 @RequestMapping(value = "/insertreader",method = RequestMethod.PUT)
	 @ResponseBody
	 public void insertReader(@RequestBody User user){
         //判断电话号码位数
         String userid;
         do {
        	 Random random = new Random();
        	 StringBuilder id= new StringBuilder();
        	 for (int i=0;i<6;i++)
        	 {
        	     id.append(random.nextInt(10));
        	 }
        	 //System.out.println(id);
             userid="1"+id;
         }while (userService.existsByUserid(userid));
         //System.out.println(userid);
         user.setUserid(userid);
         userService.saveAndFlush(user);
	 }
	 
//	 @RequestMapping(value = "/editreaderinfo",method = RequestMethod.PUT)
//	 public String editReader(@RequestParam(value = "userid")String id) {
//		 getString(id);
//		 return "editreader";
//	 }
//
//	 public String userstring;
//	 public String getString(String str) {
//		 userstring=str;
//		 return userstring;
//	}
//
//	 @RequestMapping(value = "/editreader",method = RequestMethod.GET)
//	 public String showread(@CookieValue("userid") String user, Map<String, Object> paramMap) {
//		 User sUser=userService.findByUserid(user);
//		 paramMap.put("user", sUser.getUserid());
//		 paramMap.put("username", sUser.getUsername());
//		 //System.out.println(user);
//		 String id = getString(userstring);
//		 User s=userService.findByUserid(id);
//		 //System.out.println(id);
//		 paramMap.put("userid", id);
//		 paramMap.put("name", s.getUsername());
//		 paramMap.put("phone", s.getPhone());
//		 return "editreader";
//	 }
	 
	 @RequestMapping(value = "/updatereader",method = RequestMethod.GET)
	 @ResponseBody
	 public String updateReader(@RequestParam(value = "readername")String name,@RequestParam(value = "readerphone")String phone,@RequestParam(value = "readerid")String id) {
		 //System.out.println(id);
		 User sUser=userService.findByUserid(id);
		 if(phone.length()!=11) {
			 return "";
		 }
		 else {
		     sUser.setUsername(name);
		     sUser.setPhone(phone);
		     userService.saveAndFlush(sUser);
		     return "editreader";
		 }
	 }
	 
	 @RequestMapping(value = "/upreaderpass",method = RequestMethod.GET)
	 @ResponseBody
	 public String upReaderpass(@RequestParam(value = "id")String id) {
		 User sUser=userService.findByUserid(id);
		 sUser.setUpassword("123456");
		 userService.saveAndFlush(sUser);
		 return "editreader";
	 }
	 
//	 @RequestMapping(value = "/selfinfo",method = RequestMethod.GET)
//	 public String showselfinfo(@CookieValue("userid")String user,Map<String, Object> paramMap) {
//		 User sUser=userService.findByUserid(user);
//		 paramMap.put("user", sUser.getUserid());
//		 paramMap.put("username", sUser.getUsername());
//		 paramMap.put("userid", sUser.getUserid());
//		 paramMap.put("usersname", sUser.getUsername());
//		 paramMap.put("phone", sUser.getPhone());
//		 paramMap.put("iscan", sUser.getIscan());
//		 paramMap.put("password", sUser.getUpassword());
//		 return "selfinfo";
//	 }
	 
	 @RequestMapping(value = "/updateinfo",method = RequestMethod.GET)
	 @ResponseBody
	 public String updateInfo(@RequestParam(value = "username")String username,@RequestParam(value = "userid")String userid,@RequestParam(value = "phone")String phone) {
		 User s=userService.findByUserid(userid);
		 if(phone.length()!=11) {
			 return "";
		 }
		 else {
		     s.setUsername(username);
		     s.setPhone(phone);
		     userService.saveAndFlush(s);
		     return "selfinfo";
		 }
	 }
	 
	 @RequestMapping(value = "/selfchange",method = RequestMethod.GET)
	 public String showchangepass(@RequestParam("userid")String user,Map<String, Object> paramMap) {
		 User sUser=userService.findByUserid(user);
		 //System.out.println(user);
		 paramMap.put("userid", sUser.getUserid());
		 paramMap.put("user", sUser.getUserid());
		 paramMap.put("username", sUser.getUsername());
		 
		 return "selfchange";		 
	 }
	 
	 @RequestMapping(value = "/updatepass",method = RequestMethod.GET)
	 public String updatepass(@RequestParam(value = "userid") String userid, @RequestParam(value = "oldpass") String oldpass, @RequestParam(value = "newpass") String newpass) {
		 User s=userService.findByUserid(userid);
		 if (oldpass.equals(s.getUpassword())) {
			s.setUpassword(newpass);
			userService.saveAndFlush(s);
			//System.out.println("ddd");
			return "selfchange";
		}
		 else {
			return "";
		}
		 
	 }

	 @RequestMapping(value = "/showview", method = RequestMethod.GET)
	public List<View> showRecord(@RequestParam(value = "userid") String userid){
		return viewService.findByRecordidUsersid(userid);
	 }
}
