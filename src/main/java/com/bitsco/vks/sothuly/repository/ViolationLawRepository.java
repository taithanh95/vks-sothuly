package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.ViolationLaw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ViolationLawRepository extends JpaRepository<ViolationLaw, Long> {
}
