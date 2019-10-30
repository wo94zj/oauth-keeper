package com.oauth.exception;

import com.oauth.resp.ResultCode;

public class CommonException extends RuntimeException {

	private static final long serialVersionUID = -4494571620250781965L;
	
	private Integer code;

	public CommonException(Integer code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public CommonException(Integer code, String message) {
		this(code, message, null);
	}

	public CommonException(ResultCode result) {
		this(result.getCode(), result.getMsg(), null);
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
