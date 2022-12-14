package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.PageCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.bitsco.vks.sothuly.cache.CacheService;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.bitsco.vks.sothuly.entities.*;
import com.bitsco.vks.sothuly.model.Case;
import com.bitsco.vks.sothuly.model.Law;
import com.bitsco.vks.sothuly.repository.*;
import com.bitsco.vks.sothuly.request.DenouncedDenouncementRequest;
import com.bitsco.vks.sothuly.response.DenouncedDenouncementResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: anhnb
 * Date: 5/4/2021
 * Time: 5:30 PM
 */
@Service
@Transactional
public class DenouncedDenouncementServiceImpl implements DenouncedDenouncementService {

    @Autowired
    private DenouncedDenouncementDAO denouncedDenouncementDAO;

    @Autowired
    DenounceDenouncementRepository denounceDenouncementRepository;

    @Autowired
    IdentifiersDenounceRepository identifiersDenounceRepository;

    @Autowired
    DenounceDenouncedPersonRepository denounceDenouncedPersonRepository;

    @Autowired
    SettlementDecisionRepository settlementDecisionRepository;

    @Autowired
    InvestigationActivityRepository investigationActivityRepository;

    @Autowired
    VerificationInvestigationRepository verificationInvestigationRepository;

    @Autowired
    ApParamRepository apParamRepository;

    @Autowired
    CacheService cacheService;

    @Autowired
    RegisterDecisionRepository registerDecisionRepository;

    @Override
    public List<DenouncedDenouncementResponse> getList(DenouncedDenouncementRequest request) {
        request.setUsername(cacheService.getUsernameFromHeader());
        List<DenouncedDenouncementResponse> list = denouncedDenouncementDAO.findAll(request);
        if (ArrayListCommon.isNullOrEmpty(list)) {
            throw new CommonException(Response.DATA_NOT_FOUND);
        }
        return list;
    }

    @Override
    public DenouncedDenouncementResponse findById(DenouncedDenouncementRequest request) {
        ValidateCommon.validateNullObject(request.getDenouncementCode(), "denouncementCode");
        return denouncedDenouncementDAO.findById(request);
    }

    @Override
    public DenounceDenouncement detail(DenounceDenouncement denounceDenouncement) throws Exception {
        ValidateCommon.validateNullObject(denounceDenouncement.getId(), "id");
        DenounceDenouncement response = denounceDenouncementRepository.findByIdAndStatus(denounceDenouncement.getId(), Constant.STATUS_OBJECT.ACTIVE_B);
        if (response == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        response.setDenounceDenouncedPersonList(denounceDenouncedPersonRepository.findByDenouncementIdAndStatus(response.getId(), true));
        response.setVerificationInvestigationList(verificationInvestigationRepository.findByDenouncementIdAndStatus(response.getId(), Constant.STATUS_OBJECT.ACTIVE));
        response.setSettlementDecisionList(settlementDecisionRepository.findByDenouncementIdAndStatus(response.getId(), Constant.STATUS_OBJECT.ACTIVE));
        response.setInvestigationActivityList(investigationActivityRepository.findByDenouncementIdAndStatus(response.getId(), Constant.STATUS_OBJECT.ACTIVE));
        if (!StringCommon.isNullOrBlank(response.getIpnEnactmentId())) {
            List<String> lst = Arrays.asList(response.getIpnEnactmentId().split(","));
            List<Law> lstLaw = new ArrayList<>();
            lst.forEach(s -> lstLaw.add(cacheService.getLawFromCache(s)));
            response.setLaw(lstLaw);
        }
        return response;
    }

    @Override
    public DenounceDenouncement create(DenounceDenouncement denounceDenouncement) throws Exception {
        ValidateCommon.validateNullObject(denounceDenouncement.getTakenOverDate(), "Ng??y VKS ti???p nh???n l?? b???t bu???c");
        ValidateCommon.validateNullObject(denounceDenouncement.getSettlementTerm(), "Th???i h???n gi???i quy???t l?? b???t bu???c");
        ValidateCommon.validateNullObject(denounceDenouncement.getCrimeReportSource(), "Lo???i tin b??o l?? b???t bu???c");
//        ValidateCommon.validateNullObject(denounceDenouncement.getRdelation(), "N???i dung t??? gi??c kh??ng ???????c b??? tr???ng");
        IdentifiersDenounce id = identifiersDenounceRepository.findFirstBySppId(denounceDenouncement.getSppId());
        String username = cacheService.getUsernameFromHeader();
        denounceDenouncement.setCreateDate(new Date());
        denounceDenouncement.setCreateUser(username);
//        denounceDenouncement.setDenouncementCode(denounceDenouncementRepository.getDenounceCodeInYear(denounceDenouncement.getTakenOverDate()));
        denounceDenouncement.setDenouncementCode(id.getIdentifiersCode() + " - " + identifiersDenounceRepository.getIdentifiersCode(denounceDenouncement.getSppId()));
        denounceDenouncement.setStatus(true);
        denounceDenouncement.setSettlementStatus(SoThuLyConstant.DENOUNCE_DENOUNCEMENT.SETTLEMENT_STATUS.NOT_BEING_SETTLED);
        DenounceDenouncement response = denounceDenouncementRepository.save(denounceDenouncement);
        //Update STT ??? b???ng m?? ?????nh danh c???a ????n v???
        id.setStt(identifiersDenounceRepository.getIdentifiersCode(denounceDenouncement.getSppId()));
        identifiersDenounceRepository.save(id);
        //Th??m m???i danh s??ch ng?????i b??? t??? gi??c
        response.setDenounceDenouncedPersonList(denounceDenouncement.getDenounceDenouncedPersonList());
        if (!ArrayListCommon.isNullOrEmpty(response.getDenounceDenouncedPersonList()))
            response.getDenounceDenouncedPersonList().forEach(x -> {
                x.setDenouncementId(response.getId());
                x.setCreateDate(new Date());
                x.setCreateUser(username);
                x.setStatus(true);
                denounceDenouncedPersonRepository.save(x);
            });
        //Th??m m???i danh s??ch ho???t ?????ng ??i???u tra
        response.setInvestigationActivityList(denounceDenouncement.getInvestigationActivityList());
        if (!ArrayListCommon.isNullOrEmpty(response.getInvestigationActivityList()))
            response.getInvestigationActivityList().forEach(x -> {
                x.setDenouncementId(response.getId());
                x.setCreateDate(new Date());
                x.setCreateUser(username);
                x.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                investigationActivityRepository.save(x);
            });
        //Th??m m???i danh s??ch quy???t ?????nh
        response.setSettlementDecisionList(denounceDenouncement.getSettlementDecisionList());
        if (!ArrayListCommon.isNullOrEmpty(response.getSettlementDecisionList()))
            response.getSettlementDecisionList().forEach(x -> {
                x.setDenouncementId(response.getId());
                x.setCreateDate(new Date());
                x.setCreateUser(username);
                x.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                settlementDecisionRepository.save(x);
            });
        //Th??m m???i danh s??ch ??i???u tra x??c minh
        response.setVerificationInvestigationList(denounceDenouncement.getVerificationInvestigationList());
        if (!ArrayListCommon.isNullOrEmpty(response.getVerificationInvestigationList()))
            response.getVerificationInvestigationList().forEach(x -> {
                x.setDenouncementId(response.getId());
                x.setCreateDate(new Date());
                x.setCreateUser(username);
                x.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                verificationInvestigationRepository.save(x);
            });

        return response;
    }

    @Override
    public DenounceDenouncement update(DenounceDenouncement denounceDenouncement) throws Exception {
        ValidateCommon.validateNullObject(denounceDenouncement.getId(), "id");
        ValidateCommon.validateNullObject(denounceDenouncement.getIpnClassifiedNews(), "Ph??n lo???i tin l?? b???t bu???c");
        DenounceDenouncement response = denounceDenouncementRepository.findById(denounceDenouncement.getId()).orElse(null);
        if (response == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        Long denouncementId = response.getId();
        response = response.coppyFrom(denounceDenouncement);
        String username = cacheService.getUsernameFromHeader();
        response.setUpdateDate(new Date());
        response.setUpdateUser(username);
        //C???p nh???t danh s??ch ng?????i b??? t??? gi??c
        if (!ArrayListCommon.isNullOrEmpty(response.getDenounceDenouncedPersonList()))
            response.getDenounceDenouncedPersonList().forEach(x -> {
                x.updateRecordInfo(username);
                x.setDenouncementId(denouncementId);
                denounceDenouncedPersonRepository.save(x);
            });
        //C???p nh???t danh s??ch ho???t ?????ng ??i???u tra
        if (!ArrayListCommon.isNullOrEmpty(response.getInvestigationActivityList()))
            response.getInvestigationActivityList().forEach(x -> {
                x.updateRecordInfo(username);
                x.setDenouncementId(denouncementId);
                investigationActivityRepository.save(x);
            });
        //C???p nh???t danh s??ch quy???t ?????nh
        if (!ArrayListCommon.isNullOrEmpty(response.getSettlementDecisionList()))
            response.getSettlementDecisionList().forEach(x -> {
                x.updateRecordInfo(username);
                x.setDenouncementId(denouncementId);
                settlementDecisionRepository.save(x);
            });
        //C???p nh???t danh s??ch ??i???u tra x??c minh
        if (!ArrayListCommon.isNullOrEmpty(response.getVerificationInvestigationList()))
            response.getVerificationInvestigationList().forEach(x -> {
                x.updateRecordInfo(username);
                x.setDenouncementId(denouncementId);
                verificationInvestigationRepository.save(x);
            });
        updateDenouncementStatus(response);
        return denounceDenouncementRepository.save(response);
    }

    private void updateDenouncementStatus(DenounceDenouncement denouncement) {
        List<SettlementDecision> decisionList = new ArrayList<>();
        if (!ArrayListCommon.isNullOrEmpty(denouncement.getSettlementDecisionList()))
            decisionList = denouncement.getSettlementDecisionList()
                    .stream()
                    .filter(e -> Constant.STATUS_OBJECT.ACTIVE == e.getStatus()).collect(Collectors.toList());
        denouncement.getSettlementTerm().setHours(0);
        denouncement.getSettlementTerm().setMinutes(0);
        denouncement.getSettlementTerm().setSeconds(0);
        if (!decisionList.isEmpty()) {
            SettlementDecision lastDecision = decisionList.stream().max(
                            (o1, o2) -> Long.valueOf((o1.getExecuteOrder() - o2.getExecuteOrder())).intValue())
                    .orElse(null);

            List<ApParam> apParams = apParamRepository
                    .findApParamsByParamCodeAndStatus("DECISION_TYPE_IDS", Constant.STATUS_OBJECT.ACTIVE);
            String decisionId = lastDecision.getDecisionId();
            List<String> settledDecisionIds = apParams
                    .stream()
                    .filter(e -> String.valueOf(SoThuLyConstant.DENOUNCE_DENOUNCEMENT.SETTLEMENT_STATUS.SETTLED).equals(e.getParamName()))
                    .findFirst()
                    .flatMap(e -> Optional.of(Arrays.asList(e.getParamValue().split(","))))
                    .orElse(new ArrayList<>());
            List<String> suspendedDecisionIds = apParams
                    .stream()
                    .filter(e -> String.valueOf(SoThuLyConstant.DENOUNCE_DENOUNCEMENT.SETTLEMENT_STATUS.SUSPENDED).equals(e.getParamName()))
                    .findFirst()
                    .flatMap(e -> Optional.of(Arrays.asList(e.getParamValue().split(","))))
                    .orElse(new ArrayList<>());

            if (settledDecisionIds.contains(decisionId)) {
                /*
                 * ???? gi???i quy???t
                 * C?? 1 trong 3 Q??:
                 * Q?? Kh???i t???
                 * Q?? Kh??ng kh???i t???
                 * Q?? T???m ????nh ch???
                 */
                denouncement.setSettlementStatus(SoThuLyConstant.DENOUNCE_DENOUNCEMENT.SETTLEMENT_STATUS.SETTLED);
            } else if (suspendedDecisionIds.contains(decisionId)) {
                /*
                 * T???m ????nh ch???
                 * C?? Q?? t???m ????nh ch??? vi???c gi???i quy???t ngu???n tin v??? t???i ph???m
                 */
                denouncement.setSettlementStatus(SoThuLyConstant.DENOUNCE_DENOUNCEMENT.SETTLEMENT_STATUS.SUSPENDED);
            } else {
                denouncement.setSettlementStatus(SoThuLyConstant.DENOUNCE_DENOUNCEMENT.SETTLEMENT_STATUS.BEING_SETTLED);
            }
        } else {
            if (("2".equals(denouncement.getIpnClassifiedNews()) && this.isForwarded(denouncement))) {
                // Phi???u chuy???n tin th?? chuy???n tr???ng th??i ???? gi???i quy???t
                denouncement.setSettlementStatus(SoThuLyConstant.DENOUNCE_DENOUNCEMENT.SETTLEMENT_STATUS.SETTLED);
            } else if (!StringCommon.isNullOrBlank(denouncement.getIpnClassifiedNews())) {
                // Ch???n ph??n lo???i tin th?? chuy???n tr???ng th??i ??ang gi???i quy???t
                denouncement.setSettlementStatus(SoThuLyConstant.DENOUNCE_DENOUNCEMENT.SETTLEMENT_STATUS.BEING_SETTLED);
            } else {
                if (denouncement.getIpnEnactmentId() == null) {
                    denouncement.setSettlementStatus(SoThuLyConstant.DENOUNCE_DENOUNCEMENT.SETTLEMENT_STATUS.NOT_BEING_SETTLED);
                } else {
                    denouncement.setSettlementStatus(SoThuLyConstant.DENOUNCE_DENOUNCEMENT.SETTLEMENT_STATUS.BEING_SETTLED);
                }
            }
        }
    }

    private boolean isForwarded(DenounceDenouncement denouncement) {
        return denouncement.getFnCode() != null ||
                denouncement.getFnDate() != null ||
                denouncement.getFnTakenOverAgency() != null ||
                denouncement.getFnTakenOverUnit() != null;
    }

    @Override
    public DenounceDenouncement delete(DenounceDenouncement denounceDenouncement) throws Exception {
        ValidateCommon.validateNullObject(denounceDenouncement.getId(), "id");
        DenounceDenouncement response = denounceDenouncementRepository.findById(denounceDenouncement.getId()).orElse(null);
        if (response == null) {
            throw new CommonException(Response.OBJECT_NOT_FOUND);
        } else if (registerDecisionRepository.existsAllByDenouncementIdAndTypeAndStatus(denounceDenouncement.getId(), 3, Constant.STATUS_OBJECT.ACTIVE)) {
            throw new CommonException(Response.OBJECT_IS_EXISTS, "Tin b??o t??? gi??c ???? ???????c c???p s??? l???nh");
        } else {
            String username = cacheService.getUsernameFromHeader();
            // X??a b???n ghi tin b??o
            response.setStatus(false);
            response.setUpdateDate(new Date());
            response.setUpdateUser(username);
            response = denounceDenouncementRepository.save(response);
            // X??a c??c b???n ghi ng?????i b??? t??? c??o
            List<DenounceDenouncedPerson> denounceDenouncedPersonList = denounceDenouncedPersonRepository.findByDenouncementIdAndStatus(response.getId(), true);
            if (!ArrayListCommon.isNullOrEmpty(denounceDenouncedPersonList))
                denounceDenouncedPersonList.forEach(x -> {
                    x.setStatus(false);
                    x.setUpdateDate(new Date());
                    x.setUpdateUser(username);
                    denounceDenouncedPersonRepository.save(x);
                });
            // X??a danh s??ch c?? quan ??i???u tra ti???n h??nh
            List<InvestigationActivity> investigationActivityList = investigationActivityRepository.findByDenouncementIdAndStatus(response.getId(), Constant.STATUS_OBJECT.ACTIVE);
            if (!ArrayListCommon.isNullOrEmpty(investigationActivityList))
                investigationActivityList.forEach(x -> {
                    x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                    x.setUpdateDate(new Date());
                    x.setUpdateUser(username);
                    investigationActivityRepository.save(x);
                });

            // X??a danh s??ch quy???t ?????nh gi???i quy???t
            List<SettlementDecision> settlementDecisionList = settlementDecisionRepository.findByDenouncementIdAndStatus(response.getId(), Constant.STATUS_OBJECT.ACTIVE);
            if (!ArrayListCommon.isNullOrEmpty(settlementDecisionList))
                settlementDecisionList.forEach(x -> {
                    x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                    x.setUpdateDate(new Date());
                    x.setUpdateUser(username);
                    settlementDecisionRepository.save(x);
                });
            // X??a danh s??ch ??i???u tra x??c minh
            List<VerificationInvestigation> verificationInvestigationList = verificationInvestigationRepository.findByDenouncementIdAndStatus(response.getId(), Constant.STATUS_OBJECT.ACTIVE);
            if (!ArrayListCommon.isNullOrEmpty(verificationInvestigationList))
                verificationInvestigationList.forEach(x -> {
                    x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                    x.setUpdateDate(new Date());
                    x.setUpdateUser(username);
                    verificationInvestigationRepository.save(x);
                });
        }
        return response;
    }

    @Override
    public PageResponse getPage(PageRequest pageRequest) throws Exception {
        DenouncedDenouncementRequest request = (new ObjectMapper()).convertValue(pageRequest.getDataRequest(), DenouncedDenouncementRequest.class);
        List<DenouncedDenouncementResponse> list = search(request);
        if (ArrayListCommon.isNullOrEmpty(list)) throw new CommonException(Response.DATA_NOT_FOUND);
        list.forEach(rs -> {
            if (!StringCommon.isNullOrBlank(rs.getIpnEnactmentId())) {
                List<String> lst = Arrays.asList(rs.getIpnEnactmentId().split(","));
                List<Law> lstLaw = new ArrayList<>();
                lst.forEach(s -> lstLaw.add(cacheService.getLawFromCache(s)));
                rs.setLaw(lstLaw);
            }
        });
        return PageCommon.toPageResponse(
                list,
                pageRequest.getPageNumber(),
                pageRequest.getPageSize());
    }

    private List<DenouncedDenouncementResponse> search(DenouncedDenouncementRequest request) {
//        request.setUsername(cacheService.getUsernameFromHeader());
        List<DenouncedDenouncementResponse> list = denouncedDenouncementDAO.search(request);
        if (ArrayListCommon.isNullOrEmpty(list)) {
            throw new CommonException(Response.DATA_NOT_FOUND);
        }
        return list;
    }

    @Override
    public DenounceDenouncement insertCase(DenounceDenouncement denounceDenouncement) throws Exception {
        ValidateCommon.validateNullObject(denounceDenouncement.getId(), "id");
        DenounceDenouncement response = denounceDenouncementRepository.findById(denounceDenouncement.getId()).orElse(null);
        if (response == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        response.setCasecode(denounceDenouncement.getCasecode());
        response.setCasename(denounceDenouncement.getCasename());
        return denounceDenouncementRepository.save(response);
    }
}
