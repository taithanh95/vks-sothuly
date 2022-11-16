package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.sothuly.entities.ViolationLaw;

public interface ViolationService {
    ViolationLaw create(ViolationLaw violationLaw) throws Exception;

    ViolationLaw update(ViolationLaw violationLaw) throws Exception;

    ViolationLaw delete(ViolationLaw violationLaw) throws Exception;

    ViolationLaw detail(ViolationLaw violationLaw) throws Exception;

    PageResponse getPage(PageRequest pageRequest) throws Exception;
}
