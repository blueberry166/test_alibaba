package com.wsc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ResultAnalysisMain {
	
	WriteTxt writeFile;
//	float minPrefer = 2;//设置下限
	UserBuyCnt ubc = new UserBuyCnt();
	
	public void readWriteTxt(String frompath,String toPath) throws IOException {
		writeFile = new WriteTxt(toPath);
		
		HashMap<String,Integer> userBuyCntMap = ubc.getUserBuyMap("E:\\推荐比赛\\t_alibaba_data.csv");
		BufferedReader br = new BufferedReader(new FileReader(frompath));
		
		String last_user="";
//		float last_user_Threshold = 0;//上一个用户的阈值（最大评分的一半）
		StringBuilder w_line = null;//写入的
		int i_rndCnt = 0;//推荐个数
		int m_rndCnt = 0;
		String tmp;
	    try {
	    	while(br.ready()){
	    		tmp = br.readLine();
		        if(tmp.contains("user_id"))continue;
		        String[] arr = tmp.split(",");
		        if(arr.length<3)continue;
		        
		        float cur_prefer = Float.parseFloat(arr[2]);//当前记录的评分
		        if(cur_prefer == 0) continue;
		        
		        if(arr[0].equals(last_user)){
		        	//上一个用户
		        	if(m_rndCnt > i_rndCnt && i_rndCnt>0){
		        		w_line.append(arr[1]+",");
		        		i_rndCnt++;
		        	}
		        }else{
		        	if(w_line!=null){
		        		//处理上一个用户，写入文件
		        		w_line.deleteCharAt(w_line.length()-1);//删除最后一个逗号
		        		w_line.append("\n");
		        		writeFile.writeTxt(w_line.toString());
		        	}
		        	
		        	//开始下一个用户
		        	last_user = arr[0];
		        	m_rndCnt = userBuyCntMap.get(arr[0]);//本用户的最大推荐个数
		        	
		        	if(m_rndCnt>1){
		        		w_line = new StringBuilder();
			        	w_line.append(arr[0]+"\t");
			        	w_line.append(arr[1]+",");
			        	
			        	i_rndCnt = 1;//已推荐个数
		        	}else{
		        		w_line = null;
		        	}
		        }
	    	}
	    	
	    	//处理最后一个用户
	    	if(w_line!=null){
        		w_line.deleteCharAt(w_line.length()-1);//删除最后一个逗号
        		w_line.append("\n");
        		writeFile.writeTxt(w_line.toString());
        	}
	    } catch(Exception e){
	    	e.printStackTrace();
	    }finally {
	        br.close();
	        writeFile.closeStream();
	    }
	}
	
	public static void main(String[] args) {
		
		String frompath = "E:\\推荐比赛\\t_alibaba_data_output_final.csv";
		String topath = "E:\\推荐比赛\\t_alibaba_data_output_final_result5.txt";
		try {
			new ResultAnalysisMain().readWriteTxt(frompath,topath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
