package com.duoclassic.user.service.impl;

import com.duoclassic.user.dataobject.UserInfo;
import com.duoclassic.user.repository.UserInfoRepository;
import com.duoclassic.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoImpl implements UserService {

    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserInfo findByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
