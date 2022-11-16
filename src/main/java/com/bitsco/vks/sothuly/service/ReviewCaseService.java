package com.bitsco.vks.sothuly.service;

import com.bitsco.vks.common.request.PageRequest;
import com.bitsco.vks.common.response.PageResponse;
import com.bitsco.vks.sothuly.entities.ReviewCase;
import com.bitsco.vks.sothuly.entities.ReviewCaseAccused;
import com.bitsco.vks.sothuly.entities.ReviewCaseRequest;
import com.bitsco.vks.sothuly.request.ReviewCaseAccusedRequest;
import com.bitsco.vks.sothuly.response.ReviewCaseAccusedResponse;

import java.util.List;

public interface ReviewCaseService {
    ReviewCase createReviewCase(ReviewCase reviewCase) throws Exception;

    ReviewCase updateReviewCase(ReviewCase reviewCase) throws Exception;

    ReviewCaseAccused createReviewCaseAccused(ReviewCaseAccused reviewCaseAccused) throws Exception;

    ReviewCaseAccused updateReviewCaseAccused(ReviewCaseAccused reviewCaseAccused) throws Exception;

    ReviewCaseRequest createReviewCaseRequest(ReviewCaseRequest reviewCaseRequest) throws Exception;

    ReviewCaseRequest updateReviewCaseRequest(ReviewCaseRequest reviewCaseRequest) throws Exception;

    ReviewCase findReviewCaseById(ReviewCase reviewCase) throws Exception;

    ReviewCase findReviewCaseByCaseCode(ReviewCase reviewCase) throws Exception;

    List<ReviewCaseAccusedResponse> getListReviewCaseAccused(ReviewCaseAccusedRequest reviewCase) throws Exception;

    PageResponse getPageReviewCaseAccused(PageRequest pageRequest) throws Exception;

    ReviewCase detailReviewCaseByAccused(ReviewCaseAccused reviewCaseAccused) throws Exception;
}
