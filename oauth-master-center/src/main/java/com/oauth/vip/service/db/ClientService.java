package com.oauth.vip.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oauth.vip.mapper.ClientMapper;
import com.oauth.vip.pojo.Client;

@Component
public class ClientService {

    @Autowired
    private ClientMapper clientMapper;
    
    public Client selectClientByClientId(String clientId) {
        return clientMapper.selectClientByClientId(clientId);
    }
}
