package com.qk365.bluetooth.http;

import com.qk365.bluetooth.net.response.ResultError;

import java.io.Serializable;
/**
 * 基类 http请求返回的数据结构类
 */
public class BaseResult implements Serializable{
	private static final long serialVersionUID = -2592849950094769081L;
	public static final boolean STATUS_SUCCESS = true;
    public static final boolean STATUS_FAILE = false;
	/**
	 * success = true 表示成功
	 * success = false 表示失败
	 * 服务器返回的状态码
	 */
	public boolean success;


	/**
	 * 服务器返回的消息
	 */
//	public String msg;
	public ResultError error;
	
	
}
