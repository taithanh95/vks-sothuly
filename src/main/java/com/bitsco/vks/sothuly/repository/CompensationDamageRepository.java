package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.CompensationDamage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompensationDamageRepository extends JpaRepository<CompensationDamage, Long> {

    List<CompensationDamage> findByCompensationIdAndStatus(Long compensationId, Integer status);
}
