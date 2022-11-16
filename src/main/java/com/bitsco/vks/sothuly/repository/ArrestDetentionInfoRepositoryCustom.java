package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.ArrestDetentionInfo;
import com.bitsco.vks.sothuly.model.ArrestDetentionInfoDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @Author phucnv
 * @create 4/9/2021 2:22 PM
 */
public interface ArrestDetentionInfoRepositoryCustom {
    Long getArrestDetentionInfoCodeInYear(int year) throws Exception;
    Optional<ArrestDetentionInfo> findByIdCustom(@Param("id") Long id) throws Exception;
}
