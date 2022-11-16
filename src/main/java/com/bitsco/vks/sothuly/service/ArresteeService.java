package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.sothuly.entities.Arrestee;

import java.util.Optional;

public interface ArresteeService {
    PageResponse search(PageRequest pageRequest) throws Exception;

    Optional<Arrestee> findByIdCustom(Arrestee arrestee) throws Exception;
}
