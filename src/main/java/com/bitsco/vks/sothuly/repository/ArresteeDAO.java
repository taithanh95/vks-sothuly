package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.bitsco.vks.sothuly.model.SearchArresteeDTO;
import com.bitsco.vks.sothuly.request.ArresteeRequest;
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
public class ArresteeDAO {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<SearchArresteeDTO> search(ArresteeRequest request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] ArresteeDAO.search request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<SearchArresteeDTO> rm = new SingleColumnRowMapper<SearchArresteeDTO>() {
            @Override
            public SearchArresteeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                SearchArresteeDTO response = new SearchArresteeDTO();
                response.setId(rs.getLong("n_arrestee_id"));
                response.setDefendantName(rs.getString("s_defendant_name"));
                response.setArrestDate(rs.getDate("d_arrest_date"));
                response.setProcuracyHandlingDate(rs.getDate("d_procuracy_handling_date"));
                response.setArrestReason(rs.getString("s_arrest_reason"));
                response.setArrestViolation(rs.getString("s_arrest_violation"));
                response.setFullName(rs.getString("s_full_name"));
                response.setDateOfBirth(rs.getDate("d_date_of_birth"));
                response.setYearOfBirth(rs.getInt("n_year_of_birth"));
                response.setJob(rs.getString("s_job"));
                response.setWorkPlace(rs.getString("s_workplace"));
                response.setAddress(rs.getString("s_address"));
                response.setIsDead(rs.getBoolean("n_is_dead"));
                response.setDeathDate(rs.getDate("d_death_date"));
                response.setCauseOfDeathId(rs.getString("n_cause_of_death_id"));
                response.setCauseOfDeathName(rs.getString("s_cause_of_death_name"));
                response.setEscaped(rs.getBoolean("n_escaped"));
                response.setEscapingDate(rs.getDate("d_escaping_date"));
                response.setReasonForEscaping(rs.getString("s_reason_for_escaping"));
                response.setRecaptureDate(rs.getDate("d_recapture_date"));
                response.setMoveToAnotherPlace(rs.getBoolean("n_move_to_another_place"));
                response.setMoveToAnotherPlaceDate(rs.getDate("d_move_to_another_place_date"));
                response.setArriveFromAnotherPlace(rs.getBoolean("n_arrive_from_another_place"));
                response.setArriveFromAnotherPlaceDate(rs.getDate("d_arrive_from_another_place_date"));
                response.setReason(rs.getString("s_reason"));
                response.setDetentionPlace(rs.getString("s_detention_place"));
                response.setArriveFromAnotherPlaceName(rs.getString("s_arrive_from_another_place"));
                response.setMoveToAnotherPlaceName(rs.getString("s_move_to_another_place"));
                response.setArrestingUnitName(rs.getString("s_arresting_unit_name"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(SoThuLyConstant.PACKAGE.PKG_ARREST_DETENTION_ARRESTEE)
                    .withFunctionName(SoThuLyConstant.FUNCTION.FUN_SEARCH)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_full_name", StringCommon.isNullOrBlank(request.getFullName()) ? null : request.getFullName().trim())
                    .addValue("pi_from_date", request.getFromDate())
                    .addValue("pi_to_date", request.getToDate())
                    .addValue("pi_arrest_id", request.getId())
                    .addValue("pi_arresting_unit_id", request.getArrestingUnitId());
            return jdbcCall.executeFunction((Class<List<SearchArresteeDTO>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu PKG_ARREST_DETENTION_ARRESTEE.search", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] ArresteeDAO.search");
        }
    }

}
