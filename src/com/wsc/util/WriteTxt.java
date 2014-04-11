package com.wsc.util;

import java.io.*;

public class WriteTxt {
	
	FileWriter fstream;
	BufferedWriter out;
	int num = 0;
	
	public WriteTxt(String path){
		try {
//			fstream = new FileWriter("E:\\异常检测\\项目材料\\控制\\R6258_2.csv");
			fstream = new FileWriter(path,true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		out = new BufferedWriter(fstream);
	}
	
	public boolean writeTxt(String line){
		try {
			out.write(line);
			
			if(++num == 1000){
				out.flush();
				num = 0;
			}
			
			return true;
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		return false;
	}
	
	public boolean closeStream(){
		try {
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String args[]) {
//		WriteFile writeFile = new WriteFile("E:\\异常检测\\项目材料\\控制\\R6258_2.csv");
//		writeFile.writeTxt(line);
//		writeFile.clone();
	}
}