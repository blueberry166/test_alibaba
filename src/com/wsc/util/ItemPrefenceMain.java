package com.wsc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.wsc.bean.AlibabaDataBean;
import com.wsc.bean.WeightBean;

public class ItemPrefenceMain {

	/**
	 * 读文件，计算每个用户对每个品牌的权值
	 * @param frompath
	 * @throws IOException
	 */
	@SuppressWarnings("null")
	public void readWriteTxt(String frompath,String toPath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(frompath));
		WeightStatistic ws = new WeightStatistic(toPath);
		
		String last_user="";
		ArrayList<AlibabaDataBean> userBrandList = null;
		AlibabaDataBean aliBean;
	    try {
	    	while(br.ready()){
	    		String line = br.readLine();
		        
		        if(line.contains("user_id"))continue;
		        
		        String[] arr = line.split(",");
		        
		        aliBean = new AlibabaDataBean();
	        	aliBean.setUser_id(arr[0]);
	        	aliBean.setBrand_id(arr[1]);
	        	aliBean.setType(arr[2]);
	        	aliBean.setDate(arr[3]);
		        
		        if(arr[0].equals(last_user)){
		        	//上一个用户
		        	userBrandList.add(aliBean);
		        }else{
		        	if(userBrandList!=null){
		        		//处理上一个用户
		        		ws.typeWeightMap = new HashMap<String, WeightBean>();
		        		ws.brandTypeWeightMap = new HashMap<String, WeightBean>();//统计品牌的行为及权重
		        		
			        	ws.statistic(last_user,userBrandList);
		        	}
		        	
		        	//开始下一个用户
		        	last_user = arr[0];
		        	
		        	userBrandList = new ArrayList<AlibabaDataBean>();
		        	userBrandList.add(aliBean);
		        }
	    	}
	    	
	    	//处理最后一个用户
	    	if(userBrandList!=null){
	    		
	    		ws.typeWeightMap = new HashMap<String, WeightBean>();
        		ws.brandTypeWeightMap = new HashMap<String, WeightBean>();//统计品牌的行为及权重
        		
        		ws.statistic(last_user,userBrandList);
        	}
	        
	    } catch(Exception e){
	    	e.printStackTrace();
	    }finally {
	        br.close();
	        ws.writeFile.closeStream();
	    }
	}
	
	public static void main(String[] args) {
		
		String frompath = "E:\\推荐比赛\\t_alibaba_data.csv";
		String topath = "E:\\推荐比赛\\t_alibaba_data_output.csv";
		try {
			new ItemPrefenceMain().readWriteTxt(frompath,topath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
