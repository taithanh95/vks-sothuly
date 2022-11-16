package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.sothuly.model.Accused;
import com.bitsco.vks.sothuly.model.Case;

public interface ManageService {
    Case findCaseById(Case c) throws Exception;

    Accused findAccusedById(Accused accused) throws Exception;

}
