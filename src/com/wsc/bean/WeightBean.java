package com.wsc.bean;

/**
 * Ȩ�أ���Ի����ֵ
 * @author TAOYE
 *
 */
public class WeightBean {
	private float weight;
	
	private String referType;//�����Ϊ
	private float referRatio;//��Ա�ֵ
	
	public WeightBean(){
		
	}
	
	public WeightBean(float weight){
		setWeight(weight);
	}
	
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public String getReferType() {
		return referType;
	}
	public void setReferType(String referType) {
		this.referType = referType;
	}
	public float getReferRatio() {
		return referRatio;
	}
	public void setReferRatio(float referRatio) {
		this.referRatio = referRatio;
	}
}
