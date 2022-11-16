package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.bitsco.vks.sothuly.entities.RegisterDecision;
import com.bitsco.vks.sothuly.request.RegisterDecisionRequest;
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
public class RegisterDecisionDAO {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<RegisterDecision> getList(RegisterDecisionRequest request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] RegisterDecisionDAO.getList request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<RegisterDecision> rm = new SingleColumnRowMapper<RegisterDecision>() {
            @Override
            public RegisterDecision mapRow(ResultSet rs, int rowNum) throws SQLException {
                RegisterDecision response = new RegisterDecision();
                response.setId(rs.getLong("n_id"));
                response.setCaseCode(rs.getString("s_case_code"));
                response.setCaseName(rs.getString("s_case_name"));
                response.setSppCode(rs.getString("s_spp_code"));
                response.setDecisionCode(rs.getString("s_decision_code"));
                response.setDecisionName(rs.getString("s_decision_name"));
                response.setAccusedCode(rs.getString("s_accused_code"));
                response.setAccusedName(rs.getString("s_accused_name"));
                response.setIssuesDate(rs.getDate("d_issues_date"));
                response.setDecisionNum(rs.getInt("n_decision_num"));
                response.setDecisionNumAuto(rs.getInt("n_decision_num_auto"));
                response.setStage(rs.getString("s_stage"));
                response.setStageName(rs.getString("s_stage_name"));
                response.setFromDate(rs.getDate("d_from_date"));
                response.setToDate(rs.getDate("d_to_date"));
                response.setType(rs.getInt("n_type"));
                response.setStatus(rs.getInt("n_status"));
                response.setCreatedAt(rs.getTimestamp("d_created_at"));
                response.setCreatedBy(rs.getString("s_created_by"));
                response.setUpdatedAt(rs.getTimestamp("d_updated_at"));
                response.setUpdatedBy(rs.getString("s_updated_by"));
                response.setNote(rs.getString("s_note"));
                response.setRutgon(rs.getString("s_rutgon"));
                response.setSppid(rs.getString("s_sppid"));
                response.setSigner(rs.getString("s_signer"));
                response.setPosition(rs.getString("s_position"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(SoThuLyConstant.PACKAGE.PKG_REGISTER_DECISION)
                    .withFunctionName(SoThuLyConstant.FUNCTION.GET_LIST_REGISTER_DECISION)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_type", request.getType())
                    .addValue("pi_stage", StringCommon.isNullOrBlank(request.getStage()) ? null : request.getStage())
                    .addValue("pi_decision_code", StringCommon.isNullOrBlank(request.getDecisionCode()) ? null : request.getDecisionCode().trim())
                    .addValue("pi_case_code", StringCommon.isNullOrBlank(request.getCaseCode()) ? null : StringCommon.addLikeRightAndLeft(request.getCaseCode()))
                    .addValue("pi_case_name", StringCommon.isNullOrBlank(request.getCaseName()) ? null : StringCommon.addLikeRightAndLeft(request.getCaseName()))
                    .addValue("pi_accused_code", StringCommon.isNullOrBlank(request.getAccusedCode()) ? null : StringCommon.addLikeRightAndLeft(request.getAccusedCode()))
                    .addValue("pi_accused_name", StringCommon.isNullOrBlank(request.getAccusedName()) ? null : StringCommon.addLikeRightAndLeft(request.getAccusedName()))
                    .addValue("pi_denouncement_id", request.getDenouncementId())
                    .addValue("pi_spp_code", StringCommon.isNullOrBlank(request.getSppCode()) ? null : request.getSppCode().trim())
                    .addValue("pi_spp_id", StringCommon.isNullOrBlank(request.getSppid()) ? null : request.getSppid().trim());
            return jdbcCall.executeFunction((Class<List<RegisterDecision>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu RegisterDecisionDAO.getList", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] RegisterDecisionDAO.getList");
        }
    }

    public List<RegisterDecision> getListByDenouncement(RegisterDecisionRequest request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] RegisterDecisionDAO.getListByDenouncement request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<RegisterDecision> rm = new SingleColumnRowMapper<RegisterDecision>() {
            @Override
            public RegisterDecision mapRow(ResultSet rs, int rowNum) throws SQLException {
                RegisterDecision response = new RegisterDecision();
                response.setId(rs.getLong("n_id"));
                response.setDenouncementId(rs.getLong("s_denouncement_id"));
                response.setDenouncementCode(rs.getString("s_denouncement_code"));
                response.setSReporter(rs.getString("s_reporter"));
                response.setSppCode(rs.getString("s_spp_code"));
                response.setDecisionCode(rs.getString("s_decision_code"));
                response.setDecisionName(rs.getString("s_decision_name"));
                response.setIssuesDate(rs.getDate("d_issues_date"));
                response.setDecisionNum(rs.getInt("n_decision_num"));
                response.setDecisionNumAuto(rs.getInt("n_decision_num_auto"));
                response.setStage(rs.getString("s_stage"));
                response.setStageName(rs.getString("s_stage_name"));
                response.setFromDate(rs.getDate("d_from_date"));
                response.setToDate(rs.getDate("d_to_date"));
                response.setType(rs.getInt("n_type"));
                response.setSppid(rs.getString("s_sppid"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(SoThuLyConstant.PACKAGE.PKG_REGISTER_DECISION)
                    .withFunctionName(SoThuLyConstant.FUNCTION.GET_LIST_REGISTER_DECISION_DENOUNCEMENT)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_type", request.getType())
                    .addValue("pi_denouncement_type", request.getDenouncementType())
                    .addValue("pi_decision_code", StringCommon.isNullOrBlank(request.getDecisionCode()) ? null : request.getDecisionCode())
                    .addValue("pi_reporter_name", StringCommon.isNullOrBlank(request.getReporterName()) ? null : StringCommon.addLikeRightAndLeft(request.getReporterName()))
                    .addValue("pi_denouncement_id", request.getDenouncementId())
                    .addValue("pi_spp_code", StringCommon.isNullOrBlank(request.getSppCode()) ? null : request.getSppCode().trim())
                    .addValue("pi_spp_id", StringCommon.isNullOrBlank(request.getSppid()) ? null : request.getSppid().trim());
            return jdbcCall.executeFunction((Class<List<RegisterDecision>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu RegisterDecisionDAO.getListByDenouncement", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] RegisterDecisionDAO.getListByDenouncement");
        }
    }
}
