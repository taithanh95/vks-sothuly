package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.request.DenouncedDenouncementRequest;
import com.bitsco.vks.sothuly.response.DenouncedDenouncementResponse;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: anhnb
 * Date: 5/4/2021
 * Time: 5:03 PM
 */

public interface DenouncedDenouncementDAO {

    List<DenouncedDenouncementResponse> findAll(DenouncedDenouncementRequest request);

    DenouncedDenouncementResponse findById(DenouncedDenouncementRequest request);

    List<DenouncedDenouncementResponse> search(DenouncedDenouncementRequest request);
}
