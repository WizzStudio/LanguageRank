package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/3/9.
*/

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.wizzstudio.languagerank.dao.UserDAO;
import com.wizzstudio.languagerank.domain.User;
import com.wizzstudio.languagerank.dto.WxInfo;
import com.wizzstudio.languagerank.dto.WxLogInDTO;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;
import com.wizzstudio.languagerank.service.UserService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService{

    @Autowired
    private WxMaService wxService;

    @Autowired
    private UserDAO userDAO;

    @Override
    public WxLogInDTO userLogin(WxInfo loginData) throws WxErrorException {

        // 通过code获取用户openId与session_key
        WxMaJscode2SessionResult sessionResult = wxService.getUserService().getSessionInfo(loginData.getCode());
        // 通过openId在数据库中寻找是否存在该用户，不存在则写入数据库
        User user = findByOpenId(sessionResult.getOpenid());
        if (user == null) {
            saveUser(sessionResult.getOpenid());
        }

        WxLogInDTO wxLogInDTO = new WxLogInDTO();
        wxLogInDTO.setOpenId(sessionResult.getOpenid());
        wxLogInDTO.setSession_key(sessionResult.getSessionKey());

        return wxLogInDTO;
    }

    // 只通过openId新增用户，myLanguage默认为空，studyPlanDay默认为FIRST_DAY
    @Override
    public User saveUser(String openId) {
        User user = new User();
        user.setOpenId(openId);
        user.setStudyPlanDay(StudyPlanDayEnum.FIRST_DAY.toString());
        return userDAO.save(user);
    }

    @Override
    public User findByOpenId(String openid) {
        return userDAO.findByOpenId(openid);
    }

//    @Override
//    public User findUserById(String id) {
//        return null;
//    }
}
