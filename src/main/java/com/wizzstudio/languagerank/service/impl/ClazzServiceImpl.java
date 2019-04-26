package com.wizzstudio.languagerank.service.impl;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.dao.ClazzDAO.ClazzDAO;
import com.wizzstudio.languagerank.dao.ClazzDAO.ClazzStudyPlanDAO;
import com.wizzstudio.languagerank.dao.ClazzDAO.UserClazzDAO;
import com.wizzstudio.languagerank.dao.UserDAO.UserDAO;
import com.wizzstudio.languagerank.domain.Clazz.Clazz;
import com.wizzstudio.languagerank.domain.Clazz.ClazzStudyPlan;
import com.wizzstudio.languagerank.domain.Clazz.UserClazz;
import com.wizzstudio.languagerank.dto.ClazzDTO;
import com.wizzstudio.languagerank.dto.CreateClazzDTO;
import com.wizzstudio.languagerank.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListIterator;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    ClazzDAO clazzDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserClazzDAO userClazzDAO;
    @Autowired
    ClazzStudyPlanDAO clazzStudyPlanDAO;

    @Override
    public Clazz createClazz(CreateClazzDTO createClazzDTO) {
        Clazz clazz = new Clazz();
        clazz.setClazzName(createClazzDTO.getClazzName());
        clazz.setMonitor(createClazzDTO.getMonitor());
        clazz.setStudentNumber(1);

//        List<User> userList = new ArrayList<>();
//        userList.add(userDAO.findByUserId(createClazzDTO.getMonitor()));
//        clazz.setUserList(userList);
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
    public List<ClazzDTO> getClazzList() {
        return clazzDAO.findAllClazz();
    }

    @Override
    public void joinClazz(Integer userId, Integer clazzId) {
        UserClazz userClazz = new UserClazz();
        userClazz.setUserId(userId);
        userClazz.setClazzId(clazzId);
        // 还有问题，当用户一天重复加入某一班级时
        userClazz.setAllStudyPlanDay(1);
        userClazz.setUninterruptedStudyPlanDay(1);

        userClazzDAO.save(userClazz);
    }
}
