package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.bitsco.vks.sothuly.request.ReviewCaseAccusedRequest;
import com.bitsco.vks.sothuly.response.ReviewCaseAccusedResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class ReviewCaseDAO {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<ReviewCaseAccusedResponse> getListReviewCaseAccused(ReviewCaseAccusedRequest request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] ReviewCaseDAO.getListReviewCaseAccused request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<ReviewCaseAccusedResponse> rm = new SingleColumnRowMapper<ReviewCaseAccusedResponse>() {
            @Override
            public ReviewCaseAccusedResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                ReviewCaseAccusedResponse response = new ReviewCaseAccusedResponse();
                response.setReviewCaseId(rs.getLong("n_review_case_id"));
                response.setReviewCaseAccusedId(rs.getLong("n_review_case_accused_id"));
                response.setCaseCode(rs.getString("s_case_code"));
                response.setCaseName(rs.getString("s_case_name"));
                response.setAccusedCode(rs.getString("s_accused_code"));
                response.setAccusedName(rs.getString("s_accused_name"));
                response.setJudgmentNum(rs.getString("s_judgment_num"));
                response.setJudgmentDate(rs.getDate("d_judgment_date"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(SoThuLyConstant.PACKAGE.PKG_REVIEW_CASE)
                    .withFunctionName(SoThuLyConstant.FUNCTION.GET_LIST_REVIEW_CASE_ACCUSED)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_case_code", StringCommon.isNullOrBlank(request.getCaseCode()) ? null : request.getCaseCode().trim())
                    .addValue("pi_case_name", StringCommon.isNullOrBlank(request.getCaseName()) ? null : StringCommon.addLikeRightAndLeft(request.getCaseName().trim()))
                    .addValue("pi_accused_code", StringCommon.isNullOrBlank(request.getAccusedCode()) ? null : request.getAccusedCode().trim())
                    .addValue("pi_accused_name", StringCommon.isNullOrBlank(request.getAccusedName()) ? null : StringCommon.addLikeRightAndLeft(request.getAccusedName().trim()))
                    .addValue("pi_judgment_num", StringCommon.isNullOrBlank(request.getJudgmentNum()) ? null : request.getJudgmentNum().trim());
            return jdbcCall.executeFunction((Class<List<ReviewCaseAccusedResponse>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu ReviewCaseDAO.getList", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] ReviewCaseDAO.getListReviewCaseAccused");
        }
    }
}
