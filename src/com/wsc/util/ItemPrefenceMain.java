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
	 * ���ļ�������ÿ���û���ÿ��Ʒ�Ƶ�Ȩֵ
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
		        	//��һ���û�
		        	userBrandList.add(aliBean);
		        }else{
		        	if(userBrandList!=null){
		        		//������һ���û�
		        		ws.typeWeightMap = new HashMap<String, WeightBean>();
		        		ws.brandTypeWeightMap = new HashMap<String, WeightBean>();//ͳ��Ʒ�Ƶ���Ϊ��Ȩ��
		        		
			        	ws.statistic(last_user,userBrandList);
		        	}
		        	
		        	//��ʼ��һ���û�
		        	last_user = arr[0];
		        	
		        	userBrandList = new ArrayList<AlibabaDataBean>();
		        	userBrandList.add(aliBean);
		        }
	    	}
	    	
	    	//�������һ���û�
	    	if(userBrandList!=null){
	    		
	    		ws.typeWeightMap = new HashMap<String, WeightBean>();
        		ws.brandTypeWeightMap = new HashMap<String, WeightBean>();//ͳ��Ʒ�Ƶ���Ϊ��Ȩ��
        		
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
		
		String frompath = "E:\\�Ƽ�����\\t_alibaba_data.csv";
		String topath = "E:\\�Ƽ�����\\t_alibaba_data_output.csv";
		try {
			new ItemPrefenceMain().readWriteTxt(frompath,topath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
