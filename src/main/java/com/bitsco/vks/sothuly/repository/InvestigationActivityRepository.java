package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.InvestigationActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface InvestigationActivityRepository extends JpaRepository<InvestigationActivity, Long> {
    List<InvestigationActivity> findByDenouncementIdAndStatus(Long denouncementId, int status);
}
