package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.PageCommon;
import com.bitsco.vks.sothuly.entities.Arrestee;
import com.bitsco.vks.sothuly.model.SearchArresteeDTO;
import com.bitsco.vks.sothuly.repository.ArresteeDAO;
import com.bitsco.vks.sothuly.repository.ArresteeRepositoryCustom;
import com.bitsco.vks.sothuly.request.ArresteeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ArresteeServiceImpl implements ArresteeService {
    @Autowired
    private ArresteeDAO arresteeDAO;

    @Autowired
    private ArresteeRepositoryCustom arresteeRepositoryCustom;

    @Override
    public PageResponse search(PageRequest pageRequest) throws Exception {
        ArresteeRequest request = (new ObjectMapper()).convertValue(pageRequest.getDataRequest(), ArresteeRequest.class);
        List<SearchArresteeDTO> list = arresteeDAO.search(request);
        if (ArrayListCommon.isNullOrEmpty(list)) throw new CommonException(Response.DATA_NOT_FOUND);
        return PageCommon.toPageResponse(list, pageRequest.getPageNumber(), pageRequest.getPageSize());
    }

    @Override
    public Optional<Arrestee> findByIdCustom(Arrestee arrestee) throws Exception {
        return arresteeRepositoryCustom.findByIdCustom(arrestee.getId());
    }

}

