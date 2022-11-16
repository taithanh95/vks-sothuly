package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.Arrestee;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArresteeRepositoryCustom {
    Optional<Arrestee> findByIdCustom(@Param("id") Long id) throws Exception;
}
