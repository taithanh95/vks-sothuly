package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.response.ResponseBody;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.bitsco.vks.sothuly.cache.CacheService;
import com.bitsco.vks.sothuly.feign.ManageServiceFeignAPI;
import com.bitsco.vks.sothuly.model.Accused;
import com.bitsco.vks.sothuly.model.Case;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageServiceImpl implements ManageService {
    @Autowired
    CacheService cacheService;

    @Autowired
    ManageServiceFeignAPI manageServiceFeignAPI;

    @Override
    public Case findCaseById(Case c) throws Exception {
        ValidateCommon.validateNullObject(c.getCaseCode(), "caseCode");
        Case response = cacheService.getCaseFromCache(c.getCaseCode());
        if (response != null) return response;
        else {
            ResponseBody responseBody = manageServiceFeignAPI.findById(c);
            if (responseBody == null || StringCommon.isNullOrBlank(responseBody.getResponseCode()) || responseBody.getResponseData() == null)
                throw new CommonException(Response.SYSTEM_ERROR, "Lỗi khi thực hiện truy vấn thông tin danh mục vụ án");
            return (new ObjectMapper()).convertValue(responseBody.getResponseData(), Case.class);
        }
    }

    @Override
    public Accused findAccusedById(Accused accused) throws Exception {
        ValidateCommon.validateNullObject(accused.getAccuCode(), "caseCode");
        Accused response = cacheService.getAccusedFromCache(accused.getAccuCode());
        if (response != null) return response;
        else {
            ResponseBody responseBody = manageServiceFeignAPI.findById(accused);
            if (responseBody == null || StringCommon.isNullOrBlank(responseBody.getResponseCode()) || responseBody.getResponseData() == null)
                throw new CommonException(Response.SYSTEM_ERROR, "Lỗi khi thực hiện truy vấn thông tin danh mục bị can");
            return (new ObjectMapper()).convertValue(responseBody.getResponseData(), Accused.class);
        }
    }
}
