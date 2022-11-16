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
        ValidateCommon.validateNullObject(denounceDenouncement.getTakenOverDate(), "Ngày VKS tiếp nhận là bắt buộc");
        ValidateCommon.validateNullObject(denounceDenouncement.getSettlementTerm(), "Thời hạn giải quyết là bắt buộc");
        ValidateCommon.validateNullObject(denounceDenouncement.getCrimeReportSource(), "Loại tin báo là bắt buộc");
//        ValidateCommon.validateNullObject(denounceDenouncement.getRdelation(), "Nội dung tố giác không được bỏ trống");
        IdentifiersDenounce id = identifiersDenounceRepository.findFirstBySppId(denounceDenouncement.getSppId());
        String username = cacheService.getUsernameFromHeader();
        denounceDenouncement.setCreateDate(new Date());
        denounceDenouncement.setCreateUser(username);
//        denounceDenouncement.setDenouncementCode(denounceDenouncementRepository.getDenounceCodeInYear(denounceDenouncement.getTakenOverDate()));
        denounceDenouncement.setDenouncementCode(id.getIdentifiersCode() + " - " + identifiersDenounceRepository.getIdentifiersCode(denounceDenouncement.getSppId()));
        denounceDenouncement.setStatus(true);
        denounceDenouncement.setSettlementStatus(SoThuLyConstant.DENOUNCE_DENOUNCEMENT.SETTLEMENT_STATUS.NOT_BEING_SETTLED);
        DenounceDenouncement response = denounceDenouncementRepository.save(denounceDenouncement);
        //Update STT ở bảng mã định danh của đơn vị
        id.setStt(identifiersDenounceRepository.getIdentifiersCode(denounceDenouncement.getSppId()));
        identifiersDenounceRepository.save(id);
        //Thêm mới danh sách người bị tố giác
        response.setDenounceDenouncedPersonList(denounceDenouncement.getDenounceDenouncedPersonList());
        if (!ArrayListCommon.isNullOrEmpty(response.getDenounceDenouncedPersonList()))
            response.getDenounceDenouncedPersonList().forEach(x -> {
                x.setDenouncementId(response.getId());
                x.setCreateDate(new Date());
                x.setCreateUser(username);
                x.setStatus(true);
                denounceDenouncedPersonRepository.save(x);
            });
        //Thêm mới danh sách hoạt động điều tra
        response.setInvestigationActivityList(denounceDenouncement.getInvestigationActivityList());
        if (!ArrayListCommon.isNullOrEmpty(response.getInvestigationActivityList()))
            response.getInvestigationActivityList().forEach(x -> {
                x.setDenouncementId(response.getId());
                x.setCreateDate(new Date());
                x.setCreateUser(username);
                x.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                investigationActivityRepository.save(x);
            });
        //Thêm mới danh sách quyết định
        response.setSettlementDecisionList(denounceDenouncement.getSettlementDecisionList());
        if (!ArrayListCommon.isNullOrEmpty(response.getSettlementDecisionList()))
            response.getSettlementDecisionList().forEach(x -> {
                x.setDenouncementId(response.getId());
                x.setCreateDate(new Date());
                x.setCreateUser(username);
                x.setStatus(Constant.STATUS_OBJECT.ACTIVE);
                settlementDecisionRepository.save(x);
            });
        //Thêm mới danh sách điều tra xác minh
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
        ValidateCommon.validateNullObject(denounceDenouncement.getIpnClassifiedNews(), "Phân loại tin là bắt buộc");
        DenounceDenouncement response = denounceDenouncementRepository.findById(denounceDenouncement.getId()).orElse(null);
        if (response == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        Long denouncementId = response.getId();
        response = response.coppyFrom(denounceDenouncement);
        String username = cacheService.getUsernameFromHeader();
        response.setUpdateDate(new Date());
        response.setUpdateUser(username);
        //Cập nhật danh sách người bị tố giác
        if (!ArrayListCommon.isNullOrEmpty(response.getDenounceDenouncedPersonList()))
            response.getDenounceDenouncedPersonList().forEach(x -> {
                x.updateRecordInfo(username);
                x.setDenouncementId(denouncementId);
                denounceDenouncedPersonRepository.save(x);
            });
        //Cập nhật danh sách hoạt động điều tra
        if (!ArrayListCommon.isNullOrEmpty(response.getInvestigationActivityList()))
            response.getInvestigationActivityList().forEach(x -> {
                x.updateRecordInfo(username);
                x.setDenouncementId(denouncementId);
                investigationActivityRepository.save(x);
            });
        //Cập nhật danh sách quyết định
        if (!ArrayListCommon.isNullOrEmpty(response.getSettlementDecisionList()))
            response.getSettlementDecisionList().forEach(x -> {
                x.updateRecordInfo(username);
                x.setDenouncementId(denouncementId);
                settlementDecisionRepository.save(x);
            });
        //Cập nhật danh sách điều tra xác minh
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
                 * Đã giải quyết
                 * Có 1 trong 3 QĐ:
                 * QĐ Khởi tố
                 * QĐ Không khởi tố
                 * QĐ Tạm đình chỉ
                 */
                denouncement.setSettlementStatus(SoThuLyConstant.DENOUNCE_DENOUNCEMENT.SETTLEMENT_STATUS.SETTLED);
            } else if (suspendedDecisionIds.contains(decisionId)) {
                /*
                 * Tạm đình chỉ
                 * Có QĐ tạm đình chỉ việc giải quyết nguồn tin về tội phạm
                 */
                denouncement.setSettlementStatus(SoThuLyConstant.DENOUNCE_DENOUNCEMENT.SETTLEMENT_STATUS.SUSPENDED);
            } else {
                denouncement.setSettlementStatus(SoThuLyConstant.DENOUNCE_DENOUNCEMENT.SETTLEMENT_STATUS.BEING_SETTLED);
            }
        } else {
            if (("2".equals(denouncement.getIpnClassifiedNews()) && this.isForwarded(denouncement))) {
                // Phiếu chuyển tin thì chuyển trạng thái Đã giải quyết
                denouncement.setSettlementStatus(SoThuLyConstant.DENOUNCE_DENOUNCEMENT.SETTLEMENT_STATUS.SETTLED);
            } else if (!StringCommon.isNullOrBlank(denouncement.getIpnClassifiedNews())) {
                // Chọn phân loại tin thì chuyển trạng thái Đang giải quyết
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
            throw new CommonException(Response.OBJECT_IS_EXISTS, "Tin báo tố giác đã được cấp số lệnh");
        } else {
            String username = cacheService.getUsernameFromHeader();
            // Xóa bản ghi tin báo
            response.setStatus(false);
            response.setUpdateDate(new Date());
            response.setUpdateUser(username);
            response = denounceDenouncementRepository.save(response);
            // Xóa các bản ghi người bị tố cáo
            List<DenounceDenouncedPerson> denounceDenouncedPersonList = denounceDenouncedPersonRepository.findByDenouncementIdAndStatus(response.getId(), true);
            if (!ArrayListCommon.isNullOrEmpty(denounceDenouncedPersonList))
                denounceDenouncedPersonList.forEach(x -> {
                    x.setStatus(false);
                    x.setUpdateDate(new Date());
                    x.setUpdateUser(username);
                    denounceDenouncedPersonRepository.save(x);
                });
            // Xóa danh sách cơ quan điều tra tiến hành
            List<InvestigationActivity> investigationActivityList = investigationActivityRepository.findByDenouncementIdAndStatus(response.getId(), Constant.STATUS_OBJECT.ACTIVE);
            if (!ArrayListCommon.isNullOrEmpty(investigationActivityList))
                investigationActivityList.forEach(x -> {
                    x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                    x.setUpdateDate(new Date());
                    x.setUpdateUser(username);
                    investigationActivityRepository.save(x);
                });

            // Xóa danh sách quyết định giải quyết
            List<SettlementDecision> settlementDecisionList = settlementDecisionRepository.findByDenouncementIdAndStatus(response.getId(), Constant.STATUS_OBJECT.ACTIVE);
            if (!ArrayListCommon.isNullOrEmpty(settlementDecisionList))
                settlementDecisionList.forEach(x -> {
                    x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                    x.setUpdateDate(new Date());
                    x.setUpdateUser(username);
                    settlementDecisionRepository.save(x);
                });
            // Xóa danh sách điều tra xác minh
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
