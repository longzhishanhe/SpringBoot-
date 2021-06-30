package com.school.market.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.school.market.domain.Goods;
import com.school.market.domain.Transaction;
import com.school.market.domain.User;
import com.school.market.domain.UserGoods;
import com.school.market.service.CommodityService;
import com.school.market.service.ShopCarService;
import com.school.market.service.TransactionService;
import com.school.market.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommodityService commodityService;
	
	@Autowired
	private ShopCarService shopCarService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private DefaultKaptcha defaultKaptcha;
	
	@RequestMapping(value="/register",method = RequestMethod.POST)
	public int registerUser(HttpServletRequest request, HttpServletResponse response) {
		//判断验证码是否正确
		String captchaId = (String) request.getSession().getAttribute("vrifyCode");  
		 String parameter = request.getParameter("vrifyCode");
		 
		 System.out.println("Session  vrifyCode "+captchaId+" form vrifyCode "+parameter);
		 
		 if (!captchaId.equals(parameter)) {
			 return 5;
		}
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String phone=request.getParameter("phone");
		int sex=Integer.valueOf(request.getParameter("sex"));
		String account=request.getParameter("account");
		String email=request.getParameter("email");
		String city=request.getParameter("city");
		
		int num=userService.registerUser(username, password, phone, sex, email, city, account);
		
		return num;
	}
	
	/**
	 * 获取验证码图片
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @throws Exception
	 */
	@RequestMapping("/defaultKaptcha")
	public void defaultKaptcha(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception{
		 	byte[] captchaChallengeAsJpeg = null;  
	         ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();  
	         try {  
	         //生产验证码字符串并保存到session中
	         String createText = defaultKaptcha.createText();
	         httpServletRequest.getSession().setAttribute("vrifyCode", createText);
	         //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
             BufferedImage challenge = defaultKaptcha.createImage(createText);
             ImageIO.write(challenge, "jpg", jpegOutputStream);
	         } catch (IllegalArgumentException e) {  
	             httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);  
	             return;  
	         } 
	   
	         //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
	         captchaChallengeAsJpeg = jpegOutputStream.toByteArray();  
	         httpServletResponse.setHeader("Cache-Control", "no-store");  
	         httpServletResponse.setHeader("Pragma", "no-cache");  
	         httpServletResponse.setDateHeader("Expires", 0);  
	         httpServletResponse.setContentType("image/jpeg");  
	         ServletOutputStream responseOutputStream =  
	                 httpServletResponse.getOutputStream();  
	         responseOutputStream.write(captchaChallengeAsJpeg);  
	         responseOutputStream.flush();  
	         responseOutputStream.close();  
	}
	

	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @return 登录成功返回"ok"
	 */
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		System.out.println(username+"-----"+password);
		
		if(username!=null&&password!=null) {
			User user=userService.login(username, password);
			
			if(user!=null) {
				if(user.getStatus()==0) {
					HttpSession session = request.getSession();
					session.setAttribute("user", user);
					return "ok";
				}
			}
		}
		return "you are faile";
	}
	
	/**
	 * 获取所有商品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/AllCommodity",method = RequestMethod.GET)
	public List<Goods> showAllCommodity(HttpServletRequest request, HttpServletResponse response){
		return this.commodityService.showAllGoods();
	}
	
	//获取当前用户信息
	@RequestMapping(value="/me",method = RequestMethod.GET)
	public User showUser(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		if(user!=null) {
			return user;
		}else {
			response.setStatus(403);
			return null;
		}
	}
	
	//返回购物车的商品
	@RequestMapping(value="/shopCar",method = RequestMethod.GET)
	public List<UserGoods> showShopCar(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		
		if(user!=null) {
			return shopCarService.userGoods(user.getId());
		}else {
			response.setStatus(403);
			return null;
		}
	}
	
	//加购物车
	@RequestMapping(value="/addShopCar",method = RequestMethod.POST)
	public int addShopCar(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		
		if(user!=null) {
			int commodityId=Integer.valueOf(request.getParameter("commodityId"));
			Goods goods=commodityService.showOneGoods(commodityId);
			if(goods.getNumber()>0) {
				commodityService.regulationGoodsNumber(goods.getId(), -1);
				int userId=user.getId();
				return this.userService.addShopCar(userId, commodityId);
			}
			return 0;
		}
		response.setStatus(403);
		return 0;

	}
	//移除购物车
	@RequestMapping(value="/removeShopCar",method = RequestMethod.POST)
	public int removeShopCar(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		
		if(user!=null) {
			int shopCarId=Integer.valueOf(request.getParameter("shopCarId"));
			
			UserGoods goods=this.shopCarService.findIdGoods(shopCarId);
			commodityService.regulationGoodsNumber(goods.getId(), 1);
			
			return this.shopCarService.removeOneShopCar(shopCarId);
		}
		response.setStatus(403);
		return 0;

	}
	
	
	//结算
	@RequestMapping(value="/buy",method = RequestMethod.POST)
	public int buy(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		
		
		
		if(user!=null) {
			int id=user.getId();
			System.out.println("id: "+id);
			int num=this.userService.buy(id);
			user=userService.user(user.getId());
			session.setAttribute("user", user);
			
			
			return num;
		}else {
			response.setStatus(403);
			return 0;
		}
	}
	
	//用户获取交易表单
	@RequestMapping(value="/transaction",method = RequestMethod.GET)
	public List<Transaction> showTransaction(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		
		if(user!=null) {
			String show=request.getParameter("show");
			List<Transaction> t=new ArrayList<Transaction>();
			
			if(show.equals("user1")) {
				List<Transaction> t1=transactionService.userTransaction(user.getId(), 0);
				List<Transaction> t2=transactionService.userTransaction(user.getId(), 1);
				t.addAll(t1);
				t.addAll(t2);
			}else if(show.equals("user2")) {
				List<Transaction> t1=transactionService.userTransaction(user.getId(), 2);
				t.addAll(t1);
			}
			
			return t;
		}else {
			response.setStatus(403);
			return null;
		}
	}
	
	//确认收货
  	@RequestMapping(value="/transactionStatus",method = RequestMethod.PUT)
	public int transactionStatus(HttpServletRequest request, HttpServletResponse response) {
    	//判断权限
    	HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		if(user!=null) {
			int id=Integer.valueOf(request.getParameter("id"));
			int status=Integer.valueOf(request.getParameter("status"));
			
			return transactionService.modificationTransactionStatus(id, status);
		}
    	return 0;
    }
  	

  	/**
	 * 用户充值
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/addMoney",method = RequestMethod.PUT)
	public int addMoney(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		if(user!=null) {
			String money_str=request.getParameter("money");
			if(money_str!=null) {
				int money=Integer.valueOf(money_str);
				int num= userService.addMoney(user.getId(), money);
				user=userService.user(user.getId());
				session.setAttribute("user", user);
				return num;
			}
		}
		response.setStatus(403);
		return 0;
	}
}
