package com.duoclassic.user.service;

import com.duoclassic.user.dataobject.UserInfo;

public interface UserService {

    UserInfo findByOpenid(String openid);
}
