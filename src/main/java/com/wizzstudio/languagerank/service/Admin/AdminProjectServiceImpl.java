package com.wizzstudio.languagerank.service.Admin;

import com.wizzstudio.languagerank.DAO.clazzDAO.ClazzDAO;
import com.wizzstudio.languagerank.DAO.clazzDAO.ClazzStudyPlanDAO;
import com.wizzstudio.languagerank.DAO.clazzDAO.UserClazzDAO;
import com.wizzstudio.languagerank.DTO.admin.AdminProjectDTO;
import com.wizzstudio.languagerank.DTO.admin.AdminProjectStudyPlanDTO;
import com.wizzstudio.languagerank.DTO.admin.AdminTotalDTO;
import com.wizzstudio.languagerank.VO.AdminProjectListVO;
import com.wizzstudio.languagerank.VO.AdminProjectStudyPlanVO;
import com.wizzstudio.languagerank.VO.ClazzMessageVO;
import com.wizzstudio.languagerank.domain.clazz.ClazzStudyPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProjectServiceImpl implements AdminProjectService{

    @Autowired
    ClazzDAO clazzDAO;
    @Autowired
    ClazzStudyPlanDAO clazzStudyPlanDAO;
    @Autowired
    UserClazzDAO userClazzDAO;

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

    @Override
    public AdminProjectStudyPlanVO getAdminProjectPlan(Integer id) {
        AdminProjectStudyPlanVO adminProjectStudyPlanVO = null;
        adminProjectStudyPlanVO.setAdminProjectStudyPlanDTOList(clazzStudyPlanDAO.getClazzStudyAllPlan(id));
        adminProjectStudyPlanVO.setClazzName(clazzDAO.findByClazzId(id).getClazzName());
        adminProjectStudyPlanVO.setMonitor(clazzDAO.findByClazzId(id).getMonitor());
        adminProjectStudyPlanVO.setClazzImage(clazzDAO.findByClazzId(id).getClazzImage());
        return adminProjectStudyPlanVO;
    }

    @Override
    public void saveAdminProjectStudyPlanVO(AdminProjectStudyPlanVO adminProjectStudyPlanVO,Integer clazzId) {
            clazzDAO.updateAdminClazz(adminProjectStudyPlanVO.getClazzName(),adminProjectStudyPlanVO.getMonitor(),
                    adminProjectStudyPlanVO.getClazzImage(),clazzId);
            List<AdminProjectStudyPlanDTO>  adminProjectStudyPlanDTOList = adminProjectStudyPlanVO.getAdminProjectStudyPlanDTOList();

            for (AdminProjectStudyPlanDTO adminProjectStudyPlanDTO : adminProjectStudyPlanDTOList){
                clazzStudyPlanDAO.updateClazzStudyPlan(adminProjectStudyPlanDTO.getBriefIntroduction(), adminProjectStudyPlanDTO.getContent(),
                        adminProjectStudyPlanDTO.getLink(),adminProjectStudyPlanDTO.getQrCode(), clazzId, adminProjectStudyPlanDTO.getStudyPlanDay());
            }
    }

    @Override
    public void deleteAdminClazzAndPlan(Integer clazzId) {
        try {
            userClazzDAO.deleteUserClazz(clazzId);
            clazzDAO.deleteClazz(clazzId);
            clazzStudyPlanDAO.deleteClazzStudyPlan(clazzId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
