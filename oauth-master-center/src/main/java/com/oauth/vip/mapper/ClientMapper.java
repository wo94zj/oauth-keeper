package com.oauth.vip.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.oauth.vip.pojo.Client;

@Mapper
public interface ClientMapper {

    @Select("SELECT * FROM mc_client WHERE client_id=#{clientId}")
    Client selectClientByClientId(String clientId);
}
