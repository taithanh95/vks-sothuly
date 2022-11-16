package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.IdentifiersDenounce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by IntelliJ IDEA.
 * User: Nguyen Tai Thanh <taithanh95.dev@gmail.com>
 * Date: 23/09/2022
 * Time: 9:50 AM
 */
@RepositoryRestResource
public interface IdentifiersDenounceRepository extends JpaRepository<IdentifiersDenounce, Long> {
    @Query(value = " SELECT NVL (                                                          "
            + "            (SELECT MAX (stt) + 1                        "
            + "               FROM identifiers_denounce                              "
            + "              WHERE sppid = :sppId), "
            + "            1)                                                         "
            + "   FROM DUAL                                                           "
            , nativeQuery = true
    )
    Long getIdentifiersCode(@Param("sppId") String sppId);

    IdentifiersDenounce findFirstBySppId(String sppid);
}
