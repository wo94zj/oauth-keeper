package com.oauth.resp;

import java.io.Serializable;

import lombok.Data;

@Data
public class BaseDto<T> implements Serializable {

    private static final long serialVersionUID = -1670266669488179038L;
    
    private int code;
	private String msg;
	private T data;
	
	public void setResult(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public void setResult(ResultCode result, String msg, T data) {
		setResult(result.getCode(), msg, data);
	}
	
	public void setResult(ResultCode result, T data) {
		setResult(result.getCode(), result.getMsg(), data);
	}
}
