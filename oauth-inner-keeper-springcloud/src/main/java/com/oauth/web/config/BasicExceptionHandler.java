package com.oauth.web.config;

import java.io.Serializable;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.oauth.exception.CommonException;
import com.oauth.resp.BaseDto;
import com.oauth.resp.ResultCode;
import com.oauth.resp.ResultUtil;
import com.oauth.util.ExceptionUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class BasicExceptionHandler {

	/**
	 * 参数验证失败
	 */
	@ExceptionHandler(value = ValidationException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public BaseDto<Serializable> badRequestException(ValidationException ex) {
		log.error("ValidationException :{}", ExceptionUtil.asString(ex));

		return ResultUtil.result(ResultCode.BAD_REQUEST.getCode(), ex.getMessage());
	}

	/**
	 * 404
	 */
	@ExceptionHandler(value = NoHandlerFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public BaseDto<Serializable> noHandlerFoundException(NoHandlerFoundException ex) {
		log.error("noHandlerFoundException :{}", ExceptionUtil.asString(ex));

		return ResultUtil.result(ResultCode.URL_ERROR.getCode(), ex.getMessage());
	}

	/**
	 * 自定义通用异常处理
	 */
	@ExceptionHandler(value = CommonException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public BaseDto<Serializable> commonException(CommonException ex) {
		log.error("CommonException :{}", ExceptionUtil.asString(ex));

		return ResultUtil.result(ex.getCode(), ex.getMessage());
	}
	
	/**
	 * 暂时未处理的异常
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseEntity<BaseDto<Serializable>> exception(Exception ex, HttpServletResponse response) {
		log.error("Response :{}; Exception :{}", response.getStatus(), ExceptionUtil.asString(ex));

		ResponseEntity<BaseDto<Serializable>> resp = null;
		if (ex instanceof ServletRequestBindingException) {// MissingRequestHeaderException
			// 参数映射失败
			resp = new ResponseEntity<BaseDto<Serializable>>(
					ResultUtil.result(ResultCode.BAD_REQUEST.getCode(), ex.getMessage()), HttpStatus.OK);
		}

		if (ex instanceof HttpRequestMethodNotSupportedException) {
			// 请求方式不支持
			resp = new ResponseEntity<BaseDto<Serializable>>(
					ResultUtil.result(ResultCode.METHOD_ERROR.getCode(), ex.getMessage()),
					HttpStatus.OK);
		}

		if (ex instanceof NoHandlerFoundException) {
			// 没找到处理方法
			resp = new ResponseEntity<BaseDto<Serializable>>(
					ResultUtil.result(ResultCode.URL_ERROR.getCode(), ex.getMessage()), HttpStatus.OK);
		}

		if (ex instanceof HttpMediaTypeException) {
			// 参数提交格式不正确
			resp = new ResponseEntity<BaseDto<Serializable>>(
					ResultUtil.result(ResultCode.MEDIA_TYPE_ERROR.getCode(), ex.getMessage()),
					HttpStatus.OK);
		}

		if (Objects.isNull(resp)) {
			resp = new ResponseEntity<BaseDto<Serializable>>(
					ResultUtil.result(ResultCode.SYSTEM_ERROR.getCode(), ex.getMessage()),
					HttpStatus.OK);
		}
		return resp;
	}
}
