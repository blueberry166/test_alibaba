package com.wsc.bean;

import java.util.HashMap;

public class UserTypeWeight {
//	private float click;//点击
//	private float buy;//买
//	private float prefer;//收藏权重
//	private float cart;//加购物车
//	
	private HashMap<String, Float> typeWeightMap;//按用户，不按品牌
	
	private HashMap<String, Float> brandTypeWeightMap;//该用户对品牌的某种行为的权重，格式key=brand_type,value=weight
	
	public HashMap<String, Float> getBrandTypeWeightMap() {
		return brandTypeWeightMap;
	}
	public void setBrandTypeWeightMap(HashMap<String, Float> brandTypeWeightMap) {
		this.brandTypeWeightMap = brandTypeWeightMap;
	}
	public HashMap<String, Float> getTypeWeightMap() {
		return typeWeightMap;
	}
	public void setTypeWeightMap(HashMap<String, Float> typeWeightMap) {
		this.typeWeightMap = typeWeightMap;
	}
}