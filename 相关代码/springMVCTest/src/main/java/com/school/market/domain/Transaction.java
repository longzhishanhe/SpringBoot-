package com.school.market.domain;

public class Transaction {
	//交易表id
	private int id;
	//商品id
	private int commodityId;
	//买家id
	private int userId;
	//商品状态，待发货0、发货中1、已签收2
	private int status;
	//商品名
	private String name;
	//价格
	private int price;
	//类别
	private String type;
	//商家
	private String shopName;
	//买家
	private String userName;
	//新旧程度
	private int damage_level;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(int commodityId) {
		this.commodityId = commodityId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getDamage_level() {
		return damage_level;
	}
	public void setDamage_level(int damage_level) {
		this.damage_level = damage_level;
	}
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", commodityId=" + commodityId + ", userId=" + userId + ", status=" + status
				+ ", name=" + name + ", price=" + price + ", type=" + type + ", shopName=" + shopName + ", userName="
				+ userName + ", damage_level=" + damage_level + "]";
	}
	
}
