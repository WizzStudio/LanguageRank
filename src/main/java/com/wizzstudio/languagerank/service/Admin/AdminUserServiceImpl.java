package com.wizzstudio.languagerank.service.Admin;

import com.wizzstudio.languagerank.DAO.clazzDAO.ClazzDAO;
import com.wizzstudio.languagerank.DAO.userDAO.UserDAO;
import com.wizzstudio.languagerank.DTO.admin.AdminTotalDTO;
import com.wizzstudio.languagerank.DTO.admin.AdminUserInfoDTO;
import com.wizzstudio.languagerank.VO.AdminUserInfoListVO;
import com.wizzstudio.languagerank.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    UserDAO userDAO;
    @Autowired
    ClazzDAO clazzDAO;

    @Override
    public AdminUserInfoListVO getUserInfoPage(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        List<AdminUserInfoDTO> adminUserInfoDTOList = userDAO.findAllUser(pageRequest);

        AdminUserInfoListVO adminUserInfoListVO =null;
        adminUserInfoListVO.setAdminUserInfoDTOList(adminUserInfoDTOList);
        adminUserInfoListVO.setTotal(adminUserInfoDTOList.size());
        adminUserInfoListVO.setPageIndex(page);
        adminUserInfoListVO.setPageSize(size);
        return adminUserInfoListVO;
    }

    @Override
    public AdminTotalDTO getTotalNumber() {
        Integer userNumber = userDAO.getTotalNumber();   //总人数
        Integer commentNumber = clazzDAO.getCommentNumber();
        Integer workShipNumber = userDAO.getworkshipnumber();

        AdminTotalDTO adminTotalDTO = null;
        adminTotalDTO.setUserNumber(userNumber);
        adminTotalDTO.setCommentNumber(commentNumber);
        adminTotalDTO.setWorkShipNumber(workShipNumber);
        return adminTotalDTO;
    }

    @Override
    public AdminUserInfoDTO getOneUser(String name) {
         User user = userDAO.findByNickName(name);
         AdminUserInfoDTO adminUserInfoDTO = null;

         adminUserInfoDTO.setId(user.getUserId());
         adminUserInfoDTO.setName(user.getNickName());
         adminUserInfoDTO.setTotalScore(user.getTotalScore());
         adminUserInfoDTO.setTotalPunchCardDay(user.getTotalPunchCardDay());
         adminUserInfoDTO.setTotalPunchCardScore(user.getTotalPunchCardScore());
         adminUserInfoDTO.setTotalWorshipScore(user.getTotalWorshipScore());
        return adminUserInfoDTO;
    }
}
