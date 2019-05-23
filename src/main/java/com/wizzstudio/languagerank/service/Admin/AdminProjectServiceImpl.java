package com.wizzstudio.languagerank.service.Admin;

import com.wizzstudio.languagerank.DAO.clazzDAO.ClazzDAO;
import com.wizzstudio.languagerank.DTO.admin.AdminProjectDTO;
import com.wizzstudio.languagerank.DTO.admin.AdminTotalDTO;
import com.wizzstudio.languagerank.VO.AdminProjectListVO;
import com.wizzstudio.languagerank.VO.ClazzMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProjectServiceImpl implements AdminProjectService{

    @Autowired
    ClazzDAO clazzDAO;

    @Override
    public AdminProjectListVO getAllProject(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        List<AdminProjectDTO> adminProjectDTOPage = clazzDAO.findAllClazzBack(pageRequest);

        AdminProjectListVO adminProjectListVO = null;
        adminProjectListVO.setAdminProjectDTOList(adminProjectDTOPage);
        adminProjectListVO.setTotal(adminProjectDTOPage.size());
        adminProjectListVO.setPageIndex(page);
        adminProjectListVO.setPageSize(size);
        return adminProjectListVO;
    }

    @Override
    public AdminTotalDTO getProjectTotalNumber(Integer id) {
        ClazzMessageVO clazzMessageVO = clazzDAO.getClazzMessage(id);
        Integer userNumber = clazzMessageVO.getStudentNumber();   //总人数
        Integer commentNumber = clazzMessageVO.getCommentNumber();
//        Integer workShipNumber = clazzMessageVO.getWorkShipNumber();  //膜拜数

        AdminTotalDTO adminTotalDTO = null;
        adminTotalDTO.setUserNumber(userNumber);
        adminTotalDTO.setCommentNumber(commentNumber);
//        adminTotalDTO.setWorkShipNumber(workShipNumber);
        return adminTotalDTO;
    }
}
