package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.DenounceDenouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

@RepositoryRestResource
public interface DenounceDenouncementRepository extends JpaRepository<DenounceDenouncement, Long> {
    @Query(value =  " SELECT NVL (                                                          "
            + "            (SELECT MAX (denouncement_code) + 1                        "
            + "               FROM denounce_denouncement                              "
            + "              WHERE     taken_over_date >= TRUNC (:takenOverDate, 'YEAR')     "
            + "                    AND taken_over_date <                              "
            + "                            TRUNC (ADD_MONTHS (:takenOverDate, 12), 'YEAR')), "
            + "            1)                                                         "
            + "   FROM DUAL                                                           "
            , nativeQuery = true
    )
    Long getDenounceCodeInYear(@Param("takenOverDate") Date takenOverDate);

    DenounceDenouncement findByIdAndStatus (Long id, Boolean status);
}
