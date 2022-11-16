package com.bitsco.vks.sothuly.service;


import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.sothuly.entities.*;
import com.bitsco.vks.sothuly.request.DenouncedDenouncementRequest;
import com.bitsco.vks.sothuly.response.DenouncedDenouncementResponse;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: anhnb
 * Date: 5/4/2021
 * Time: 5:30 PM
 */
public interface DenouncedDenouncementService {

    List<DenouncedDenouncementResponse> getList(DenouncedDenouncementRequest request);

    DenouncedDenouncementResponse findById(DenouncedDenouncementRequest request);

    DenounceDenouncement detail(DenounceDenouncement denounceDenouncement) throws Exception;

    DenounceDenouncement create(DenounceDenouncement denounceDenouncement) throws Exception;

    DenounceDenouncement update(DenounceDenouncement denounceDenouncement) throws Exception;

    DenounceDenouncement insertCase(DenounceDenouncement denounceDenouncement) throws Exception;

    DenounceDenouncement delete(DenounceDenouncement denounceDenouncement) throws Exception;

    PageResponse getPage(PageRequest pageRequest) throws Exception;
}
