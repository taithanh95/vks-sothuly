package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.bitsco.vks.sothuly.request.ViolationRequest;
import com.bitsco.vks.sothuly.response.ViolationResponse;
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
public class ViolationDAO {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ViolationResponse> getListViolationLaw(ViolationRequest request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] ViolationDAO.getListViolationLaw request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<ViolationResponse> rm = new SingleColumnRowMapper<ViolationResponse>() {
            @Override
            public ViolationResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                ViolationResponse response = new ViolationResponse();
                response.setId(rs.getLong("n_id"));
                response.setViolatedUnitsId(rs.getString("s_violated_units_id"));
                response.setViolatedUnitsName(rs.getString("s_violated_units_name"));
                response.setViolationDate(rs.getDate("d_violation_date"));
                response.setResultName(rs.getString("s_result_name"));
                response.setDocumentName(rs.getString("s_document_name"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(SoThuLyConstant.PACKAGE.PKG_VIOLATION)
                    .withFunctionName(SoThuLyConstant.FUNCTION.GET_LIST_VIOLATION)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_violation_id", request.getId() == null ? -1 : request.getId())
                    .addValue("pi_violated_agency", StringCommon.isNullOrBlank(request.getViolatedAgency()) ? null : request.getViolatedAgency().trim())
                    .addValue("pi_violated_units_id", StringCommon.isNullOrBlank(request.getViolatedUnitsId()) ? null : request.getViolatedUnitsId().trim())
                    .addValue("pi_document_code", request.getDocumentCode() == null ? -1 : request.getDocumentCode())
                    .addValue("pi_chap_nhan", request.checkChapNhan("CHAP_NHAN") ? "Y" : null)
                    .addValue("pi_chap_nhan_mot_phan", request.checkChapNhan("CHAP_NHAN_MOT_PHAN") ? "Y" : null)
                    .addValue("pi_khong_chap_nhan", request.checkChapNhan("KHONG_CHAP_NHAN") ? "Y" : null)
                    .addValue("pi_khong_chap_nhan_mot_han", request.checkChapNhan("KHONG_CHAP_NHAN_MOT_PHAN") ? "Y" : null)
                    .addValue("pi_username", StringCommon.isNullOrBlank(request.getUsername()) ? null : request.getUsername().trim())
                    .addValue("pi_sppid", StringCommon.isNullOrBlank(request.getSppid()) ? null : request.getSppid());
            return jdbcCall.executeFunction((Class<List<ViolationResponse>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu ViolationDAO.getListViolationLaw", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] ViolationDAO.getListViolationLaw");
        }
    }
}
