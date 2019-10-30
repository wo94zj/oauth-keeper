package com.oauth.web.config;

import java.io.Serializable;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.exception.CommonException;
import com.oauth.resp.BaseDto;
import com.oauth.resp.ResultCode;
import com.oauth.resp.ResultUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/error")
public class BasicExceptionController implements ErrorController {
	
	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping
	public ResponseEntity<BaseDto<Serializable>> handleError(HttpServletRequest request, HttpServletResponse response) {
		Object error = request.getAttribute("javax.servlet.error.exception");
		log.error("request ERROR :{}", error);
		
		if(Objects.nonNull(error) && error instanceof CommonException) {
			CommonException exception = (CommonException) error;
			return new ResponseEntity<BaseDto<Serializable>>(ResultUtil.result(exception.getCode(), exception.getMessage()), HttpStatus.OK);
		}
		
		return new ResponseEntity<BaseDto<Serializable>>(ResultUtil.result(ResultCode.SYSTEM_ERROR), HttpStatus.OK);
	}
	
}
