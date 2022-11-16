package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.NumberCommon;
import com.bitsco.vks.common.util.PageCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.bitsco.vks.sothuly.cache.CacheService;
import com.bitsco.vks.sothuly.constant.ShareInfoLvl;
import com.bitsco.vks.sothuly.entities.ArrestDetentionInfo;
import com.bitsco.vks.sothuly.entities.ArrestSettlementDecision;
import com.bitsco.vks.sothuly.entities.Arrestee;
import com.bitsco.vks.sothuly.entities.DenounceDenouncement;
import com.bitsco.vks.sothuly.model.ArrestDetentionInfoDTO;
import com.bitsco.vks.sothuly.repository.ArrestDetentionInfoDAO;
import com.bitsco.vks.sothuly.repository.ArrestDetentionInfoRepository;
import com.bitsco.vks.sothuly.repository.ArrestSettlementDecisionRepository;
import com.bitsco.vks.sothuly.repository.ArresteeRepository;
import com.bitsco.vks.sothuly.request.ArrestDetentionInfoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author phucnv
 * @create 4/8/2021 1:36 PM
 */
@Service
@Slf4j
public class ArrestDetentionServiceImpl implements ArrestDetentionService {
    @Autowired
    private ArrestDetentionInfoRepository arrestDetentionInfoRepository;

    @Autowired
    private ArrestSettlementDecisionRepository arrestSettlementDecisionRepository;

    @Autowired
    private ArresteeRepository arresteeRepository;

    @Autowired
    CacheService cacheService;

    @Autowired
    ArrestDetentionInfoDAO arrestDetentionInfoDAO;

    @Override
    @Transactional
    public ArrestDetentionInfo createArrestDetention(ArrestDetentionInfo detentionInfo) throws Exception {
        Date curentDate = new Date();
        boolean created = NumberCommon.isNullOrZero(detentionInfo.getId());
        if (!ArrayListCommon.isNullOrEmpty(detentionInfo.getArrestees())) {
            detentionInfo.getArrestees().forEach(x -> {
                if (!created) {
                    x.setUpdatedAt(curentDate);
                    x.setUpdatedBy(cacheService.getUsernameFromHeader());
                } else {
                    x.setCreatedAt(curentDate);
                    x.setCreatedBy(cacheService.getUsernameFromHeader());
                }
            });
            for (Arrestee arrestee : detentionInfo.getArrestees()) {
                if (!ArrayListCommon.isNullOrEmpty(arrestee.getLawOffenses())) {
                    arrestee.getLawOffenses().forEach(x -> {
                        if (!created) {
                            x.setUpdatedAt(curentDate);
                            x.setUpdatedBy(cacheService.getUsernameFromHeader());
                        } else {
                            x.setCreatedAt(curentDate);
                            x.setCreatedBy(cacheService.getUsernameFromHeader());
                        }
                    });
                }
                if (!ArrayListCommon.isNullOrEmpty(arrestee.getDisciplineViolations())) {
                    arrestee.getDisciplineViolations().forEach(x -> {
                        if (!created) {
                            x.setUpdatedAt(curentDate);
                            x.setUpdatedBy(cacheService.getUsernameFromHeader());
                        } else {
                            x.setCreatedAt(curentDate);
                            x.setCreatedBy(cacheService.getUsernameFromHeader());
                        }
                    });
                }
                if (!ArrayListCommon.isNullOrEmpty(arrestee.getSettlementDecisions())) {
                    arrestee.getSettlementDecisions().forEach(x -> {
                        if (!created) {
                            x.setUpdatedAt(curentDate);
                            x.setUpdatedBy(cacheService.getUsernameFromHeader());
                        } else {
                            x.setCreatedAt(curentDate);
                            x.setCreatedBy(cacheService.getUsernameFromHeader());
                        }
                    });
                }
            }
        }
        if (!NumberCommon.isNullOrZero(detentionInfo.getId())) {
            detentionInfo.setUpdatedAt(curentDate);
            detentionInfo.setUpdatedBy(cacheService.getUsernameFromHeader());
        } else {
            if (detentionInfo.getProcuracyTakenOverDate() != null) {
                Integer year = getYearInDate(detentionInfo.getProcuracyTakenOverDate());
                detentionInfo.setCreatedAt(curentDate);
                detentionInfo.setCreatedBy(cacheService.getUsernameFromHeader());
                detentionInfo.setCode(arrestDetentionInfoRepository.getArrestDetentionInfoCodeInYear(year));
                detentionInfo.setStatus(Constant.ARREST_DETENTION.ACTIVE);
            } else {
                Integer year = getYearInDate(new Date());
                detentionInfo.setCreatedAt(curentDate);
                detentionInfo.setCreatedBy(cacheService.getUsernameFromHeader());
                detentionInfo.setCode(arrestDetentionInfoRepository.getArrestDetentionInfoCodeInYear(year));
                detentionInfo.setStatus(Constant.ARREST_DETENTION.ACTIVE);
            }
        }
        return this.arrestDetentionInfoRepository.saveAndFlush(detentionInfo);
    }

    @Override
    @Transactional
    public ArrestSettlementDecision createSettlementDecision(ArrestSettlementDecision arrestSettlementDecision) throws Exception {
        Date currentDate = new Date();
        if (!NumberCommon.isNullOrZero(arrestSettlementDecision.getId())) {
            arrestSettlementDecision.setUpdatedAt(currentDate);
            arrestSettlementDecision.setUpdatedBy(cacheService.getUsernameFromHeader());
        } else {
            Long maxExecuteOrder = this.arrestSettlementDecisionRepository.getMaxExecuteOrder(arrestSettlementDecision.getArresteeId());
            arrestSettlementDecision.setExecuteOrder(maxExecuteOrder + 1);
            arrestSettlementDecision.setCreatedAt(currentDate);
            arrestSettlementDecision.setCreatedBy(cacheService.getUsernameFromHeader());
            arrestSettlementDecision.setStatus(Constant.ARREST_DETENTION.ACTIVE);
        }

        return this.arrestSettlementDecisionRepository.saveAndFlush(arrestSettlementDecision);

    }

    @Override
    @Transactional(readOnly = true)
    public ArrestDetentionInfo getArrestDetentionInfoById(Long id, String sppId) throws Exception {
        Optional<ArrestDetentionInfo> optionalArrestDetentionInfo = this.arrestDetentionInfoRepository.findByIdCustom(id);
        if (optionalArrestDetentionInfo.isPresent()) {
            return optionalArrestDetentionInfo.get();
        }
        log.error("ArrestDetentionInfo id = {} not found", id);
        throw new EntityNotFoundException();
    }

    @Override
    @Transactional
    public ArrestDetentionInfo updateArrestDetentionInfo(ArrestDetentionInfo arrestDetentionInfo, String sppId) throws Exception {
        List<Arrestee> lstArrestee = arrestDetentionInfo.getArrestees();
//        this.checkPermission(denouncement, sppId, true);
        Optional<ArrestDetentionInfo> optionalArrestDetentionInfo = this.arrestDetentionInfoRepository.findByIdCustom(arrestDetentionInfo.getId());
        if (optionalArrestDetentionInfo.isPresent()) {
            String username = cacheService.getUsernameFromHeader();
            if (StringCommon.safeEqual("typeUpdate", "saveAll")) {
                if (arrestDetentionInfo.getId() != null) {
                    arrestDetentionInfo.setUpdatedBy(username);
                    arrestDetentionInfo.setUpdatedAt();
                } else {
                    arrestDetentionInfo.setCreatedBy(username);
                    arrestDetentionInfo.setCreatedAt();
                }
                arrestDetentionInfo.getArrestees().forEach(e -> {
                    if (e.getId() != null) {
                        e.setUpdatedBy(username);
                        e.setUpdatedAt();
                    } else {
                        e.setCreatedBy(username);
                        e.setCreatedAt();
                    }
                });

            } else if (StringCommon.safeEqual("typeUpdate", "saveArrestee")) {
                if (!ArrayListCommon.isNullOrEmpty(lstArrestee)) {
                    for (Arrestee arrestee : lstArrestee) {
                        arrestee.getLawOffenses().forEach(e -> {
                            if (e.getId() != null) {
                                e.setUpdatedBy(username);
                                e.setUpdatedAt();
                            } else {
                                e.setCreatedBy(username);
                                e.setCreatedAt();
                            }
                        });
                        arrestee.getDisciplineViolations().forEach(e -> {
                            if (e.getId() != null) {
                                e.setUpdatedBy(username);
                                e.setUpdatedAt();
                            } else {
                                e.setCreatedBy(username);
                                e.setCreatedAt();
                            }
                        });
                    }
                }
            } else if (StringCommon.safeEqual("typeUpdate", "saveSettlementDecisions")) {
                if (!ArrayListCommon.isNullOrEmpty(lstArrestee)) {
                    for (Arrestee arrestee : lstArrestee) {
                        arrestee.getSettlementDecisions().forEach(e -> {
                            if (e.getId() != null) {
                                e.setUpdatedBy(username);
                                e.setUpdatedAt();
                            } else {
                                e.setCreatedBy(username);
                                e.setCreatedAt();
                            }
                        });
                    }
                }
            }
            return arrestDetentionInfoRepository.save(arrestDetentionInfo);
        } else {
            log.error("ArrestDetentionInfo id = {} not found", arrestDetentionInfo.getId());
            throw new EntityNotFoundException();
        }
    }

    @Override
    @Transactional
    public Arrestee saveArrestee(Arrestee arrestee) throws Exception {
        Date curentDate = new Date();
        // !null thi cap nhat
        List<ArrestSettlementDecision> list = arrestSettlementDecisionRepository.findByArresteeId(arrestee.getId());
        if (!NumberCommon.isNullOrZero(arrestee.getId())) {
            arrestee.setUpdatedAt(curentDate);
            arrestee.setUpdatedBy(cacheService.getUsernameFromHeader());
            arrestee.setSettlementDecisions(list);
        } else {
            // insert
            arrestee.setCreatedAt(curentDate);
            arrestee.setCreatedBy(cacheService.getUsernameFromHeader());
            arrestee.setStatus(Constant.ARREST_DETENTION.ACTIVE);
        }
        return this.arresteeRepository.saveAndFlush(arrestee);
    }

    @Override
    @Transactional
    public void delete(Long id, String sppId) throws Exception {
        String userName = cacheService.getUsernameFromHeader();
        Date currentDate = new Date();
        Optional<ArrestDetentionInfo> optionalArrestDetentionInfo = this.arrestDetentionInfoRepository.findByIdCustom(id);
        if (optionalArrestDetentionInfo.isPresent()) {
            ArrestDetentionInfo info = optionalArrestDetentionInfo.get();
            info.setUpdatedBy(userName);
            info.setUpdatedAt(currentDate);
            info.setStatus(0);
            if (!ArrayListCommon.isNullOrEmpty(info.getArrestees())) {
                for (Arrestee arrestee : info.getArrestees()) {
                    arrestee.setUpdatedBy(userName);
                    arrestee.setUpdatedAt(currentDate);
                    arrestee.setStatus(0);

                    if (!ArrayListCommon.isNullOrEmpty(arrestee.getLawOffenses())) {
                        arrestee.getLawOffenses().forEach(e -> {
                            e.setUpdatedAt(currentDate);
                            e.setUpdatedBy(userName);
                            e.setStatus(0);
                        });
                    }
                    if (!ArrayListCommon.isNullOrEmpty(arrestee.getDisciplineViolations())) {
                        arrestee.getDisciplineViolations().forEach(e -> {
                            e.setUpdatedAt(currentDate);
                            e.setUpdatedBy(userName);
                            e.setStatus(0);
                        });
                    }
                    if (!ArrayListCommon.isNullOrEmpty(arrestee.getSettlementDecisions())) {
                        arrestee.getSettlementDecisions().forEach(e -> {
                            e.setUpdatedAt(currentDate);
                            e.setUpdatedBy(userName);
                            e.setStatus(0);
                        });
                    }
                }
            }
            this.arrestDetentionInfoRepository.saveAndFlush(info);

        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public PageResponse search(PageRequest pageRequest) throws Exception {
        ArrestDetentionInfoRequest request = (new ObjectMapper()).convertValue(pageRequest.getDataRequest(), ArrestDetentionInfoRequest.class);
        List<ArrestDetentionInfoDTO> list = arrestDetentionInfoDAO.search(request);
        if (ArrayListCommon.isNullOrEmpty(list)) throw new CommonException(Response.DATA_NOT_FOUND);
        return PageCommon.toPageResponse(list, pageRequest.getPageNumber(), pageRequest.getPageSize());
    }

    private Integer getYearInDate(Date date) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        return calendar1.get(Calendar.YEAR);
    }

    private void checkPermission(ArrestDetentionInfo detentionInfo, String sppId, boolean isUpdate, String username) {
        if (sppId == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Thông tin đơn vị chưa được cung cấp");
        if (!sppId.equals(detentionInfo.getSppId())) {
            throw new CommonException("Người dùng không có quyền truy cập tin báo thuộc đơn vị khác");
        }
        if (isUpdate) {
            if (!username.equals(detentionInfo.getCreatedBy()) &&
                    detentionInfo.getShareInfoLevel() != ShareInfoLvl.INTERNAL.getValue()) {
                throw new CommonException("Người dùng không có quyền cập nhật tin báo");
            }
        } else {
            if (!username.equals(detentionInfo.getCreatedBy()) &&
                    detentionInfo.getShareInfoLevel() != ShareInfoLvl.INTERNAL.getValue() &&
                    detentionInfo.getShareInfoLevel() != ShareInfoLvl.PROTECTED.getValue()) {
                throw new CommonException("Người dùng không có quyền xem tin báo");
            }
        }

    }

}
