package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.exception.CommonException;
import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.common.response.Response;
import com.bitsco.vks.common.util.ArrayListCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.PageCommon;
import com.bitsco.vks.common.validate.ValidateCommon;
import com.bitsco.vks.sothuly.cache.CacheService;
import com.bitsco.vks.sothuly.entities.ReviewCase;
import com.bitsco.vks.sothuly.entities.ReviewCaseAccused;
import com.bitsco.vks.sothuly.entities.ReviewCaseRequest;
import com.bitsco.vks.sothuly.repository.ReviewCaseAccusedRepository;
import com.bitsco.vks.sothuly.repository.ReviewCaseDAO;
import com.bitsco.vks.sothuly.repository.ReviewCaseRepository;
import com.bitsco.vks.sothuly.repository.ReviewCaseRequestRepository;
import com.bitsco.vks.sothuly.request.ReviewCaseAccusedRequest;
import com.bitsco.vks.sothuly.response.ReviewCaseAccusedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class ReviewCaseServiceImpl implements ReviewCaseService {
    @Autowired
    ReviewCaseRepository reviewCaseRepository;

    @Autowired
    ReviewCaseAccusedRepository reviewCaseAccusedRepository;

    @Autowired
    ReviewCaseRequestRepository reviewCaseRequestRepository;

    @Autowired
    ReviewCaseDAO reviewCaseDAO;

    @Autowired
    CacheService cacheService;

    private ReviewCase saveReviewCase(ReviewCase reviewCase) throws Exception {
        if (reviewCase.getId() == null) reviewCase.setCreatedBy(cacheService.getUsernameFromHeader());
        else reviewCase.setUpdatedBy(cacheService.getUsernameFromHeader());
        return reviewCaseRepository.save(reviewCase);
    }

    private ReviewCaseAccused saveReviewCaseAccused(ReviewCaseAccused reviewCaseAccused) throws Exception {
        if (reviewCaseAccused.getId() == null) reviewCaseAccused.setCreatedBy(cacheService.getUsernameFromHeader());
        else reviewCaseAccused.setUpdatedBy(cacheService.getUsernameFromHeader());
        return reviewCaseAccusedRepository.save(reviewCaseAccused);
    }

    private ReviewCaseRequest saveReviewCaseRequest(ReviewCaseRequest reviewCaseRequest) throws Exception {
        if (reviewCaseRequest.getId() == null) reviewCaseRequest.setCreatedBy(cacheService.getUsernameFromHeader());
        else reviewCaseRequest.setUpdatedBy(cacheService.getUsernameFromHeader());
        return reviewCaseRequestRepository.save(reviewCaseRequest);
    }

    @Override
    public ReviewCase createReviewCase(ReviewCase reviewCase) throws Exception {
        //Validate null
        ValidateCommon.validateNullObject(reviewCase.getCaseCode(), "caseCode");
        if (reviewCaseRepository.existsByCaseCodeAndStatus(reviewCase.getCaseCode(), Constant.STATUS_OBJECT.ACTIVE))
            throw new CommonException(Response.OBJECT_IS_EXISTS, "B???n ??n ???? ???????c xem x??t l???i");
        //Validate reviewCaseAccusedList
        List<ReviewCaseAccused> reviewCaseAccusedList = reviewCase.getReviewCaseAccusedList();
        if (!ArrayListCommon.isNullOrEmpty(reviewCaseAccusedList))
            reviewCaseAccusedList.forEach(x -> {
                ValidateCommon.validateNullObject(x.getAccusedCode(), "reviewCaseAccused.accusedCode");
                ValidateCommon.validateNullObject(x.getStage(), "reviewCaseAccused.stage");
                ValidateCommon.validateNullObject(x.getJudgmentCode(), "reviewCaseAccused.judgmentCode");
                x.setStatus(Constant.STATUS_OBJECT.ACTIVE);
            });
        //Validate reviewCaseRequestList
        List<ReviewCaseRequest> reviewCaseRequestList = reviewCase.getReviewCaseRequestList();
        if (!ArrayListCommon.isNullOrEmpty(reviewCaseRequestList))
            reviewCaseRequestList.forEach(x -> {
                ValidateCommon.validateNullObject(x.getAccusedCode(), "reviewCaseRequest.accusedCode");
                ValidateCommon.validateNullObject(x.getRequestNum(), "reviewCaseRequest.requestNum");
                ValidateCommon.validateNullObject(x.getRequestDate(), "reviewCaseRequest.requestDate");
                x.setStatus(Constant.STATUS_OBJECT.ACTIVE);
            });

        reviewCase.setStatus(Constant.STATUS_OBJECT.ACTIVE);
        //Kh???i t???o b???n ghi xem x??t l???i
        ReviewCase rc = saveReviewCase(reviewCase);
        //L??u th??ng tin b???n ??n xem x??t l???i
        if (!ArrayListCommon.isNullOrEmpty(reviewCaseAccusedList))
            reviewCaseAccusedList.forEach(x -> {
                x.setReviewCaseId(rc.getId());
                try {
                    saveReviewCaseAccused(x);
                } catch (Exception e) {
                    throw new CommonException(Response.SYSTEM_ERROR, "L???i khi th???c hi???n l??u th??ng tin b???n ??n xem x??t l???i");
                }
            });
        //L??u th??ng tin y??u c???u ki???n ngh??? ????? ngh???
        if (!ArrayListCommon.isNullOrEmpty(reviewCaseRequestList))
            reviewCaseRequestList.forEach(x -> {
                x.setReviewCaseId(rc.getId());
                try {
                    saveReviewCaseRequest(x);
                } catch (Exception e) {
                    throw new CommonException(Response.SYSTEM_ERROR, "L???i khi th???c hi???n l??u th??ng tin y??u c???u ki???n ngh??? ????? ngh???");
                }
            });
        return rc;
    }

    @Override
    public ReviewCase updateReviewCase(ReviewCase reviewCase) throws Exception {
        ValidateCommon.validateNullObject(reviewCase.getId(), "id");
        ReviewCase response = findReviewCaseById(reviewCase);
        if (response == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        //L??u th??ng tin ch??nh
        response = saveReviewCase(response.coppyFrom(reviewCase));
        //L??u th??ng tin b???n ??n xem x??t l???i cho c??c b??? can
        List<ReviewCaseAccused> reviewCaseAccusedList = reviewCase.getReviewCaseAccusedList();
        List<ReviewCaseAccused> reviewCaseAccuseds = reviewCaseAccusedRepository.findByReviewCaseIdAndStatus(response.getId(), Constant.STATUS_OBJECT.ACTIVE);
        //L??u h???t c??c b???n ghi ??? trong db v??? tr???ng th??i INACTIVE
        if (!ArrayListCommon.isNullOrEmpty(reviewCaseAccuseds))
            reviewCaseAccuseds.forEach(x -> {
                x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                try {
                    saveReviewCaseAccused(x);
                } catch (Exception e) {
                    throw new CommonException(Response.SYSTEM_ERROR, "L???i khi th???c hi???n c???p nh???t b???n ghi b???n ??n xem x??t l???i " + JsonCommon.objectToJsonNotNull(x));
                }
            });
        //L??u h???t c??c b???n ghi t??? client truy???n xu???ng v??o DB(n???u tr??ng v???i danh s??ch tr??n n?? s??? t??? ?????ng update ???? l??n)
        if (!ArrayListCommon.isNullOrEmpty(reviewCaseAccusedList))
            reviewCaseAccusedList.forEach(x -> {
                try {
                    x.setReviewCaseId(reviewCase.getId());
                    saveReviewCaseAccused(x);
                } catch (Exception e) {
                    throw new CommonException(Response.SYSTEM_ERROR, "L???i khi th???c hi???n c???p nh???t b???n ghi b???n ??n xem x??t l???i " + JsonCommon.objectToJsonNotNull(x));
                }
            });

        //L??u th??ng tin y??u c???u ki???n ngh??? ????? ngh???
        List<ReviewCaseRequest> reviewCaseRequestList = reviewCase.getReviewCaseRequestList();
        List<ReviewCaseRequest> reviewCaseRequests = reviewCaseRequestRepository.findByReviewCaseIdAndStatus(response.getId(), Constant.STATUS_OBJECT.ACTIVE);
        //L??u h???t c??c b???n ghi ??? trong db v??? tr???ng th??i INACTIVE
        if (!ArrayListCommon.isNullOrEmpty(reviewCaseRequests))
            reviewCaseRequests.forEach(x -> {
                x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                try {
                    saveReviewCaseRequest(x);
                } catch (Exception e) {
                    throw new CommonException(Response.SYSTEM_ERROR, "L???i khi th???c hi???n c???p nh???t b???n ghi y??u c???u ki???n ngh??? ????? ngh??? " + JsonCommon.objectToJsonNotNull(x));
                }
            });
        //L??u h???t c??c b???n ghi t??? client truy???n xu???ng v??o DB(n???u tr??ng v???i danh s??ch tr??n n?? s??? t??? ?????ng update ???? l??n)
        if (!ArrayListCommon.isNullOrEmpty(reviewCaseRequestList))
            reviewCaseRequestList.forEach(x -> {
                try {
                    x.setReviewCaseId(reviewCase.getId());
                    saveReviewCaseRequest(x);
                } catch (Exception e) {
                    throw new CommonException(Response.SYSTEM_ERROR, "L???i khi th???c hi???n c???p nh???t b???n ghi y??u c???u ki???n ngh??? ????? ngh??? " + JsonCommon.objectToJsonNotNull(x));
                }
            });
        return response;
    }

    @Override
    public ReviewCaseAccused createReviewCaseAccused(ReviewCaseAccused reviewCaseAccused) throws Exception {
        ValidateCommon.validateNullObject(reviewCaseAccused.getReviewCaseId(), "reviewCaseId");
        ValidateCommon.validateNullObject(reviewCaseAccused.getAccusedCode(), "accusedCode");
        ValidateCommon.validateNullObject(reviewCaseAccused.getStage(), "stage");
        ValidateCommon.validateNullObject(reviewCaseAccused.getJudgmentCode(), "judgmentCode");
        reviewCaseAccused.setStatus(Constant.STATUS_OBJECT.ACTIVE);
        return saveReviewCaseAccused(reviewCaseAccused);
    }

    @Override
    public ReviewCaseAccused updateReviewCaseAccused(ReviewCaseAccused reviewCaseAccused) throws Exception {
        ValidateCommon.validateNullObject(reviewCaseAccused.getId(), "id");
        ReviewCaseAccused old = reviewCaseAccusedRepository.findById(reviewCaseAccused.getId()).orElse(null);
        if (old == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        return saveReviewCaseAccused(old.coppyFrom(reviewCaseAccused));
    }

    @Override
    public ReviewCaseRequest createReviewCaseRequest(ReviewCaseRequest reviewCaseRequest) throws Exception {
        ValidateCommon.validateNullObject(reviewCaseRequest.getReviewCaseId(), "reviewCaseId");
        ValidateCommon.validateNullObject(reviewCaseRequest.getRequestNum(), "requestNum");
        ValidateCommon.validateNullObject(reviewCaseRequest.getRequestDate(), "requestDate");
        reviewCaseRequest.setStatus(Constant.STATUS_OBJECT.ACTIVE);
        return saveReviewCaseRequest(reviewCaseRequest);
    }

    @Override
    public ReviewCaseRequest updateReviewCaseRequest(ReviewCaseRequest reviewCaseRequest) throws Exception {
        ValidateCommon.validateNullObject(reviewCaseRequest.getId(), "id");
        ReviewCaseRequest old = reviewCaseRequestRepository.findById(reviewCaseRequest.getId()).orElse(null);
        if (old == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        return saveReviewCaseRequest(old.coppyFrom(reviewCaseRequest));
    }

    @Override
    public ReviewCase findReviewCaseById(ReviewCase reviewCase) throws Exception {
        ValidateCommon.validateNullObject(reviewCase.getId(), "id");
        ReviewCase response = reviewCaseRepository.findById(reviewCase.getId()).orElse(null);
        if (response == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        return response;
    }

    @Override
    public ReviewCase findReviewCaseByCaseCode(ReviewCase reviewCase) throws Exception {
        ValidateCommon.validateNullObject(reviewCase.getCaseCode(), "caseCode");
        ReviewCase response = reviewCaseRepository.findFirstByCaseCodeAndStatus(reviewCase.getCaseCode(), Constant.STATUS_OBJECT.ACTIVE);
        if (response == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        response.setReviewCaseAccusedList(reviewCaseAccusedRepository.findByReviewCaseIdAndStatus(response.getId(), Constant.STATUS_OBJECT.ACTIVE));
        response.setReviewCaseRequestList(reviewCaseRequestRepository.findByReviewCaseIdAndStatus(response.getId(), Constant.STATUS_OBJECT.ACTIVE));
        return response;
    }

    @Override
    public List<ReviewCaseAccusedResponse> getListReviewCaseAccused(ReviewCaseAccusedRequest reviewCase) throws Exception {
        List<ReviewCaseAccusedResponse> list = reviewCaseDAO.getListReviewCaseAccused(reviewCase);
        if (ArrayListCommon.isNullOrEmpty(list)) throw new CommonException(Response.DATA_NOT_FOUND);
        return list;
    }

    @Override
    public PageResponse getPageReviewCaseAccused(PageRequest pageRequest) throws Exception {
        ReviewCaseAccusedRequest request = (new ObjectMapper()).convertValue(pageRequest.getDataRequest(), ReviewCaseAccusedRequest.class);
        List<ReviewCaseAccusedResponse> reviewCaseAccusedList = getListReviewCaseAccused(request);
        if (ArrayListCommon.isNullOrEmpty(reviewCaseAccusedList)) throw new CommonException(Response.DATA_NOT_FOUND);
        if (!StringUtils.isBlank(pageRequest.getSortField()) && !StringUtils.isBlank(pageRequest.getSortOrder())) {
            sortReviewCaseAccusedList(reviewCaseAccusedList, pageRequest.getSortField(), pageRequest.getSortOrder());
        }
        return PageCommon.toPageResponse(
                reviewCaseAccusedList,
                pageRequest.getPageNumber(),
                pageRequest.getPageSize());
    }


    private void sortReviewCaseAccusedList(List<ReviewCaseAccusedResponse> list, String sortField, String sortOrder) {
        if (sortField.equals("caseCode")) {
            if (sortOrder.equals("descend")) {
                list.sort(Comparator.comparing(ReviewCaseAccusedResponse::getCaseCode, Comparator.nullsFirst(Comparator.naturalOrder())).reversed());
            } else {
                list.sort(Comparator.comparing(ReviewCaseAccusedResponse::getCaseCode, Comparator.nullsFirst(Comparator.naturalOrder())));
            }
        }
        if (sortField.equals("caseName")) {
            if (sortOrder.equals("descend")) {
                list.sort(Comparator.comparing(ReviewCaseAccusedResponse::getCaseName, Comparator.nullsFirst(Comparator.naturalOrder())).reversed());
            } else {
                list.sort(Comparator.comparing(ReviewCaseAccusedResponse::getCaseName, Comparator.nullsFirst(Comparator.naturalOrder())));
            }
        }
        if (sortField.equals("accusedCode")) {
            if (sortOrder.equals("descend")) {
                list.sort(Comparator.comparing(ReviewCaseAccusedResponse::getAccusedCode, Comparator.nullsFirst(Comparator.naturalOrder())).reversed());
            } else {
                list.sort(Comparator.comparing(ReviewCaseAccusedResponse::getAccusedCode, Comparator.nullsFirst(Comparator.naturalOrder())));
            }
        }
        if (sortField.equals("accusedName")) {
            if (sortOrder.equals("descend")) {
                list.sort(Comparator.comparing(ReviewCaseAccusedResponse::getAccusedName, Comparator.nullsFirst(Comparator.naturalOrder())).reversed());
            } else {
                list.sort(Comparator.comparing(ReviewCaseAccusedResponse::getAccusedName, Comparator.nullsFirst(Comparator.naturalOrder())));
            }
        }
        if (sortField.equals("judgmentNum")) {
            if (sortOrder.equals("descend")) {
                list.sort(Comparator.comparing(ReviewCaseAccusedResponse::getJudgmentNum, Comparator.nullsFirst(Comparator.naturalOrder())).reversed());
            } else {
                list.sort(Comparator.comparing(ReviewCaseAccusedResponse::getJudgmentNum, Comparator.nullsFirst(Comparator.naturalOrder())));
            }
        }
        if (sortField.equals("judgmentDate")) {
            if (sortOrder.equals("descend")) {
                list.sort(Comparator.comparing(ReviewCaseAccusedResponse::getJudgmentDate, Comparator.nullsFirst(Comparator.naturalOrder())).reversed());
            } else {
                list.sort(Comparator.comparing(ReviewCaseAccusedResponse::getJudgmentDate, Comparator.nullsFirst(Comparator.naturalOrder())));
            }
        }
    }

    @Override
    public ReviewCase detailReviewCaseByAccused(ReviewCaseAccused reviewCaseAccused) throws Exception {
        ValidateCommon.validateNullObject(reviewCaseAccused.getReviewCaseId(), "reviewCaseId");
        ValidateCommon.validateNullObject(reviewCaseAccused.getAccusedCode(), "accusedCode");
        ReviewCase reviewCase = reviewCaseRepository.findById(reviewCaseAccused.getReviewCaseId()).orElse(null);
        if (reviewCase == null)
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Kh??ng t???n t???i b???n ghi xem x??t l???i c???a v??? ??n");
        reviewCase.setReviewCaseAccusedList(reviewCaseAccusedRepository.findByReviewCaseIdAndAccusedCodeAndStatus(reviewCaseAccused.getReviewCaseId(), reviewCaseAccused.getAccusedCode(), Constant.STATUS_OBJECT.ACTIVE));
        reviewCase.setReviewCaseRequestList(reviewCaseRequestRepository.findByReviewCaseIdAndAccusedCodeAndStatus(reviewCaseAccused.getReviewCaseId(), reviewCaseAccused.getAccusedCode(), Constant.STATUS_OBJECT.ACTIVE));
        return reviewCase;
    }
}
