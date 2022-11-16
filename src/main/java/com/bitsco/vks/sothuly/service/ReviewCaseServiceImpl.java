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
            throw new CommonException(Response.OBJECT_IS_EXISTS, "Bản án đã được xem xét lại");
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
        //Khởi tạo bản ghi xem xét lại
        ReviewCase rc = saveReviewCase(reviewCase);
        //Lưu thông tin bản án xem xét lại
        if (!ArrayListCommon.isNullOrEmpty(reviewCaseAccusedList))
            reviewCaseAccusedList.forEach(x -> {
                x.setReviewCaseId(rc.getId());
                try {
                    saveReviewCaseAccused(x);
                } catch (Exception e) {
                    throw new CommonException(Response.SYSTEM_ERROR, "Lỗi khi thực hiện lưu thông tin bản án xem xét lại");
                }
            });
        //Lưu thông tin yêu cầu kiến nghị đề nghị
        if (!ArrayListCommon.isNullOrEmpty(reviewCaseRequestList))
            reviewCaseRequestList.forEach(x -> {
                x.setReviewCaseId(rc.getId());
                try {
                    saveReviewCaseRequest(x);
                } catch (Exception e) {
                    throw new CommonException(Response.SYSTEM_ERROR, "Lỗi khi thực hiện lưu thông tin yêu cầu kiến nghị đề nghị");
                }
            });
        return rc;
    }

    @Override
    public ReviewCase updateReviewCase(ReviewCase reviewCase) throws Exception {
        ValidateCommon.validateNullObject(reviewCase.getId(), "id");
        ReviewCase response = findReviewCaseById(reviewCase);
        if (response == null) throw new CommonException(Response.OBJECT_NOT_FOUND);
        //Lưu thông tin chính
        response = saveReviewCase(response.coppyFrom(reviewCase));
        //Lưu thông tin bản án xem xét lại cho các bị can
        List<ReviewCaseAccused> reviewCaseAccusedList = reviewCase.getReviewCaseAccusedList();
        List<ReviewCaseAccused> reviewCaseAccuseds = reviewCaseAccusedRepository.findByReviewCaseIdAndStatus(response.getId(), Constant.STATUS_OBJECT.ACTIVE);
        //Lưu hết các bản ghi ở trong db về trạng thái INACTIVE
        if (!ArrayListCommon.isNullOrEmpty(reviewCaseAccuseds))
            reviewCaseAccuseds.forEach(x -> {
                x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                try {
                    saveReviewCaseAccused(x);
                } catch (Exception e) {
                    throw new CommonException(Response.SYSTEM_ERROR, "Lỗi khi thực hiện cập nhật bản ghi bản án xem xét lại " + JsonCommon.objectToJsonNotNull(x));
                }
            });
        //Lưu hết các bản ghi từ client truyền xuống vào DB(nếu trùng với danh sách trên nó sẽ tự động update đè lên)
        if (!ArrayListCommon.isNullOrEmpty(reviewCaseAccusedList))
            reviewCaseAccusedList.forEach(x -> {
                try {
                    x.setReviewCaseId(reviewCase.getId());
                    saveReviewCaseAccused(x);
                } catch (Exception e) {
                    throw new CommonException(Response.SYSTEM_ERROR, "Lỗi khi thực hiện cập nhật bản ghi bản án xem xét lại " + JsonCommon.objectToJsonNotNull(x));
                }
            });

        //Lưu thông tin yêu cầu kiến nghị đề nghị
        List<ReviewCaseRequest> reviewCaseRequestList = reviewCase.getReviewCaseRequestList();
        List<ReviewCaseRequest> reviewCaseRequests = reviewCaseRequestRepository.findByReviewCaseIdAndStatus(response.getId(), Constant.STATUS_OBJECT.ACTIVE);
        //Lưu hết các bản ghi ở trong db về trạng thái INACTIVE
        if (!ArrayListCommon.isNullOrEmpty(reviewCaseRequests))
            reviewCaseRequests.forEach(x -> {
                x.setStatus(Constant.STATUS_OBJECT.INACTIVE);
                try {
                    saveReviewCaseRequest(x);
                } catch (Exception e) {
                    throw new CommonException(Response.SYSTEM_ERROR, "Lỗi khi thực hiện cập nhật bản ghi yêu cầu kiến nghị đề nghị " + JsonCommon.objectToJsonNotNull(x));
                }
            });
        //Lưu hết các bản ghi từ client truyền xuống vào DB(nếu trùng với danh sách trên nó sẽ tự động update đè lên)
        if (!ArrayListCommon.isNullOrEmpty(reviewCaseRequestList))
            reviewCaseRequestList.forEach(x -> {
                try {
                    x.setReviewCaseId(reviewCase.getId());
                    saveReviewCaseRequest(x);
                } catch (Exception e) {
                    throw new CommonException(Response.SYSTEM_ERROR, "Lỗi khi thực hiện cập nhật bản ghi yêu cầu kiến nghị đề nghị " + JsonCommon.objectToJsonNotNull(x));
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
            throw new CommonException(Response.OBJECT_NOT_FOUND, "Không tồn tại bản ghi xem xét lại của vụ án");
        reviewCase.setReviewCaseAccusedList(reviewCaseAccusedRepository.findByReviewCaseIdAndAccusedCodeAndStatus(reviewCaseAccused.getReviewCaseId(), reviewCaseAccused.getAccusedCode(), Constant.STATUS_OBJECT.ACTIVE));
        reviewCase.setReviewCaseRequestList(reviewCaseRequestRepository.findByReviewCaseIdAndAccusedCodeAndStatus(reviewCaseAccused.getReviewCaseId(), reviewCaseAccused.getAccusedCode(), Constant.STATUS_OBJECT.ACTIVE));
        return reviewCase;
    }
}
