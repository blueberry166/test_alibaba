package com.wsc.bean;

import java.util.HashMap;

public class UserTypeWeight {
//	private float click;//���
//	private float buy;//��
//	private float prefer;//�ղ�Ȩ��
//	private float cart;//�ӹ��ﳵ
//	
	private HashMap<String, Float> typeWeightMap;//���û�������Ʒ��
	
	private HashMap<String, Float> brandTypeWeightMap;//���û���Ʒ�Ƶ�ĳ����Ϊ��Ȩ�أ���ʽkey=brand_type,value=weight
	
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