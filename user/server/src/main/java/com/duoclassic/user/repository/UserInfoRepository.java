package com.duoclassic.user.repository;

import com.duoclassic.user.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo ,String> {

    UserInfo findByOpenid(String openid);
}
