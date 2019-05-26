package com.wizzstudio.languagerank.DAO.userDAO;

/*
Created by Ben Wen on 2019/3/16.
*/

import com.wizzstudio.languagerank.DTO.admin.AdminUserInfoDTO;
import com.wizzstudio.languagerank.domain.clazz.Clazz;
import com.wizzstudio.languagerank.domain.user.User;
import com.wizzstudio.languagerank.DTO.HardworkingRankDTO;
import com.wizzstudio.languagerank.DTO.PopularityRankDTO;
import com.wizzstudio.languagerank.VO.UserPunchCardMessageTodayVO;
import com.wizzstudio.languagerank.enums.PunchReminderTimeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByOpenId(String openId);

    User findByUserId(Integer userId);

    User findByNickName(String name);
    /**
     *     获取所有用户的部分信息，后台
     */
    @Query("select new com.wizzstudio.languagerank.DTO.admin.AdminUserInfoDTO(u.userId, u.nickName, u.totalScore,u.totalPunchCardDay ,u.todayPunchCardScore, u.todayWorshipScore)  from User as u ")
    List<AdminUserInfoDTO> findAllUser(PageRequest pageRequest);
//  总膜拜数
    @Query(nativeQuery = true, value = "select sum(worship) from user")
    Integer getworkshipnumber();

    @Query(nativeQuery = true, value = "select userId from user")
    Integer getTotalNumber();

    /**
     *  查询设置为在reminderTime点时进行消息推送的用户中今日还未打卡的用户
     */
    @Query("select u from User u where u.reminderTime = :reminderTime and u.isPunchCardToday = false")
    List<User> findUsersNotPunchCardTodayAndRemindAtWhen(@Param("reminderTime")PunchReminderTimeEnum reminderTime);

    @Modifying
    @Query("update User u set u.isLogInToday = false")
    void resetIsLogInToday();

    @Modifying
    @Query("update User u set u.isLogInToday = true where u.userId = :userId")
    void updateIsLogInToday(@Param("userId") Integer userId);

//    @Modifying
//    @Query("update user u set u.studyPlanDay = :studyPlanDay where u.userId = :userId")
//    void updateStudyPlanDay(@Param("studyPlanDay") StudyPlanDayEnum studyPlanDay, @Param("userId") Integer userId);

//    @Modifying
//    @Query("update user u set u.myLanguage = :myLanguage where u.userId = :userId")
//    void updateMyLanguage(@Param("myLanguage") String myLanguage, @Param("userId") Integer userId);

    @Modifying
    @Query("update User u set u.isViewedJoinMyApplet = false where u.userId = :userId")
    void updateIsViewedJoinMyApplet(@Param("userId") Integer userId);

    @Modifying
    @Query("update User u set u.logInLastTime = :logInLastTime where u.userId = :userId")
    void updateLogInLastTime(@Param("logInLastTime") Date logInLastTime, @Param("userId") Integer userId);

    @Query("select u.clazzList from User u where u.userId = :userId")
    List<Clazz> findUserClazz(@Param("userId") Integer userId);

//    @Modifying
//    @Query("update user u set u.commentDisplayMode = :commentDisplayMode where u.userId = :userId")
//    void updateCommentDisplayMode(@Param("commentDisplayMode")CommentDisplayModeEnum commentDisplayMode, @Param("userId") Integer userId);

    @Query("select new com.wizzstudio.languagerank.VO.UserPunchCardMessageTodayVO" +
            "(u.avatarUrl, u.totalScore, u.totalPunchCardDay, u.todayScore) from User u where  u.userId = :userId")
    UserPunchCardMessageTodayVO findUserPunchCardMessageToday(@Param("userId") Integer userId);

    /**
     * 查询勤奋排行榜该用户信息
     */
    @Query("select new com.wizzstudio.languagerank.DTO.HardworkingRankDTO(u.userId, u.nickName, u.avatarUrl, u.todayScore) from User u where u.userId = :userId")
    HardworkingRankDTO findMyHardworking(@Param("userId") Integer userId);

    /**
     * 查询勤奋排行榜所有用户信息
     */
    @Query("select new com.wizzstudio.languagerank.DTO.HardworkingRankDTO(u.userId, u.nickName, u.avatarUrl, u.todayScore) from User u where u.userId in (:userIdList)")
    Page<HardworkingRankDTO> findHardingworkingRank(Pageable pageable, @Param("userIdList")List<Integer> userIdList);

    /**
     * 查询人气排行榜该用户信息
     */
    @Query("select new com.wizzstudio.languagerank.DTO.PopularityRankDTO(u.userId, u.nickName, u.avatarUrl, u.worship) from User u where u.userId = :userId")
    PopularityRankDTO findMyPopularity(@Param("userId") Integer userId);

    /**
     * 查询人气排行榜所有用户信息
     */
    @Query("select new com.wizzstudio.languagerank.DTO.PopularityRankDTO(u.userId, u.nickName, u.avatarUrl, u.worship) from User u where u.userId in (:userIdList)")
    Page<PopularityRankDTO> findPopularityRank(Pageable pageable, @Param("userIdList")List<Integer> userIdList);
}