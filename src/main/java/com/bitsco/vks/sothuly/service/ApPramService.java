package com.bitsco.vks.sothuly.service;



import com.bitsco.vks.sothuly.entities.ApParam;

import java.util.List;

public interface ApPramService {

    List<ApParam> findApParamsByParamCodeAndStatus(String code);

}
