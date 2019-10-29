package com.oauth.inner.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.oauth.util.AESUtil;
import com.oauth.util.TimeUtil;
import com.oauth.vip.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DubboAccountServiceTest {

	@Autowired
	private AccountService dubboAccountService;
	
	@Test
	public void loginTest() {
		//DataSource
		String str = "password=123456&timestamp=" + TimeUtil.currentMilli();
		System.out.println(dubboAccountService.signLogin("zhaoj", "vc-traders", AESUtil.encrypt(str, "3381229f1beb087e92d924e65f63e649503fdb74304c632b604af9d2275ce09e")));
	}
}
