package com.wizzstudio.languagerank.dao;

import com.wizzstudio.languagerank.domain.FixedFinalExponent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageTendDAO extends JpaRepository<FixedFinalExponent, Integer > {

    List<FixedFinalExponent> findByLanguageName(String LanguageName);



}
