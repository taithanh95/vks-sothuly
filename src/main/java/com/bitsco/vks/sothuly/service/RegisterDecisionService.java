package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.common.response.ReportResponse;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.sothuly.entities.RegisterDecision;
import com.bitsco.vks.sothuly.entities.RegisterDecisionNum;
import com.bitsco.vks.sothuly.request.RegisterDecisionRequest;

import java.util.List;

public interface RegisterDecisionService {
    RegisterDecision createRegisterDecisionAccused(RegisterDecision registerDecision) throws Exception;

    RegisterDecision updateRegisterDecisionAccused(RegisterDecision registerDecision) throws Exception;

    RegisterDecision deleteRegisterDecisionAccused(RegisterDecision registerDecision) throws Exception;

    RegisterDecision findRegisterDecisionAccusedById(RegisterDecision registerDecision) throws Exception;

    List<RegisterDecision> getListByAccusedCode(RegisterDecisionRequest registerDecisionRequest) throws Exception;

    List<RegisterDecision> getList(RegisterDecisionRequest registerDecisionRequest) throws Exception;

    PageResponse getPage(PageRequest pageRequest) throws Exception;

    Integer getDecisionNum(RegisterDecisionNum registerDecisionNum) throws Exception;

    Response checkRegisterDecision(String req, Integer type) throws Exception;
}
