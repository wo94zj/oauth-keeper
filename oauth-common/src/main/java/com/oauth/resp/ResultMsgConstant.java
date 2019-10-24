package com.oauth.resp;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResultMsgConstant {

	private final static String PREFIX = "result.";
	private final static String SUFFIX = "";
	
	public static String OK = PREFIX + "ok" + SUFFIX;
	public static String FAILTED = PREFIX + "failed" + SUFFIX;
	public static String BUSY = PREFIX + "busy" + SUFFIX;
	
	public static String VISIT_LIMIT = PREFIX + "visit.limit" + SUFFIX;
	public static String IDEMPOTENT_LIMIT = PREFIX + "idempotent.limit" + SUFFIX;
	
	public static String BAD_REQUEST = PREFIX + "badrequest" + SUFFIX;
	public static String NOT_LOGIN = PREFIX + "unauthorized" + SUFFIX;
	public static String NOT_AUTH = PREFIX + "forbidden" + SUFFIX;
	public static String METHOD_ERROR = PREFIX + "notmethod" + SUFFIX;
	public static String URL_ERROR = PREFIX + "notfound" + SUFFIX;
	public static String MEDIA_TYPE_ERROR = PREFIX + "media.error" + SUFFIX;
	
	public static String SYSTEM_ERROR = PREFIX + "system.error" + SUFFIX;
	
	
	//自定义部分
	public static String UNABLE_ACOUNT = PREFIX + "unable.unaccount" + SUFFIX;
    public static String UNABLE_LOGIN = PREFIX + "unable.login" + SUFFIX;
    public static String AUTH_EXPIRE = PREFIX + "auth.expire" + SUFFIX;
    public static String CHECK_FAILED = PREFIX + "check.failed" + SUFFIX;
    public static String UNABLE_CLIENT = PREFIX + "unable.client" + SUFFIX;
    public static String UNABLE_SECRET = PREFIX + "unable.secret" + SUFFIX;
	
	
	static {
		Locale locale = Locale.getDefault();
		ResourceBundle res = ResourceBundle.getBundle("common", locale);
		
		OK = res.getString(OK);
		FAILTED = res.getString(FAILTED);
		BUSY = res.getString(BUSY);
		
		VISIT_LIMIT = res.getString(VISIT_LIMIT);
		IDEMPOTENT_LIMIT = res.getString(IDEMPOTENT_LIMIT);
		
		BAD_REQUEST = res.getString(BAD_REQUEST);
		NOT_LOGIN = res.getString(NOT_LOGIN);
		NOT_AUTH = res.getString(NOT_AUTH);
		METHOD_ERROR = res.getString(METHOD_ERROR);
		URL_ERROR = res.getString(URL_ERROR);
		MEDIA_TYPE_ERROR = res.getString(MEDIA_TYPE_ERROR);
		
		SYSTEM_ERROR = res.getString(SYSTEM_ERROR);
		
		
		UNABLE_ACOUNT = res.getString(UNABLE_ACOUNT);
		UNABLE_LOGIN = res.getString(UNABLE_LOGIN);
		AUTH_EXPIRE = res.getString(AUTH_EXPIRE);
		CHECK_FAILED = res.getString(CHECK_FAILED);
		UNABLE_CLIENT = res.getString(UNABLE_CLIENT);
		UNABLE_SECRET = res.getString(UNABLE_SECRET);
	}
}
