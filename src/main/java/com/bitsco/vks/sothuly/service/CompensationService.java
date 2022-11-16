package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.sothuly.entities.Compensation;

public interface CompensationService {
    Compensation create(Compensation compensation) throws Exception;

    Compensation update(Compensation compensation) throws Exception;

    Compensation delete(Compensation compensation) throws Exception;

    Compensation detail(Compensation compensation) throws Exception;

    PageResponse getPage(PageRequest pageRequest) throws Exception;
}
