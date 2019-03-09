package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/3/9.
*/

import com.wizzstudio.languagerank.domain.User;
import com.wizzstudio.languagerank.dto.WxInfo;
import com.wizzstudio.languagerank.dto.WxLogInDTO;
import me.chanjar.weixin.common.error.WxErrorException;

public interface UserService {

    /**
     * 用户登录
     * @param loginData 用户登录临时凭证code
     * @return 用户openId与session_key
     */
    WxLogInDTO userLogin(WxInfo loginData) throws WxErrorException;

    /**
     * 新增用户信息
     *
     * @param openId 用户openId
     */
    void saveUser(String openId);

    /**
     * 通过openId获取用户信息
     *
     * @param openid 用户openid
     */
    User findUserByOpenId(String openid);

    /**
     * 通过Id获取用户信息
     */
    User findUserById(String id);
}
