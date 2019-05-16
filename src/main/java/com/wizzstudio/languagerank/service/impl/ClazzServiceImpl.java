package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.VO.AllClazzVO;
import com.wizzstudio.languagerank.DTO.ClazzMemberDTO;
import com.wizzstudio.languagerank.VO.ClazzMessageVO;
import com.wizzstudio.languagerank.VO.UserPunchCardMessageTodayVO;
import com.wizzstudio.languagerank.constants.Constant;
import com.wizzstudio.languagerank.DAO.clazzDAO.ClazzDAO;
import com.wizzstudio.languagerank.DAO.clazzDAO.ClazzStudyPlanDAO;
import com.wizzstudio.languagerank.DAO.clazzDAO.UserClazzDAO;
import com.wizzstudio.languagerank.DAO.clazzDAO.UserJoinedClazzDAO;
import com.wizzstudio.languagerank.DAO.userDAO.UserDAO;
import com.wizzstudio.languagerank.domain.clazz.Clazz;
import com.wizzstudio.languagerank.domain.clazz.ClazzStudyPlan;
import com.wizzstudio.languagerank.domain.clazz.UserClazz;
import com.wizzstudio.languagerank.domain.user.User;
import com.wizzstudio.languagerank.DTO.*;
import com.wizzstudio.languagerank.service.ClazzService;
import com.wizzstudio.languagerank.util.DateUtil;
import com.wizzstudio.languagerank.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    ClazzDAO clazzDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserClazzDAO userClazzDAO;
    @Autowired
    UserJoinedClazzDAO userJoinedClazzDAO;
    @Autowired
    ClazzStudyPlanDAO clazzStudyPlanDAO;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public Clazz createClazz(CreateClazzDTO createClazzDTO) {
        Clazz clazz = new Clazz();
        clazz.setClazzName(createClazzDTO.getClazzName());
        clazz.setMonitor(createClazzDTO.getMonitor());
        clazz.setClazzTag(createClazzDTO.getClazzTag());
        clazz.setClazzImage(createClazzDTO.getClazzImage());
        clazz.setStudentNumber(1);
        clazz.setCommentNumber(0);

        clazzDAO.save(clazz);
        ListIterator listIterator = createClazzDTO.getClazzStudyPlanList().listIterator();
        while (listIterator.hasNext()) {
            ClazzStudyPlan clazzStudyPlan = (ClazzStudyPlan)listIterator.next();
            clazzStudyPlan.setClazzId(clazz.getClazzId());
        }
        clazzStudyPlanDAO.saveAll(createClazzDTO.getClazzStudyPlanList());

        joinClazz(createClazzDTO.getMonitor(), clazz.getClazzId());

        return clazz;
    }

    @Override
    public List<UserClazzDTO> getUserClazzList(Integer userId) {
        List<Clazz> clazzList = userDAO.findUserClazz(userId);
        List<UserClazzDTO> userClazzListDTOList = new ArrayList<>();
        // 更大的网络包与更长的执行时间如何选择
        for (Clazz clazz : clazzList) {
            UserClazzDTO userClazzDTO = new UserClazzDTO();
            userClazzDTO.setClazzImage(clazz.getClazzImage());
            userClazzDTO.setClazzName(clazz.getClazzName());
            userClazzDTO.setMonitor(clazz.getMonitor());
            userClazzDTO.setClazzId(clazz.getClazzId());

            userClazzListDTOList.add(userClazzDTO);
        }
        return userClazzListDTOList;
    }

    @Override
    public List<Integer> findUserJoinedClazz(Integer userId) {
        return userClazzDAO.findUserJoinedClazz(userId);
    }

    @Override
    public List<AllClazzVO> getAllClazzList() {
        return clazzDAO.findAllClazz();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void joinClazz(Integer userId, Integer clazzId) {
        UserClazz userClazz = new UserClazz();
        userClazz.setUserId(userId);
        userClazz.setClazzId(clazzId);
        userClazz.setJoinedTime(new Date());
        userClazz.setAllStudyPlanDay(1);
        // 还有问题，当用户一天重复加入某一班级时;
        userClazz.setUninterruptedStudyPlanDay(0);
        userClazz.setStudyTime(null);
        userClazz.setIsStudyToday(false);

        userClazzDAO.save(userClazz);
    }

    // 重复加入班级的一串逻辑最后再加
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void quitClazz(Integer userId, Integer clazzId) {
        UserClazz userClazz = userClazzDAO.findByClazzIdAndUserId(clazzId, userId);
//        UserJoinedClazz userJoinedClazz = new UserJoinedClazz();
//        userJoinedClazz.setUserId(userId);
//        userJoinedClazz.setJoinedClazzId(clazzId);
//        userJoinedClazz.setIsJoinedToday(true);
//        userJoinedClazz.setStudyPlanDay(userClazz.getAllStudyPlanDay());
//        userJoinedClazzDAO.save(userJoinedClazz);
        userClazzDAO.delete(userClazz);
    }

    @Override
    public ClazzMessageVO getClazzMessage(Integer userId, Integer clazzId) {
        ClazzMessageVO clazzMessage = clazzDAO.getClazzMessage(clazzId);
        List<Integer> userClazzList = findUserJoinedClazz(userId);
        if (userClazzList.contains(clazzId)) {
            clazzMessage.setIsInClazz(true);
        } else {
            clazzMessage.setIsInClazz(false);
        }

        return clazzMessage;
    }

    @Override
    public List<String> getClazzStudyPlan(Integer clazzId) {
        return clazzStudyPlanDAO.getAllBriefIntroduction(clazzId);
    }

    @Override
    public List<ClazzStudyPlan> getUserClazzStudyPlan(Integer userId, Integer clazzId) {
        UserClazz userClazz = userClazzDAO.findByClazzIdAndUserId(clazzId, userId);
        return clazzStudyPlanDAO.findByClazzIdAndStudyPlanDayLessThanEqualOrderByStudyPlanDayAsc(clazzId, userClazz.getAllStudyPlanDay());
    }

    @Override
    public String getUserClazzStudyPlanToday(Integer userId, Integer clazzId) {
        UserClazz userClazz = userClazzDAO.findByClazzIdAndUserId(clazzId, userId);
        return clazzStudyPlanDAO.findQRCodeToday(clazzId, userClazz.getAllStudyPlanDay());
    }

    @Override
    public Map<String, Object> getSpecialClazzMember(Integer userId, Integer clazzId) {
        Map<String, Object> clazzMemberMap = new HashMap<>();

        ClazzMemberDTO monitor = new ClazzMemberDTO();
        User monitorDomain = userDAO.findByUserId(clazzDAO.findMonitorByClazzId(clazzId));
        monitor.setUserId(monitorDomain.getUserId());
        monitor.setNickName(monitorDomain.getNickName());
        monitor.setAvatarUrl(monitorDomain.getAvatarUrl());
        clazzMemberMap.put("monitor", monitor);

        List<String> stringList = new ArrayList<>(redisUtil.getUserRelationship(userId));
        List<Integer> friendIdList = stringList.stream().map(Integer::parseInt).collect(Collectors.toList());
        // 求用户好友List与班级学员List的交集
        // 用户数较大时性能降低严重，考虑用将班级学员写入redis再求交集
        friendIdList.retainAll(userClazzDAO.findAllUserIdInClazz(clazzId));
        List<ClazzMemberDTO> friendList = new ArrayList<>();
        for (Integer friendId : friendIdList) {
            ClazzMemberDTO friend = new ClazzMemberDTO();
            User friendDomain = userDAO.findByUserId(friendId);
            friend.setUserId(friendDomain.getUserId());
            friend.setAvatarUrl(friendDomain.getAvatarUrl());
            friend.setNickName(friendDomain.getNickName());
            friendList.add(friend);
        }
        clazzMemberMap.put("friend", friendList);

        return clazzMemberMap;
    }

    @Override
    public Map<String, Object> getClazzMember(Integer clazzId, Integer pageIndex) {
        // 按连续打卡天数降序排列
        List<UserClazz> memberList =  userClazzDAO.findAll(
                (Specification<UserClazz>) (root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("clazzId"), clazzId),
                PageRequest.of(pageIndex - 1, Constant.COMMENT_PAGE_SIZE,
                       Sort.Direction.DESC, "uninterruptedStudyPlanDay"))
                .getContent();

        List<ClazzMemberDTO> clazzMemberDTOList = new ArrayList<>();
        for (UserClazz userClazz : memberList) {
            User user = userDAO.findByUserId(userClazz.getUserId());
            ClazzMemberDTO clazzMemberDTO = new ClazzMemberDTO();

            clazzMemberDTO.setJoinedTime(userClazz.getJoinedTime());
            clazzMemberDTO.setUninterruptedStudyPlanDay(userClazz.getUninterruptedStudyPlanDay());
            clazzMemberDTO.setUserId(user.getUserId());
            clazzMemberDTO.setNickName(user.getNickName());
            clazzMemberDTO.setAvatarUrl(user.getAvatarUrl());

            clazzMemberDTOList.add(clazzMemberDTO);
        }

        Map<String, Object> clazzMemberMap = new HashMap<>();
        clazzMemberMap.put("members", clazzMemberDTOList);
        clazzMemberMap.put("total", userClazzDAO.getTheNumberOfStudents(clazzId));
        clazzMemberMap.put("pageIndex", pageIndex);
        clazzMemberMap.put("pageSize", Constant.RANK_PAGE_SIZE);

        return clazzMemberMap;
    }

    // 每天重置isStudyPlanToday，每天根据isStudyPlanToday决定是否重置uninterruptedStudyPlanDay
    @Override
    public void punchCard(Integer userId, Integer clazzId, String formId) {
        UserClazz userClazz = userClazzDAO.findByClazzIdAndUserId(clazzId, userId);

        // 修改用户在该班级打卡情况
        userClazz.setIsStudyToday(true);
        userClazz.setStudyTime(new Date());
        userClazz.setAllStudyPlanDay(userClazz.getAllStudyPlanDay() + 1);
        userClazz.setUninterruptedStudyPlanDay(userClazz.getUninterruptedStudyPlanDay() + 1);

        User user = userDAO.findByUserId(userId);
        // 修改用户总体打卡情况
        if (!user.getIsPunchCardToday()) {
            user.setIsPunchCardToday(true);
            user.setTotalPunchCardDay(user.getTotalPunchCardDay() + 1);
        }

        // 修改用户积分情况
        if (user.getTodayScore() <= Constant.SCORE_UPPER_LIMIT_TODAY) {
            Integer todayPunchCardScore;
            if (compareNowAndTwelve()) {
                todayPunchCardScore = userClazz.getUninterruptedStudyPlanDay() * 10;
            } else {
                todayPunchCardScore = userClazz.getUninterruptedStudyPlanDay() * 5;
            }
            user.setTodayPunchCardScore(user.getTotalPunchCardScore() + todayPunchCardScore);
            user.setTodayScore(user.getTodayScore() + todayPunchCardScore);
            user.setTotalPunchCardScore(user.getTotalPunchCardScore() + todayPunchCardScore);
            user.setTotalScore(user.getTotalScore());
        }

        // 设置新的模板消息推送formId
        user.setFormId(formId);
    }

    @Override
    public UserPunchCardMessageTodayVO getUserPunchCardMessageToday(Integer userId, Integer clazzId) {
        UserPunchCardMessageTodayVO message = userDAO.findUserPunchCardMessageToday(userId);
        message.setUninterruptedStudyPlanDay(userClazzDAO.findByClazzIdAndUserId(clazzId, userId).getUninterruptedStudyPlanDay());
        return message;
    }

    @Override
    public Map<String, Object> getHardWorkingRank(Integer userId, Integer clazzId, Integer pageIndex) {
        Map<String, Object> hardworkingRankMap = new HashMap<>();
        hardworkingRankMap.put("me", userDAO.findMyHardworking(userId));

        Pageable pageable = PageRequest.of(pageIndex - 1, Constant.RANK_PAGE_SIZE, Sort.Direction.DESC, "todayScore");
        Page<HardworkingRankDTO> page = userDAO.findHardingworkingRank(pageable, userClazzDAO.findAllUserIdInClazz(clazzId));

        hardworkingRankMap.put("members", page.getContent());
        hardworkingRankMap.put("total", userClazzDAO.getTheNumberOfStudents(clazzId));
        hardworkingRankMap.put("pageIndex", pageIndex);
        hardworkingRankMap.put("pageSize", Constant.RANK_PAGE_SIZE);

        return hardworkingRankMap;
    }

    @Override
    public Map<String, Object> getPopularityRank(Integer userId, Integer clazzId, Integer pageIndex) {
        Map<String, Object> popularityRankMap = new HashMap<>();
        popularityRankMap.put("me", userDAO.findMyPopularity(userId));

        Pageable pageable = PageRequest.of(pageIndex - 1, Constant.RANK_PAGE_SIZE, Sort.Direction.DESC, "worship");
        Page<PopularityRankDTO> page = userDAO.findPopularityRank(pageable, userClazzDAO.findAllUserIdInClazz(clazzId));

        popularityRankMap.put("members", page.getContent());
        popularityRankMap.put("total", userClazzDAO.getTheNumberOfStudents(clazzId));
        popularityRankMap.put("pageIndex", pageIndex);
        popularityRankMap.put("pageSize", Constant.RANK_PAGE_SIZE);

        return popularityRankMap;
    }

    /**
     * 比较当前时间与当日12点时间
     * @return 若当前时间在12点之前返回true，反之返回false
     */
    private Boolean compareNowAndTwelve() {
        Date date = new Date();
        if (date.compareTo(DateUtil.getTwelveToday()) < 0) {
            return true;
        } else {
            return false;
        }
    }
}
