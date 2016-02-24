package org.caco.common.util;

public class Result {
	public static int SUCCESS = 0;
	public static String SUCCESS_DESC = "SUCCESS";
	public static int FAILD = 1;
	public static String FAILD_DESC = "FAILD";
	private int ret;
	private String msg;
	private Object entity_data;
	public Result(int ret) {
		this.ret = ret;
		this.msg = ret==SUCCESS?SUCCESS_DESC:FAILD_DESC;
	}
	public Result(int ret, String msg) {
		this.ret = ret;
		this.msg = msg;
	}
	public Result(int ret, String msg,Object entity_data) {
		this.ret = ret;
		this.msg = msg;
		this.entity_data = entity_data;
	}
	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getEntity_data() {
		return entity_data;
	}
	public void setEntity_data(Object entity_data) {
		this.entity_data = entity_data;
	}
	
}
