package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.DateCommon;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.bitsco.vks.sothuly.request.CompensationRequest;
import com.bitsco.vks.sothuly.response.CompensationResponse;
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
public class CompensationDAO {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CompensationResponse> getList(CompensationRequest request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] CompensationDAO.getList request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<CompensationResponse> rm = new SingleColumnRowMapper<CompensationResponse>() {
            @Override
            public CompensationResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                CompensationResponse response = new CompensationResponse();
                response.setId(rs.getLong("n_id"));
                response.setCompensationDate(rs.getDate("d_compensation_date"));
                response.setClaimantName(rs.getString("s_claimant_name"));
                response.setDamagesName(rs.getString("s_damages_name"));
                response.setResultName(rs.getString("s_result_name"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(SoThuLyConstant.PACKAGE.PKG_COMPENSATION)
                    .withFunctionName(SoThuLyConstant.FUNCTION.GET_LIST_COMPENSATION)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_from_date", DateCommon.convertDateToString(request.getFromDate()))
                    .addValue("pi_to_date", DateCommon.convertDateToString(request.getToDate()))
                    .addValue("pi_compensation_id", request.getId() == null ? -1 : request.getId())
                    .addValue("pi_claimant_name", StringCommon.isNullOrBlank(request.getClaimantName()) ? null : StringCommon.addLikeRightAndLeft(request.getClaimantName().trim()))
                    .addValue("pi_damages_name", StringCommon.isNullOrBlank(request.getDamagesName()) ? null : StringCommon.addLikeRightAndLeft(request.getDamagesName().trim()))
                    .addValue("pi_result_code", request.getResultCode() == null ? -1 : request.getResultCode())
                    .addValue("pi_username", StringCommon.isNullOrBlank(request.getUsername()) ? null : request.getUsername().trim())
                    .addValue("pi_sppid", StringCommon.isNullOrBlank(request.getSppid()) ? null : request.getSppid());
            return jdbcCall.executeFunction((Class<List<CompensationResponse>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu CompensationDAO.getList", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] CompensationDAO.getList");
        }
    }
}
