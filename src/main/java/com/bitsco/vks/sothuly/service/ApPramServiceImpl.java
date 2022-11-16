package com.bitsco.vks.sothuly.service;


import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.sothuly.entities.ApParam;
import com.bitsco.vks.sothuly.repository.ApParamRepository;
import com.bitsco.vks.sothuly.service.ApPramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApPramServiceImpl implements ApPramService {

    @Autowired
    private ApParamRepository apParamRepository;

    @Override
    public List<ApParam> findApParamsByParamCodeAndStatus(String code) {
        return apParamRepository.findApParamsByParamCodeAndStatus(code, Constant.ARREST_DETENTION.ACTIVE);
    }
}
