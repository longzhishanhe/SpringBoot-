package com.school.market.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.school.market.domain.Goods;
import com.school.market.domain.Shop;
import com.school.market.domain.Transaction;
import com.school.market.service.CommodityService;
import com.school.market.service.ShopService;
import com.school.market.service.TransactionService;

@RestController
@RequestMapping("/shop")
public class ShopController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private TransactionService transactionService;
	
	/**
	 * 商家登录
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
			Shop user=shopService.login(username, password);
			if(user!=null){
				if(user.getStatus()==0){
					HttpSession session = request.getSession();
					session.setAttribute("shop", user);
					return "ok";
				}
			}
			
		}
		return "you are faile";
	}
	
	//shop
	@RequestMapping(value="/me",method = RequestMethod.GET)
	public Shop myMsg(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Shop shop=(Shop) session.getAttribute("shop");
		if(shop!=null) {
			return shop;
		}
		response.setStatus(403);
		return null;
	}
	
	/**
	 * 获取所有商品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/AllCommodity",method = RequestMethod.GET)
	public List<Goods> showAllCommodity(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Shop shop=(Shop) session.getAttribute("shop");
		if(shop!=null) {
			int shopId=shop.getId();
			return this.shopService.shopGoods(shopId);
		}
		response.setStatus(403);
		return null;
		
	}
	
	
	@RequestMapping(value="/register",method = RequestMethod.POST)
	public int registerShop(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
		
		int num=shopService.registerShop(username, password, phone, sex, account);
		
		if(num==1) {
			request.getSession().setAttribute("shopNmae", username);
		}

		return num;
	}
	
	//用于接收文件
    @RequestMapping("/upload")
	public int updateP(MultipartFile files1,MultipartFile file2,HttpServletRequest request, HttpServletResponse response){
    	String fileName=(String) request.getSession().getAttribute("shopNmae");
    	try {
			upload(files1, "license", fileName+".jpg");
			upload(file2, "card", fileName+".jpg");
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

    public static boolean upload(MultipartFile photo,String pathName,String fileName) throws IOException {
        //判断用户是否上传了文件
        if(!photo.isEmpty()){
 
            //文件上传的地址
            String path = ResourceUtils.getURL("classpath:").getPath()+"static/"+pathName;
            String realPath = path.replace('/', '\\').substring(1,path.length());
            //转成UTF-8编码
            realPath=URLDecoder.decode(realPath,"UTF-8");
            //用于查看路径是否正确
            System.out.println(realPath);
            
//            realPath="D:\\img";
 
            //获取文件的名称
//            final String fileName = photo.getOriginalFilename();
 
            //限制文件上传的类型
            String contentType = photo.getContentType();
            if("image/jpeg".equals(contentType) || "image/jpg".equals(contentType) ){
            	
                File file = new File(realPath,fileName);
 
                //完成文件的上传
                photo.transferTo(file);
                System.out.println("图片上传成功!");
                return true;
            } else {
                System.out.println("上传失败！");
                return false;
            }
        } else {
            System.out.println("上传失败！");
            return false;
        }
    }
    
    //上架商品
    @RequestMapping(value="/addGoods",method = RequestMethod.POST)
	public int addGoods(HttpServletRequest request, HttpServletResponse response) {
    	//判断权限
    	HttpSession session = request.getSession();
		Shop shop=(Shop) session.getAttribute("shop");
		if(shop!=null) {
			int shopId=shop.getId();
			String name=request.getParameter("name");
			int price=Integer.valueOf(request.getParameter("price"));
			String type=request.getParameter("type");
			int number=Integer.valueOf(request.getParameter("number"));
			int damage_level=Integer.valueOf(request.getParameter("damage_level"));
			
			int num= commodityService.addCommodity(name, price, type, shopId, number, number, damage_level);
			if(num==1) {
				session.setAttribute("goodsName", name);
			}
			return num;
		}
    	return 0;
    }
    
    //商品的图片
    @RequestMapping("/goodsImg")
	public int updateGoodsImg(MultipartFile goodsImg,HttpServletRequest request, HttpServletResponse response){
    	String fileName=(String) request.getSession().getAttribute("goodsName");
    	System.out.println(fileName);
    	System.out.println(goodsImg.getOriginalFilename());
    	if(fileName==null) {
    		System.out.println("fileName!=null");
    		return 0;
    	}
    	try {
			upload(goodsImg, "goodsImg", fileName+".jpg");
			System.out.println("upload");
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
    
    //下架商品
    @RequestMapping(value="/removeGoods",method = RequestMethod.PUT)
	public int removeGoods(HttpServletRequest request, HttpServletResponse response) {
    	//判断权限
    	HttpSession session = request.getSession();
		Shop shop=(Shop) session.getAttribute("shop");
		if(shop!=null) {
			int goodsId=Integer.valueOf(request.getParameter("id"));
			
			return commodityService.modificationGoodsStatus(goodsId, 0);
		}
    	return 0;
    }
    
    //商户获取交易表单
  	@RequestMapping(value="/transaction",method = RequestMethod.GET)
  	public List<Transaction> showTransaction(HttpServletRequest request, HttpServletResponse response) {
  		HttpSession session = request.getSession();
  		Shop shop=(Shop) session.getAttribute("shop");
  		
  		if(shop!=null) {
  			String show=request.getParameter("show");
  			List<Transaction> t=new ArrayList<Transaction>();
  			
  			if(show.equals("shop1")) {
  				List<Transaction> t1=transactionService.shopTransaction(shop.getId(), 0);
  				t.addAll(t1);
  			}else if(show.equals("shop2")) {
  				
  				List<Transaction> t1=transactionService.shopTransaction(shop.getId(), 1);
  				List<Transaction> t2=transactionService.shopTransaction(shop.getId(), 2);
  				t.addAll(t1);
  				t.addAll(t2);
  			}
  			
  			return t;
  		}else {
  			response.setStatus(403);
  			return null;
  		}
  	}
  	
  	//确认发货
  	@RequestMapping(value="/transactionStatus",method = RequestMethod.PUT)
	public int transactionStatus(HttpServletRequest request, HttpServletResponse response) {
    	//判断权限
    	HttpSession session = request.getSession();
		Shop shop=(Shop) session.getAttribute("shop");
		if(shop!=null) {
			int id=Integer.valueOf(request.getParameter("id"));
			int status=Integer.valueOf(request.getParameter("status"));
			
			return transactionService.modificationTransactionStatus(id, status);
		}
    	return 0;
    }
}
