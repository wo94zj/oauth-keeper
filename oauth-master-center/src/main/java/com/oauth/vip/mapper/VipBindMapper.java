package com.oauth.vip.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.oauth.vip.pojo.VipBind;

@Mapper
public interface VipBindMapper {

	@Select("SELECT * FROM mc_account_bind WHERE account=#{account} AND client_id=#{clientId}")
    VipBind selectVipBindByClientIdAndAccount(@Param("clientId")String clientId, @Param("account")String account);
	
	@Insert("INSERT INTO mc_account_bind(account,client_id,authority,auth_start_time,auth_stop_time,update_time,create_time) "
			+ "VALUES(#{account},#{clientId},#{authority},#{authStartTime},#{authStopTime},#{updateTime},#{createTime})")
	int insertVipBind(VipBind vipBind);
}
