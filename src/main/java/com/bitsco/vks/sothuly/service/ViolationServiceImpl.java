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
import com.bitsco.vks.sothuly.entities.ViolationLaw;
import com.bitsco.vks.sothuly.entities.ViolationLegislationDocument;
import com.bitsco.vks.sothuly.entities.ViolationResult;
import com.bitsco.vks.sothuly.repository.ViolationDAO;
import com.bitsco.vks.sothuly.repository.ViolationLawRepository;
import com.bitsco.vks.sothuly.repository.ViolationLegislationDocumentRepository;
import com.bitsco.vks.sothuly.repository.ViolationResultRepository;
import com.bitsco.vks.sothuly.request.ViolationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ViolationServiceImpl implements ViolationService {
    @Autowired
    ViolationLawRepository violationLawRepository;

    @Autowired
    ViolationLegislationDocumentRepository violationLegislationDocumentRepository;

    @Autowired
    ViolationResultRepository violationResultRepository;

    @Autowired
    ViolationDAO violationDAO;

    @Autowired
    CacheService cacheService;

    private ViolationLaw saveViolationLaw(ViolationLaw violationLaw) throws Exception {
        if (violationLaw.getId() == null) violationLaw.setCreatedBy(cacheService.getUsernameFromHeader());
        else violationLaw.setUpdatedBy(cacheService.getUsernameFromHeader());
        return violationLawRepository.save(violationLaw);
    }

    private ViolationLegislationDocument saveViolationLegislationDocument(ViolationLegislationDocument violationLegislationDocument) {
        if (violationLegislationDocument.getId() == null)
            violationLegislationDocument.setCreatedBy(cacheService.getUsernameFromHeader());
        else violationLegislationDocument.setUpdatedBy(cacheService.getUsernameFromHeader());
        return violationLegislationDocumentRepository.save(violationLegislationDocument);
    }

    private ViolationResult saveViolationResult(ViolationResult violationResult) {
        if (violationResult.getId() == null)
            violationResult.setCreatedBy(cacheService.getUsernameFromHeader());
        else violationResult.setUpdatedBy(cacheService.getUsernameFromHeader());
        return violationResultRepository.save(violationResult);
    }

    @Override
    public ViolationLaw create(ViolationLaw violationLaw) throws Exception {
        //Validate ?????u v??o b???t bu???c b???n ghi vi ph???m
        ValidateCommon.validateNullObject(violationLaw.getSppId(), "sppId");
        ValidateCommon.validateNullObject(violationLaw.getViolationDate(), "violationDate");
        ValidateCommon.validateNullObject(violationLaw.getViolatedAgency(), "violatedAgency");
        ValidateCommon.validateNullObject(violationLaw.getViolatedUnitsId(), "violatedUnitsId");
        ValidateCommon.validateNullObject(violationLaw.getViolatedUnitsName(), "violatedUnitsName");
        violationLaw.setStatus(Constant.STATUS_OBJECT.ACTIVE);
        //Validate ?????u v??o b???t bu???c b???n ghi v??n b???n ban h??nh ??i k??m b???n ghi vi ph???m
        if (!ArrayListCommon.isNullOrEmpty(violationLaw.getViolationLegislationDocumentList()))
            violationLaw.getViolationLegislationDocumentList().forEach(x -> {
                ValidateCommon.validateNullObject(x.getDocumentCode(), "documentCode");
                ValidateCommon.validateNullObject(x.getDocumentNumber(), "documentNumber");
                ValidateCommon.validateNullObject(x.getDocumentDate(), "documentDate");
            });
        //L??u b???n ghi vi ph???m
        ViolationLaw response = saveViolationLaw(violationLaw);

        //L??u danh s??ch b???n ghi v??n b???n ??i k??m b???n ghi vi ph???m
        List<ViolationLegislationDocument> violationLegislationDocumentList = new ArrayList<>();
        if (!ArrayListCommon.isNullOrEmpty(violationLaw.getViolationLegislationDocumentList()))
            violationLaw.getViolationLegislationDocumentList().forEach(x -> {
                x.setViolationLawId(response.getId());
                x.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                violationLegislationDocumentList.add(saveViolationLegislationDocument(x));
            });
        response.setViolationLegislationDocumentList(violationLegislationDocumentList);

        // L??u danh s??ch b???n ghi K???t qu??? th???c hi???n
        List<ViolationResult> violationResults = new ArrayList<>();
        if (!ArrayListCommon.isNullOrEmpty(violationLaw.getViolationResultLists())) {
            violationLaw.getViolationResultLists().forEach(item -> {
                item.setViolationLawId(response.getId());
                item.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                violationResults.add(saveViolationResult(item));
            });
        }
        response.setViolationResultLists(violationResults);

        return response;
    }

    @Override
    public ViolationLaw update(ViolationLaw violationLaw) throws Exception {
        //Validate ?????u v??o b???t bu???c b???n ghi vi ph???m
        ValidateCommon.validateNullObject(violationLaw.getId(), "id");
        ValidateCommon.validateNullObject(violationLaw.getViolationDate(), "violationDate");
        //Validate ?????u v??o b???t bu???c b???n ghi v??n b???n ban h??nh ??i k??m b???n ghi vi ph???m
        if (!ArrayListCommon.isNullOrEmpty(violationLaw.getViolationLegislationDocumentList()))
            violationLaw.getViolationLegislationDocumentList().forEach(x -> {
                ValidateCommon.validateNullObject(x.getDocumentCode(), "documentCode");
                ValidateCommon.validateNullObject(x.getDocumentNumber(), "documentNumber");
                ValidateCommon.validateNullObject(x.getDocumentDate(), "documentDate");
            });
        ViolationLaw old = violationLawRepository.findById(violationLaw.getId()).orElse(null);
        if (old == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Kh??ng t???n t???i b???n ghi v???i m?? " + violationLaw.getId());
        // T???m c???p nh???t c??c v??n b???n ban h??nh c???a vi ph???m v??? tr???ng th??i KH??NG ho???t ?????ng
        List<ViolationLegislationDocument> violationLegislationDocumentList = violationLegislationDocumentRepository.findByViolationLawIdAndStatus(violationLaw.getId(), Constant.STATUS_OBJECT.ACTIVE);
        if (!ArrayListCommon.isNullOrEmpty(violationLegislationDocumentList))
            violationLegislationDocumentList.forEach(x -> {
                x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                saveViolationLegislationDocument(x);
            });

        // T???m c???p nh???t c??c k???t qu??? th???c hi???n c???a vi ph???m v??? tr???ng th??i KH??NG ho???t ?????ng
        List<ViolationResult> violationResultList = violationResultRepository.findByViolationLawIdAndStatus(violationLaw.getId(), Constant.STATUS_OBJECT.ACTIVE);
        if (!ArrayListCommon.isNullOrEmpty(violationResultList))
            violationResultList.forEach(x -> {
                x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                saveViolationResult(x);
            });


        ViolationLaw response = saveViolationLaw(old.copyFrom(violationLaw));

        // L??u danh s??ch v??n b???n ban h??nh n???u c??
        List<ViolationLegislationDocument> list = new ArrayList<>();
        if (!ArrayListCommon.isNullOrEmpty(violationLaw.getViolationLegislationDocumentList()))
            violationLaw.getViolationLegislationDocumentList().forEach(x -> {
                x.setViolationLawId(response.getId());
                list.add(saveViolationLegislationDocument(x));
            });
        response.setViolationLegislationDocumentList(list);

        // L??u danh s??ch b???n ghi K???t qu??? th???c hi???n
        List<ViolationResult> violationResults = new ArrayList<>();
        if (!ArrayListCommon.isNullOrEmpty(violationLaw.getViolationResultLists())) {
            violationLaw.getViolationResultLists().forEach(item -> {
                item.setViolationLawId(response.getId());
                violationResults.add(saveViolationResult(item));
            });
        }
        response.setViolationResultLists(violationResults);

        return response;
    }

    @Override
    public ViolationLaw delete(ViolationLaw violationLaw) throws Exception {
        ValidateCommon.validateNullObject(violationLaw.getId(), "id");
        ViolationLaw old = violationLawRepository.findById(violationLaw.getId()).orElse(null);
        if (old == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Kh??ng t???n t???i b???n ghi v???i m?? " + violationLaw.getId());
        //X??a c??c v??n b???n ban h??nh c???a vi ph???m
        List<ViolationLegislationDocument> violationLegislationDocumentList = violationLegislationDocumentRepository.findByViolationLawIdAndStatus(violationLaw.getId(), Constant.STATUS_OBJECT.ACTIVE);
        if (!ArrayListCommon.isNullOrEmpty(violationLegislationDocumentList))
            violationLegislationDocumentList.forEach(x -> {
                x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                saveViolationLegislationDocument(x);
            });
        //X??a vi ph???m
        old.setStatus(Constant.STATUS_OBJECT.INACTIVE);
        return saveViolationLaw(old);
    }

    @Override
    public ViolationLaw detail(ViolationLaw violationLaw) {
        ValidateCommon.validateNullObject(violationLaw.getId(), "id");
        ViolationLaw old = violationLawRepository.findById(violationLaw.getId()).orElse(null);
        if (old == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Kh??ng t???n t???i b???n ghi v???i m?? " + violationLaw.getId());
        old.setViolationLegislationDocumentList(violationLegislationDocumentRepository.findByViolationLawIdAndStatus(old.getId(), Constant.STATUS_OBJECT.ACTIVE));
        old.setViolationResultLists(violationResultRepository.findByViolationLawIdAndStatus(old.getId(), Constant.STATUS_OBJECT.ACTIVE));
        return old;
    }

    @Override
    public PageResponse getPage(PageRequest pageRequest) throws Exception {
        ViolationRequest request = (new ObjectMapper()).convertValue(pageRequest.getDataRequest(), ViolationRequest.class);
        request.setUsername(cacheService.getUsernameFromHeader());
        return PageCommon.toPageResponse(
                violationDAO.getListViolationLaw(request),
                pageRequest.getPageNumber(),
                pageRequest.getPageSize());
    }
}
