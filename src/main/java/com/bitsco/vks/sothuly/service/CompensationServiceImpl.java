package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.PageCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.bitsco.vks.sothuly.cache.CacheService;
import com.bitsco.vks.sothuly.entities.Compensation;
import com.bitsco.vks.sothuly.entities.CompensationDamage;
import com.bitsco.vks.sothuly.entities.CompensationDetail;
import com.bitsco.vks.sothuly.entities.CompensationDocument;
import com.bitsco.vks.sothuly.repository.CompensationDAO;
import com.bitsco.vks.sothuly.repository.CompensationDetailRepository;
import com.bitsco.vks.sothuly.repository.CompensationDocumentRepository;
import com.bitsco.vks.sothuly.repository.CompensationRepository;
import com.bitsco.vks.sothuly.repository.CompensationDamageRepository;
import com.bitsco.vks.sothuly.request.CompensationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompensationServiceImpl implements CompensationService {
    @Autowired
    CacheService cacheService;
    @Autowired
    CompensationRepository compensationRepository;
    @Autowired
    CompensationDetailRepository compensationDetailRepository;
    @Autowired
    CompensationDocumentRepository compensationDocumentRepository;
    @Autowired
    CompensationDamageRepository compensationDamageRepository;
    @Autowired
    CompensationDAO compensationDAO;

    private Compensation save(Compensation compensation) {
        if (compensation.getId() == null) compensation.setCreatedBy(cacheService.getUsernameFromHeader());
        else compensation.setUpdatedBy(cacheService.getUsernameFromHeader());
        return compensationRepository.save(compensation);
    }

    private CompensationDetail save(CompensationDetail compensationDetail) {
        if (compensationDetail.getId() == null) compensationDetail.setCreatedBy(cacheService.getUsernameFromHeader());
        else compensationDetail.setUpdatedBy(cacheService.getUsernameFromHeader());
        return compensationDetailRepository.save(compensationDetail);
    }

    private CompensationDocument save(CompensationDocument compensationDocument) {
        if (compensationDocument.getId() == null)
            compensationDocument.setCreatedBy(cacheService.getUsernameFromHeader());
        else compensationDocument.setUpdatedBy(cacheService.getUsernameFromHeader());
        return compensationDocumentRepository.save(compensationDocument);
    }

    private CompensationDamage save(CompensationDamage compensationDamage) {
        if (compensationDamage.getId() == null)
            compensationDamage.setCreatedBy(cacheService.getUsernameFromHeader());
        else compensationDamage.setUpdatedBy(cacheService.getUsernameFromHeader());
        return compensationDamageRepository.save(compensationDamage);
    }

    @Override
    public Compensation create(Compensation compensation) throws Exception {
        //Validate ?????u v??o b???t bu???c b???n ghi
        ValidateCommon.validateNullObject(compensation.getCompensationDate(), "compensationDate");
        ValidateCommon.validateNullObject(compensation.getSppId(), "sppId");
        compensation.setStatus(Constant.STATUS_OBJECT.ACTIVE);
        //Validate ?????u v??o b???t bu???c b???n ghi trong danh s??ch t??i li???u b??? sung(n???u c??)
        if (!ArrayListCommon.isNullOrEmpty(compensation.getCompensationDocumentList()))
            compensation.getCompensationDocumentList().forEach(x -> {
                ValidateCommon.validateNullObject(x.getDocumentName(), "documentName");
                ValidateCommon.validateNullObject(x.getDeadlines(), "deadlines");
            });
        //L??u b???n ghi b???i th?????ng
        Compensation response = save(compensation);
        //L??u danh s??ch t??i li???u b??? sung
        List<CompensationDocument> compensationDocumentList = new ArrayList<>();
        if (!ArrayListCommon.isNullOrEmpty(compensation.getCompensationDocumentList()))
            compensation.getCompensationDocumentList().forEach(x -> {
                x.setCompensationId(response.getId());
                x.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                compensationDocumentList.add(save(x));
            });
        response.setCompensationDocumentList(compensationDocumentList);
        //L??u danh s??ch n???i dung kh??c
        List<CompensationDetail> compensationDetailList = new ArrayList<>();
        if (!ArrayListCommon.isNullOrEmpty(compensation.getCompensationDetailList()))
            compensation.getCompensationDetailList().forEach(x -> {
                x.setCompensationId(response.getId());
                x.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                compensationDetailList.add(save(x));
            });
        response.setCompensationDetailList(compensationDetailList);
        //L??u danh s??ch ng?????i thi???t h???i
        List<CompensationDamage> compensationDamageList = new ArrayList<>();
        if (!ArrayListCommon.isNullOrEmpty(compensation.getCompensationDamagesList()))
            compensation.getCompensationDamagesList().forEach(x -> {
                x.setCompensationId(response.getId());
                x.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                compensationDamageList.add(save(x));
            });
        response.setCompensationDamagesList(compensationDamageList);
        return response;
    }

    @Override
    public Compensation update(Compensation compensation) throws Exception {
        //Validate ?????u v??o b???t bu???c b???n ghi
        ValidateCommon.validateNullObject(compensation.getId(), "id");
        Compensation old = compensationRepository.findById(compensation.getId()).orElse(null);
        if (old == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Kh??ng t???n t???i b???n ghi v???i m?? " + compensation.getId());
        //Validate ?????u v??o b???t bu???c b???n ghi trong danh s??ch t??i li???u b??? sung(n???u c??)
        if (!ArrayListCommon.isNullOrEmpty(compensation.getCompensationDocumentList()))
            compensation.getCompensationDocumentList().forEach(x -> {
                if (x.getStatus().equals(Constant.STATUS_OBJECT.ACTIVE)) {
                    ValidateCommon.validateNullObject(x.getDocumentName(), "documentName");
                    ValidateCommon.validateNullObject(x.getDeadlines(), "deadlines");
                }
            });
        //T???m c???p nh???t danh s??ch t??i li???u b??? sung v??? kh??ng ho???t ?????ng
        List<CompensationDocument> compensationDocumentList = compensationDocumentRepository.findByCompensationIdAndStatus(compensation.getId(), Constant.STATUS_OBJECT.ACTIVE);
        if (!ArrayListCommon.isNullOrEmpty(compensationDocumentList))
            compensationDocumentList.forEach(x -> {
                x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                save(x);
            });
        //T???m c???p nh???t danh s??ch n???i dung kh??c v??? kh??ng ho???t ?????ng
        List<CompensationDetail> compensationDetailList = compensationDetailRepository.findByCompensationIdAndStatus(compensation.getId(), Constant.STATUS_OBJECT.ACTIVE);
        if (!ArrayListCommon.isNullOrEmpty(compensationDetailList))
            compensationDetailList.forEach(x -> {
                x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                save(x);
            });
        //T???m c???p nh???t danh s??ch ng?????i y??u c???u v??? kh??ng ho???t ?????ng
        List<CompensationDamage> compensationDamageList = compensationDamageRepository.findByCompensationIdAndStatus(compensation.getId(), Constant.STATUS_OBJECT.ACTIVE);
        if (!ArrayListCommon.isNullOrEmpty(compensationDamageList))
            compensationDamageList.forEach(x -> {
                x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                save(x);
            });
        Compensation response = save(old.coppyFrom(compensation));
        //L??u danh s??ch t??i li???u b??? sung(n???u c??)
        if (!ArrayListCommon.isNullOrEmpty(compensation.getCompensationDocumentList()))
            compensation.getCompensationDocumentList().forEach(x -> {
                x.setCompensationId(response.getId());
                compensationDocumentList.add(save(x));
            });
        response.setCompensationDocumentList(compensationDocumentList);
        //L??u danh s??ch n???i dung kh??c(n???u c??)
        if (!ArrayListCommon.isNullOrEmpty(compensation.getCompensationDetailList()))
            compensation.getCompensationDetailList().forEach(x -> {
                x.setCompensationId(response.getId());
                compensationDetailList.add(save(x));
            });
        response.setCompensationDetailList(compensationDetailList);
        //L??u danh s??ch ng?????i thi???t h???i(n???u c??)
        if (!ArrayListCommon.isNullOrEmpty(compensation.getCompensationDamagesList()))
            compensation.getCompensationDamagesList().forEach(x -> {
                x.setCompensationId(response.getId());
                compensationDamageList.add(save(x));
            });
        response.setCompensationDamagesList(compensationDamageList);
        return response;
    }

    @Override
    public Compensation delete(Compensation compensation) throws Exception {
        //Validate ?????u v??o b???t bu???c b???n ghi
        ValidateCommon.validateNullObject(compensation.getId(), "id");
        Compensation old = compensationRepository.findById(compensation.getId()).orElse(null);
        if (old == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Kh??ng t???n t???i b???n ghi v???i m?? " + compensation.getId());
        //X??a danh s??ch t??i li???u b??? sung(n???u c??)
        List<CompensationDocument> compensationDocumentList = compensationDocumentRepository.findByCompensationIdAndStatus(compensation.getId(), Constant.STATUS_OBJECT.ACTIVE);
        if (!ArrayListCommon.isNullOrEmpty(compensationDocumentList))
            compensationDocumentList.forEach(x -> {
                x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                save(x);
            });
        //X??a danh s??ch n???i dung kh??c(n???u c??)
        List<CompensationDetail> compensationDetailList = compensationDetailRepository.findByCompensationIdAndStatus(compensation.getId(), Constant.STATUS_OBJECT.ACTIVE);
        if (!ArrayListCommon.isNullOrEmpty(compensationDetailList))
            compensationDetailList.forEach(x -> {
                x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                save(x);
            });
        //X??a danh s??ch ng?????i thi???t h???i(n???u c??)
        List<CompensationDamage> compensationDamageList = compensationDamageRepository.findByCompensationIdAndStatus(compensation.getId(), Constant.STATUS_OBJECT.ACTIVE);
        if (!ArrayListCommon.isNullOrEmpty(compensationDamageList))
            compensationDamageList.forEach(x -> {
                x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                save(x);
            });
        //X??a b???n ghi
        old.setStatus(Constant.STATUS_OBJECT.INACTIVE);
        return save(old);
    }

    @Override
    public Compensation detail(Compensation compensation) throws Exception {
        //Validate ?????u v??o b???t bu???c b???n ghi
        ValidateCommon.validateNullObject(compensation.getId(), "id");
        Compensation old = compensationRepository.findById(compensation.getId()).orElse(null);
        if (old == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Kh??ng t???n t???i b???n ghi v???i m?? " + compensation.getId());
        old.setCompensationDetailList(compensationDetailRepository.findByCompensationIdAndStatus(old.getId(), Constant.STATUS_OBJECT.ACTIVE));
        old.setCompensationDocumentList(compensationDocumentRepository.findByCompensationIdAndStatus(old.getId(), Constant.STATUS_OBJECT.ACTIVE));
        old.setCompensationDamagesList(compensationDamageRepository.findByCompensationIdAndStatus(old.getId(), Constant.STATUS_OBJECT.ACTIVE));
        return old;
    }

    @Override
    public PageResponse getPage(PageRequest pageRequest) throws Exception {
        CompensationRequest request = (new ObjectMapper()).convertValue(pageRequest.getDataRequest(), CompensationRequest.class);
        request.setUsername(cacheService.getUsernameFromHeader());
        return PageCommon.toPageResponse(
                compensationDAO.getList(request),
                pageRequest.getPageNumber(),
                pageRequest.getPageSize());
    }
}
