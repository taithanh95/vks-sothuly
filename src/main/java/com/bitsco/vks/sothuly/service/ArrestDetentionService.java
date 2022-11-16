package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.sothuly.entities.ArrestDetentionInfo;
import com.bitsco.vks.sothuly.entities.ArrestSettlementDecision;
import com.bitsco.vks.sothuly.entities.Arrestee;
/**
 * @Author phucnv
 * @create 4/8/2021 1:31 PM
 */
public interface ArrestDetentionService {
    ArrestDetentionInfo createArrestDetention(ArrestDetentionInfo detentionInfo) throws Exception;
    ArrestSettlementDecision createSettlementDecision(ArrestSettlementDecision arrestSettlementDecision) throws Exception;
    ArrestDetentionInfo getArrestDetentionInfoById(Long id,String sppId) throws Exception;
    ArrestDetentionInfo updateArrestDetentionInfo(ArrestDetentionInfo arrestDetentionInfo,String sppId) throws Exception;
    Arrestee saveArrestee(Arrestee arrestee) throws Exception;
    void delete(Long id, String sppId) throws Exception;

    PageResponse search(PageRequest pageRequest) throws Exception;
}
