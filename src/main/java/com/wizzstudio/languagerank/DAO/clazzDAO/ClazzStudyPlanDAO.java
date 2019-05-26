package com.wizzstudio.languagerank.DAO.clazzDAO;

/*
Created by Ben Wen on 2019/4/26.
*/

import com.wizzstudio.languagerank.DTO.admin.AdminProjectStudyPlanDTO;
import com.wizzstudio.languagerank.VO.CollectionVO;
import com.wizzstudio.languagerank.domain.clazz.ClazzStudyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClazzStudyPlanDAO extends JpaRepository<ClazzStudyPlan, Integer> {
    @Query("select c.briefIntroduction from ClazzStudyPlan c where c.clazzId = :clazzId order by c.studyPlanDay ASC")
    List<String> getAllBriefIntroduction(@Param("clazzId") Integer clazzId);

    List<ClazzStudyPlan> findByClazzIdAndStudyPlanDayLessThanEqualOrderByStudyPlanDayAsc(Integer clazzId, Integer studyPlanDay);

    @Query("select c.qrCode from ClazzStudyPlan c where c.clazzId = :clazzId and c.studyPlanDay = :studyPlanDay")
    String findQRCodeToday(@Param("clazzId") Integer clazzId, @Param("studyPlanDay")Integer studyPlanDay);

    /**
     * 查询某一个clazz的学习计划
     * @param id
     */
    @Query("select new com.wizzstudio.languagerank.DTO.admin.AdminProjectStudyPlanDTO(c.briefIntroduction,c.link,c.qrCode,c.content,c.studyPlanDay) " +
            "from ClazzStudyPlan as c where c.clazzId = :id")
    List<AdminProjectStudyPlanDTO> getClazzStudyAllPlan(@Param("id") Integer id);

    /**
     * 后台更新学习计划
     * @param briefIntroduction
     * @param content
     * @param link
     * @param qrCode
     * @param clazzId
     * @param studyPlanDay
     */
    @Modifying
    @Transactional
    @Query("update ClazzStudyPlan as c set c.briefIntroduction=:briefIntroduction,c.content=:content," +
            "c.link=:link, c.qrCode=:qrCode where c.clazzId=:clazzId and c.studyPlanDay=:studyPlanDay")
    void updateClazzStudyPlan( @Param("briefIntroduction")String briefIntroduction, @Param("content") String content, @Param("link")String link,
                               @Param("qrCode")String qrCode, @Param("clazzId")Integer clazzId,@Param("studyPlanDay")Integer studyPlanDay);

    /**
     * 删除班级的学习计划，全部
     */
    @Modifying
    @Transactional
    @Query("delete from ClazzStudyPlan as c where c.clazzId=:clazzId")
    void deleteClazzStudyPlan(@Param("clazzId") Integer clazzId);

    @Query("select new com.wizzstudio.languagerank.VO.CollectionVO(c.clazzId, c.studyPlanDay, c.link, c.extractedCode, c.briefIntroduction, c.content)" +
            " from ClazzStudyPlan c where c.clazzId = :clazzId and c.studyPlanDay = :studyPlanDay")
    CollectionVO findUserCollection(@Param("clazzId") Integer clazzId, @Param("studyPlanDay")Integer studyPlanDay);
}
