package com.oauth.vip.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.oauth.vip.pojo.VipBind;

@Mapper
public interface VipBindMapper {

	@Select("SELECT * FROM mc_account_bind WHERE account_id=#{accountId} AND client_code=#{clientCode}")
    VipBind selectVipBindByClientCodeAndAccountId(@Param("clientCode")String clientCode, @Param("accountId")long accountId);
	
	@Insert("INSERT INTO mc_account_bind(account_id,client_code,authority,auth_start_time,auth_stop_time,update_time,create_time) "
			+ "VALUES(#{accountId},#{clientCode},#{authority},#{authStartTime},#{authStopTime},#{updateTime},#{createTime})")
	int insertVipBind(VipBind vipBind);
}
