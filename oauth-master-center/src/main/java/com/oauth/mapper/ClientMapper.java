package com.oauth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.oauth.pojo.Client;

@Mapper
public interface ClientMapper {

    @Select("SELECT * FROM mc_client WHERE client_code=#{clientCode}")
    Client selectClientByClientCode(String clientCode);
}
