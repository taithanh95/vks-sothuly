package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.bitsco.vks.sothuly.request.DenouncedDenouncementRequest;
import com.bitsco.vks.sothuly.response.DenouncedDenouncementResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: anhnb
 * Date: 5/4/2021
 * Time: 5:06 PM
 */
@Repository
public class DenouncedDenouncementDAOImpl implements DenouncedDenouncementDAO {

    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<DenouncedDenouncementResponse> findAll(DenouncedDenouncementRequest request) {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] DenouncedDenouncementDAOImpl.findAll DenouncedDenouncementRequest = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall simpleJdbcCall = null;
        RowMapper<DenouncedDenouncementResponse> rm = new SingleColumnRowMapper<DenouncedDenouncementResponse>() {
            @Override
            public DenouncedDenouncementResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                DenouncedDenouncementResponse response = new DenouncedDenouncementResponse();
                response.setId(rs.getLong("n_denouncement_id"));
                response.setDenouncementCode(rs.getString("s_denouncement_code"));
                response.setCrimeReportSource(rs.getString("s_crime_report_source"));
                response.setRReporter(rs.getString("s_reporter"));
                response.setRDelation(rs.getString("s_delation"));
                response.setTakenOverDate(rs.getDate("d_taken_over_date"));
                response.setFullName(rs.getString("s_accused_name"));
                return response;
            }
        };
        try {
            simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(SoThuLyConstant.PACKAGE.PKG_DENOUNCE_DENOUNCEMENT)
                    .withFunctionName(SoThuLyConstant.FUNCTION.GET_LIST_DENOUNCE_DENOUNCEMENT)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_username", StringCommon.isNullOrBlank(request.getUsername()) ? null : request.getUsername().trim())
                    .addValue("pi_sppid", StringCommon.isNullOrBlank(request.getSppId()) ? null : request.getSppId())
                    .addValue("pi_denouncement_code", StringCommon.isNullOrBlank(request.getDenouncementCode()) ? null : StringCommon.addLikeRightAndLeft(request.getDenouncementCode()))
                    .addValue("pi_crime_report_source", StringCommon.isNullOrBlank(request.getCrimeReportSource()) ? null : request.getCrimeReportSource().trim())
                    .addValue("pi_reporter", StringCommon.isNullOrBlank(request.getReporter()) ? null : StringCommon.addLikeRightAndLeft(request.getReporter()))
                    .addValue("pi_accused_name", StringCommon.isNullOrBlank(request.getAccusedName()) ? null : StringCommon.addLikeRightAndLeft(request.getAccusedName()));
            return simpleJdbcCall.executeFunction((Class<List<DenouncedDenouncementResponse>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu DenouncedDenouncementDAOImpl.findAll", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] DenouncedDenouncementDAOImpl.findAll");
        }
    }

    @Override
    public DenouncedDenouncementResponse findById(DenouncedDenouncementRequest request) {
        return null;
    }

    @Override
    public List<DenouncedDenouncementResponse> search(DenouncedDenouncementRequest request) {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] DenouncedDenouncementDAOImpl.search DenouncedDenouncementRequest = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall simpleJdbcCall = null;
        RowMapper<DenouncedDenouncementResponse> rm = new SingleColumnRowMapper<DenouncedDenouncementResponse>() {
            @Override
            public DenouncedDenouncementResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                DenouncedDenouncementResponse response = new DenouncedDenouncementResponse();
                response.setStt(rs.getRow());
                response.setId(rs.getLong("DENOUNCEMENT_ID"));
                response.setDenouncementCode(rs.getString("DENOUNCEMENT_CODE"));
                response.setCrimeReportSource(rs.getString("CRIME_REPORT_SOURCE"));
                response.setTakenOverDate(rs.getDate("TAKEN_OVER_DATE"));
                response.setRReporter(rs.getString("R_REPORTER"));
                response.setNameAccused(rs.getString("FULL_NAME"));
                response.setDecisionName(rs.getString("DECISION_NAME"));
                response.setSettlementStatus(rs.getInt("STATUS_VALUE"));
                response.setCreateUser(rs.getString("CREATE_USER"));
                response.setSppId(rs.getString("SPPID"));
                response.setIpnEnactmentId(rs.getString("IPN_ENACTMENT_ID"));
                response.setShareInfoLevel(rs.getInt("share_info_level"));
                response.setRDelation(rs.getString("R_DELATION"));
                response.setCasecode(rs.getString("CASECODE"));
                response.setCasename(rs.getString("CASENAME"));
                return response;
            }
        };
        try {
            simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(SoThuLyConstant.PACKAGE.PKG_DENOUNCE_DENOUNCEMENT)
                    .withFunctionName(SoThuLyConstant.FUNCTION.SEARCH_DENOUNCE_DENOUNCEMENT)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_handling_from_date", DateCommon.convertDateToString(request.getPhandlingFromDate()))
                    .addValue("pi_handling_to_date", DateCommon.convertDateToString(request.getPhandlingToDate()))
                    .addValue("pi_verification_from_date", DateCommon.convertDateToString(request.getVerificationFromDate()))
                    .addValue("pi_verification_to_date", DateCommon.convertDateToString(request.getVerificationToDate()))
                    .addValue("pi_denouncement_code", StringCommon.isNullOrBlank(request.getDenouncementCode()) ? null : StringCommon.addLikeRightAndLeft(request.getDenouncementCode()).toLowerCase())
                    .addValue("pi_crime_report_source_arr", StringCommon.isNullOrBlank(request.getCrimeReportSourceList()) ? null : request.getCrimeReportSourceList())
                    .addValue("pi_taken_over_agencies_arr", StringCommon.isNullOrBlank(request.getListTakenOverAgency()) ? null : request.getListTakenOverAgency())
                    .addValue("pi_reporter", StringCommon.isNullOrBlank(request.getReporter()) ? null : StringCommon.addLikeRightAndLeft(request.getReporter().trim().toLowerCase()))
                    .addValue("pi_taken_over_officer", StringCommon.isNullOrBlank(request.getTakenOverOfficer()) ? null : StringCommon.addLikeRightAndLeft(request.getTakenOverOfficer().trim().toLowerCase()))
                    .addValue("pi_decision_arr", StringCommon.isNullOrBlank(request.getDecisionId()) ? null : request.getDecisionId())
                    .addValue("pi_status_arr", StringCommon.isNullOrBlank(request.getStatusList()) ? null : request.getStatusList())
                    .addValue("pi_spp_id", request.getSppId())
                    .addValue("pi_username", StringCommon.isNullOrBlank(request.getUsername()) ? null : request.getUsername().trim())
                    .addValue("pi_ia_assignment_decision_number", StringCommon.isNullOrBlank(request.getIaAssignmentDecisionNumber()) ? null : StringCommon.addLikeRightAndLeft(request.getIaAssignmentDecisionNumber()))
                    .addValue("pi_ia_handling_officer", StringCommon.isNullOrBlank(request.getIaHandlingOfficer()) ? null : StringCommon.addLikeRightAndLeft(request.getIaHandlingOfficer().trim().toLowerCase()))
                    .addValue("pi_handling_number", StringCommon.isNullOrBlank(request.getPhandlingNumber()) ? -1 : request.getPhandlingNumber())
                    .addValue("pi_phandling_prosecutor_id", StringCommon.isNullOrBlank(request.getPhandlingProsecutorId()) ? null : request.getPhandlingProsecutorId())
                    .addValue("pi_passignment_decision_number", StringCommon.isNullOrBlank(request.getPassignmentDecisionNumber()) ? null : request.getPassignmentDecisionNumber())
                    .addValue("pi_ipn_settlement_agency", StringCommon.isNullOrBlank(request.getIpnSettlementAgency()) ? null : request.getIpnSettlementAgency())
                    .addValue("pi_ipn_classified_news", StringCommon.isNullOrBlank(request.getIpnClassifiedNews()) ? null : request.getIpnClassifiedNews())
                    .addValue("pi_ipn_enactment_id_arr", StringCommon.isNullOrBlank(request.getIpnEnactmentId()) ? null : request.getIpnEnactmentId())
                    .addValue("pi_corruption_crime", Boolean.TRUE.equals(request.getCorruptionCrime()) ? 1 : 0)
                    .addValue("pi_economic_crime", Boolean.TRUE.equals(request.getEconomicCrime()) ? 1 : 0)
                    .addValue("pi_other_crime", Boolean.TRUE.equals(request.getOtherCrime()) ? 1 : 0)
                    .addValue("pi_verification_investigation_code", StringCommon.isNullOrBlank(request.getVerificationInvestigationCode()) ? null : request.getVerificationInvestigationCode())
                    .addValue("pi_type", StringCommon.isNullOrBlank(request.getType()) ? null : request.getType())
                    .addValue("pi_investigation_activity_type", StringCommon.isNullOrBlank(request.getInvestigationActivityType()) ? null : request.getInvestigationActivityType())
                    ;
            return simpleJdbcCall.executeFunction((Class<List<DenouncedDenouncementResponse>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu DenouncedDenouncementDAOImpl.search", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] DenouncedDenouncementDAOImpl.search");
        }
    }

}
