package com.oauth.vip.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.oauth.vip.pojo.Vip;

@Mapper
public interface VipMapper {

    @Select("SELECT * FROM mc_account WHERE phone=#{phone}")
    Vip selectVipByPhone(@Param("phone")String phone);
    
    //@Select("SELECT t1.nickname,t1.account,t1.img,t2.authority FROM  mc_account t1,mc_account_bind t2 WHERE t1.account=t2.account AND t1.account=#{account} AND t1.level=1 AND t2.client_id=#{clientId}")
    //Account selectAccountByClientIdAndAccount(@Param("clientId")String clientId, @Param("account")String account);
    
    //@Select("SELECT t1.* FROM  mc_account t1,mc_account_bind t2 WHERE t1.account=t2.account AND t1.account=#{account} AND t1.level=1 AND t2.client_id=#{clientId}")
    //Vip selectVipByClientIdAndAccount(@Param("clientId")String clientId, @Param("account")String account);
    
    @Insert("INSERT IGNORE INTO mc_account(nickname,img,password,salt,phone,level,status,update_time,create_time) "
    		+ "VALUES(#{nickname},#{img},#{password},#{salt},#{phone},#{level},#{status},#{updateTime},#{createTime})")
    int insertVip(Vip vip);
    
}
