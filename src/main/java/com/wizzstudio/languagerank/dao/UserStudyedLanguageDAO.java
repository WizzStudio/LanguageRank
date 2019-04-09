package com.wizzstudio.languagerank.dao;

/*
Created by Ben Wen on 2019/3/26.
*/

import com.wizzstudio.languagerank.domain.UserStudyedLanguage;
import com.wizzstudio.languagerank.enums.StudyPlanDayEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserStudyedLanguageDAO extends JpaRepository<UserStudyedLanguage, Integer> {
    @Query("select u from UserStudyedLanguage u where u.userId = :userId")
    List<UserStudyedLanguage> findStudyedLanguageByUserId(@Param("userId") Integer userId);

    @Modifying
    @Query("update UserStudyedLanguage u set u.studyPlanDay = :studyPlanDay where u.studyedLanguage = :languageName and u.userId = :userId")
    void updateStudyPlanDayByLanguageNameAndUserId(
            @Param("studyPlanDay") StudyPlanDayEnum studyPlanDay,
            @Param("languageName") String languageName,
            @Param("userId") Integer userId);

    @Modifying
    @Query("update UserStudyedLanguage u set u.isStudyedToday = true where u.studyedLanguage = :languageName and u.userId = :userId")
    void updateIsStudyedTodayByLanguageNameAndUserId(@Param("languageName") String languageName, @Param("userId") Integer userId);

    @Modifying
    @Query("update UserStudyedLanguage u set u.isStudyedToday = false")
    void resetIsStudyedToday();
}
