package com.bitsco.vks.sothuly.repository;
import com.bitsco.vks.sothuly.entities.ArrestDetentionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author phucnv
 * @create 4/9/2021 2:22 PM
 */
public interface ArrestDetentionInfoRepository extends JpaRepository<ArrestDetentionInfo, Long>, ArrestDetentionInfoRepositoryCustom {
}
