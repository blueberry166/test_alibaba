package com.wsc.bean;

/**
 * 权重，相对或绝对值
 * @author TAOYE
 *
 */
public class WeightBean {
	private float weight;
	
	private String referType;//相对行为
	private float referRatio;//相对比值
	
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
