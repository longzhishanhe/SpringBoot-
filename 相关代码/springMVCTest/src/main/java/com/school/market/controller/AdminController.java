package com.school.market.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.school.market.domain.Admin;
import com.school.market.domain.Shop;
import com.school.market.domain.User;
import com.school.market.service.AdminService;
import com.school.market.service.ShopService;
import com.school.market.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShopService shopService;
	
	/**
	 * 管理员登录
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
			Admin user=adminService.login(username, password);
			if(user!=null) {
				HttpSession session = request.getSession();
				session.setAttribute("admin", user);
				return "ok";
			}
		}
		return "you are faile";
	}
	
	/**
	 * 查看指定状态的普通用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/showUser",method = RequestMethod.GET)
	public List<User> showUser(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Admin admin=(Admin) session.getAttribute("admin");
		if(admin!=null&&request.getParameter("status")!=null) {
			int status=Integer.valueOf(request.getParameter("status"));
			return this.userService.showUser(status);
		}
		response.setStatus(403);
		return null;
	}
	
	/**
	 * 查看指定状态的商铺
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/showShop",method = RequestMethod.GET)
	public List<Shop> showShop(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Admin admin=(Admin) session.getAttribute("admin");
		if(admin!=null&&request.getParameter("status")!=null) {
			int status=Integer.valueOf(request.getParameter("status"));
			return this.shopService.showShop(status);
		}
		response.setStatus(403);
		return null;
	}
	
	/**
	 * 修改商铺等级
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/ShopLevel",method = RequestMethod.PUT)
	public String updateLevel(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Admin admin=(Admin) session.getAttribute("admin");
		if(admin!=null) {
			String id_str=request.getParameter("id");
			String level_str=request.getParameter("level");
			if(id_str!=null&&level_str!=null) {
				int id=Integer.valueOf(id_str);
				int level=Integer.valueOf(level_str);
				if(this.shopService.modificationLevel(id, level)==1) {
					return "ok";
				}
			}
		}
		response.setStatus(403);
		return null;
	}
	
	/**
	 * 管理普通用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/updateUser",method = RequestMethod.PUT)
	public int updateUser(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Admin admin=(Admin) session.getAttribute("admin");
		if(admin!=null) {
			int id=Integer.valueOf(request.getParameter("id"));
			int status=Integer.valueOf(request.getParameter("status"));
			return this.userService.modificationUserStatus(id, status);
		}
		response.setStatus(403);
		return 0;
	}
	
	//管理商户
	/**
	 * 管理普通用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/updateShop",method = RequestMethod.PUT)
	public int updateShop(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Admin admin=(Admin) session.getAttribute("admin");
		if(admin!=null) {
			int id=Integer.valueOf(request.getParameter("id"));
			int status=Integer.valueOf(request.getParameter("status"));
			return this.shopService.modificationShopStatus(id, status);
		}
		response.setStatus(403);
		return 0;
	}
	
	/**
	 * 充值
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/addMoney",method = RequestMethod.PUT)
	public int addMoney(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Admin admin=(Admin) session.getAttribute("admin");
		if(admin!=null) {
			String id_str=request.getParameter("id");
			String money_str=request.getParameter("money");
			if(id_str!=null&&money_str!=null) {
				int id=Integer.valueOf(id_str);
				int money=Integer.valueOf(money_str);
				return userService.addMoney(id, money);
				
			}
		}
		response.setStatus(403);
		return 0;
	}
}
