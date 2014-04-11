package com.wsc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.wsc.bean.AlibabaDataBean;
import com.wsc.bean.TypeConst;
import com.wsc.bean.WeightBean;

public class WeightStatistic {
	
	HashMap<String, WeightBean> typeWeightMap = new HashMap<String, WeightBean>();
	HashMap<String, WeightBean> brandTypeWeightMap = new HashMap<String, WeightBean>();//统计品牌的行为及权重
	
	WriteTxt writeFile;
	
	public WeightStatistic(String toPath){
		writeFile = new WriteTxt(toPath);
	}

	/**
	 * 统计用户每个行为的权重
	 */
	public void statistic(String user,ArrayList<AlibabaDataBean> dataLst) {
		
		HashMap<String, Integer> weightMap = new HashMap<String, Integer>();//统计总行为和个数
		weightMap.put(0+"", 0);
		weightMap.put(1+"", 0);
		weightMap.put(2+"", 0);
		weightMap.put(3+"", 0);
		
		HashMap<String, Integer> i_weightMap = null;//统计单个品牌的行为及个数

		String last_brand = "";
		for (AlibabaDataBean i_data : dataLst) {
			
			String type = i_data.getType();
			int typeCnt = weightMap.get(type);
			weightMap.put(type, typeCnt+1);
			
			if (i_data.getBrand_id().equals(last_brand)) {
				
				if(i_weightMap.containsKey(type)){
					i_weightMap.put(type, i_weightMap.get(type)+1);
				}else{
					i_weightMap.put(type, 1);
				}
				
			} else {
				
				if(i_weightMap!=null){
					// 处理上一个品牌
					getUserBrandResult(last_brand,i_weightMap,brandTypeWeightMap);
				}
				
				// 开始下一个品牌
				last_brand = i_data.getBrand_id();

				i_weightMap = new HashMap<String, Integer>();//统计总行为和个数
				i_weightMap.put(type, 1);
			}
		}
		if(i_weightMap!=null){
			// 处理最后一个品牌
			getUserBrandResult(last_brand,i_weightMap,brandTypeWeightMap);
		}
		
		getUserBrandResult("",weightMap,typeWeightMap);//按用户计算各行为的比值
		typeWeightMap.put(1+"", new WeightBean((float) 1.0));//购买的权重为1
		//将大于1的权重设1
		Iterator it = typeWeightMap.entrySet().iterator();
		while(it.hasNext()){
			Entry en = (Entry) it.next();
			WeightBean value = (WeightBean)en.getValue();
			
			if(value.getWeight() >1){
				typeWeightMap.put((String)en.getKey(),new WeightBean((float)1.0));
			}
		}
		
		//重新计算用户偏好
		last_brand = "";
		AlibabaDataBean aliBean = null;
		for (AlibabaDataBean i_data : dataLst) {
			String type = i_data.getType();
			String brand = i_data.getBrand_id();
			
			//计算权重
			float i_weight = getCurrentPreference(brand,type);
			
			if (i_data.getBrand_id().equals(last_brand)) {
				
				aliBean.setPrefer(aliBean.getPrefer()+i_weight);
			} else {
				
				if(aliBean!=null){
					//保存上一个品牌偏好
					writeFile.writeTxt(aliBean.toString());
					System.out.print(aliBean.toString());
				}
				
				//开始下一个品牌
				last_brand = i_data.getBrand_id();
				aliBean = new AlibabaDataBean();
				aliBean.setUser_id(user);
				aliBean.setBrand_id(last_brand);
				aliBean.setPrefer(i_weight);
				aliBean.setDate(i_data.getDate());
			}
		}
		if(aliBean!=null){
			//保存上一个品牌偏好
			writeFile.writeTxt(aliBean.toString());
			System.out.print(aliBean.toString());
		}
	}
	
	public float getCurrentPreference(String brandId,String type){
		
		String brandWkey = brandId+"_"+type;
		if(brandTypeWeightMap.containsKey(brandWkey)){
			
			WeightBean wb = brandTypeWeightMap.get(brandWkey);
			if(wb.getWeight()!=0){
				//相对购买
				return wb.getWeight();
			}else{
				//相对其他行为
				String referType = wb.getReferType();
				
				if(typeWeightMap.containsKey(referType)){
					return wb.getReferRatio() * typeWeightMap.get(referType).getWeight();
				}else{
					return 0;
				}
			}
		}else{
			if(typeWeightMap.containsKey(type)){
				return typeWeightMap.get(type).getWeight();
			}else{
				return 0;
			}
		}
	}
	/**
	 * 
	 * @param brand_id
	 * @param map
	 * @param brandTypeWeightMap 品牌行为，及对应的权重
	 */
	public void getUserBrandResult(String brand_id,HashMap<String, Integer> map,HashMap<String, WeightBean> brandTypeWeightMap){
		
		Iterator it = map.entrySet().iterator();
		
		while(it.hasNext()){
			Entry en = (Entry) it.next();
			String key = (String) en.getKey();//行为
			int value = (Integer) en.getValue();//行为次数
			
			float ratio = 0;
			WeightBean wb = new WeightBean();
			if(map.containsKey(TypeConst.buy) && !key.equals(TypeConst.buy)){
				float buyCnt = map.get(TypeConst.buy);
				ratio = (float)buyCnt/value;
				wb.setReferType(TypeConst.buy);
				wb.setWeight(ratio);//相对buy的权重
				
			}else if(map.containsKey(TypeConst.cart)){
				
				float cartCnt = map.get(TypeConst.cart);
				ratio = (float)cartCnt/value;
				wb.setReferType(TypeConst.cart);
				
			}else if(map.containsKey(TypeConst.prefer)){
				
				float preferCnt = map.get(TypeConst.prefer);
				ratio = (float)preferCnt/value;
				wb.setReferType(TypeConst.prefer);
			}
			
			if(ratio!=0){
				wb.setReferRatio(ratio);//相对比值
				
				if(brand_id.equals("")){
					brandTypeWeightMap.put(key, wb);
				}else{
					brandTypeWeightMap.put(brand_id+"_"+key, wb);
				}
			}
		}
		
		System.out.println(map);
	}
	
}