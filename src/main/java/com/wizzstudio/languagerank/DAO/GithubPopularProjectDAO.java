package com.wizzstudio.languagerank.DAO;

/*
Created by Ben Wen on 2019/5/22.
*/

import com.wizzstudio.languagerank.domain.GithubPopularProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GithubPopularProjectDAO extends JpaRepository<GithubPopularProject, Integer> {
    List<GithubPopularProject> findByProjectTag(String projectTag);
}
