package com.wizzstudio.languagerank.service;

/*
Created by Ben Wen on 2019/4/11.
*/

import java.io.IOException;

public interface PosterService {
    /**
     * 生成邀请卡
     */
    String invitationCard(Integer userId, Integer clazzId) throws IOException;

    /**
     * 生成成就卡
     */
    String achievementCard(Integer userId, Integer clazzId) throws IOException;
}
