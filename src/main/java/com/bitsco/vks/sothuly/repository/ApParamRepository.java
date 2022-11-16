package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.ApParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApParamRepository extends JpaRepository<ApParam, Long> {
    List<ApParam> findApParamsByParamCodeAndStatus(String code, Integer status);
}
