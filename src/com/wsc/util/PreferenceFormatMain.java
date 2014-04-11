package com.wsc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.wsc.bean.AlibabaDataBean;

public class PreferenceFormatMain {
	
	WriteTxt writeFile;
	float maxPreferValue = 80;//最大值
	
	public void readWriteTxt(String frompath,String toPath) throws IOException {
		writeFile = new WriteTxt(toPath);
		
		BufferedReader br = new BufferedReader(new FileReader(frompath));
	    try {
	    	AlibabaDataBean aliBean;
	    	while(br.ready()){
	    		String line = br.readLine();
		        
		        String[] arr = line.split(",");
		        
		        aliBean = new AlibabaDataBean();
	        	aliBean.setUser_id(arr[0]);
	        	aliBean.setBrand_id(arr[1]);
	        	if(arr.length == 4){
	        		
	        		aliBean.setDate(arr[3]);
	        		float prefer = Float.parseFloat(arr[2])/maxPreferValue;
		        	
		        	aliBean.setPrefer(prefer);
		        	writeFile.writeTxt(aliBean.toString());
	        	}
	    	}
	        
	    } catch(Exception e){
	    	e.printStackTrace();
	    }finally {
	        br.close();
	    }
	}
	
	public static void main(String[] args) {
		
		String frompath = "E:\\推荐比赛\\t_alibaba_data_output.csv";
		String topath = "E:\\推荐比赛\\t_alibaba_data_output_归一化.csv";
		try {
			new PreferenceFormatMain().readWriteTxt(frompath,topath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
