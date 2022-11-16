package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.DenounceDenouncedPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface DenounceDenouncedPersonRepository extends JpaRepository<DenounceDenouncedPerson, Long> {
    List<DenounceDenouncedPerson> findByDenouncementIdAndStatus(Long denouncementId, boolean status);
}
