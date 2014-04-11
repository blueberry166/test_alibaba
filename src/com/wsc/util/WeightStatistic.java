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
	HashMap<String, WeightBean> brandTypeWeightMap = new HashMap<String, WeightBean>();//ͳ��Ʒ�Ƶ���Ϊ��Ȩ��
	
	WriteTxt writeFile;
	
	public WeightStatistic(String toPath){
		writeFile = new WriteTxt(toPath);
	}

	/**
	 * ͳ���û�ÿ����Ϊ��Ȩ��
	 */
	public void statistic(String user,ArrayList<AlibabaDataBean> dataLst) {
		
		HashMap<String, Integer> weightMap = new HashMap<String, Integer>();//ͳ������Ϊ�͸���
		weightMap.put(0+"", 0);
		weightMap.put(1+"", 0);
		weightMap.put(2+"", 0);
		weightMap.put(3+"", 0);
		
		HashMap<String, Integer> i_weightMap = null;//ͳ�Ƶ���Ʒ�Ƶ���Ϊ������

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
					// ������һ��Ʒ��
					getUserBrandResult(last_brand,i_weightMap,brandTypeWeightMap);
				}
				
				// ��ʼ��һ��Ʒ��
				last_brand = i_data.getBrand_id();

				i_weightMap = new HashMap<String, Integer>();//ͳ������Ϊ�͸���
				i_weightMap.put(type, 1);
			}
		}
		if(i_weightMap!=null){
			// �������һ��Ʒ��
			getUserBrandResult(last_brand,i_weightMap,brandTypeWeightMap);
		}
		
		getUserBrandResult("",weightMap,typeWeightMap);//���û��������Ϊ�ı�ֵ
		typeWeightMap.put(1+"", new WeightBean((float) 1.0));//�����Ȩ��Ϊ1
		//������1��Ȩ����1
		Iterator it = typeWeightMap.entrySet().iterator();
		while(it.hasNext()){
			Entry en = (Entry) it.next();
			WeightBean value = (WeightBean)en.getValue();
			
			if(value.getWeight() >1){
				typeWeightMap.put((String)en.getKey(),new WeightBean((float)1.0));
			}
		}
		
		//���¼����û�ƫ��
		last_brand = "";
		AlibabaDataBean aliBean = null;
		for (AlibabaDataBean i_data : dataLst) {
			String type = i_data.getType();
			String brand = i_data.getBrand_id();
			
			//����Ȩ��
			float i_weight = getCurrentPreference(brand,type);
			
			if (i_data.getBrand_id().equals(last_brand)) {
				
				aliBean.setPrefer(aliBean.getPrefer()+i_weight);
			} else {
				
				if(aliBean!=null){
					//������һ��Ʒ��ƫ��
					writeFile.writeTxt(aliBean.toString());
					System.out.print(aliBean.toString());
				}
				
				//��ʼ��һ��Ʒ��
				last_brand = i_data.getBrand_id();
				aliBean = new AlibabaDataBean();
				aliBean.setUser_id(user);
				aliBean.setBrand_id(last_brand);
				aliBean.setPrefer(i_weight);
				aliBean.setDate(i_data.getDate());
			}
		}
		if(aliBean!=null){
			//������һ��Ʒ��ƫ��
			writeFile.writeTxt(aliBean.toString());
			System.out.print(aliBean.toString());
		}
	}
	
	public float getCurrentPreference(String brandId,String type){
		
		String brandWkey = brandId+"_"+type;
		if(brandTypeWeightMap.containsKey(brandWkey)){
			
			WeightBean wb = brandTypeWeightMap.get(brandWkey);
			if(wb.getWeight()!=0){
				//��Թ���
				return wb.getWeight();
			}else{
				//���������Ϊ
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
	 * @param brandTypeWeightMap Ʒ����Ϊ������Ӧ��Ȩ��
	 */
	public void getUserBrandResult(String brand_id,HashMap<String, Integer> map,HashMap<String, WeightBean> brandTypeWeightMap){
		
		Iterator it = map.entrySet().iterator();
		
		while(it.hasNext()){
			Entry en = (Entry) it.next();
			String key = (String) en.getKey();//��Ϊ
			int value = (Integer) en.getValue();//��Ϊ����
			
			float ratio = 0;
			WeightBean wb = new WeightBean();
			if(map.containsKey(TypeConst.buy) && !key.equals(TypeConst.buy)){
				float buyCnt = map.get(TypeConst.buy);
				ratio = (float)buyCnt/value;
				wb.setReferType(TypeConst.buy);
				wb.setWeight(ratio);//���buy��Ȩ��
				
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
				wb.setReferRatio(ratio);//��Ա�ֵ
				
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