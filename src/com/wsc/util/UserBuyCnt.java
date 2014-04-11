package com.wsc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.wsc.bean.TypeConst;

/**
 * 统计每个用户每月的平均购买个数
 * @author TAOYE
 *
 */
public class UserBuyCnt {
	/**
	 * 读文件，统计每个用户每月的平均购买个数
	 * @param frompath
	 * @throws IOException
	 */
	public HashMap<String,Integer> getUserBuyMap(String frompath) throws IOException {
		
		HashMap<String,Integer> userBuyCntMap = new HashMap<String,Integer>();
		
		BufferedReader br = new BufferedReader(new FileReader(frompath));
		
	    try {
	    	while(br.ready()){
	    		String line = br.readLine();
		        
		        if(line.contains("user_id"))continue;
		        
		        String[] arr = line.split(",");
		        
		        if(TypeConst.buy.equals(arr[2])){
		        	if(userBuyCntMap.containsKey(arr[0])){
		        		userBuyCntMap.put(arr[0], userBuyCntMap.get(arr[0]) + 1);
		        	}else{
		        		userBuyCntMap.put(arr[0], 1);
		        	}
		        }
	    	}
	    	
	    } catch(Exception e){
	    	e.printStackTrace();
	    }finally {
	        br.close();
	    }
	    
	    Iterator it = userBuyCntMap.entrySet().iterator();
	    while(it.hasNext()){
	    	Entry en = (Entry) it.next();
	    	int cnt = (Integer) en.getValue();
	    	userBuyCntMap.put((String) en.getKey(), (int) Math.round(cnt/4));
	    }
	    
	    return userBuyCntMap;
	}
	
	public static void main(String[] args) {
		
		String frompath = "E:\\推荐比赛\\t_alibaba_data.csv";
		UserBuyCnt ubc = new UserBuyCnt();
		try {
			HashMap<String,Integer> userBuyCntMap = ubc.getUserBuyMap(frompath);
			System.out.println(userBuyCntMap.size());
			System.out.println(userBuyCntMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
