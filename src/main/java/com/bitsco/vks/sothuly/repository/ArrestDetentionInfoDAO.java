package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.common.util.JsonCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.sothuly.cache.CacheService;
import com.bitsco.vks.sothuly.constant.SoThuLyConstant;
import com.bitsco.vks.sothuly.model.ArrestDetentionInfoDTO;
import com.bitsco.vks.sothuly.request.ArrestDetentionInfoRequest;
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

/**
 * @Author phucnv
 * @create 4/13/2021 3:41 PM
 */
@Service
public class ArrestDetentionInfoDAO {

    //inner class act as a model to map ResultSet
    class LongResult {
        public Long value;

        public LongResult() {
        }

        public LongResult(Long value) {
            this.value = value;
        }

        public Long getValue() {
            return value;
        }

        public void setValue(Long value) {
            this.value = value;
        }
    }
    //end inner class

    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.DB);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    CacheService cacheService;

    public List<ArrestDetentionInfoDTO> search(ArrestDetentionInfoRequest request) throws Exception {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[B][" + startTime + "] ArrestDetentionInfoDAO.search request = " + JsonCommon.objectToJsonNotNull(request));
        SimpleJdbcCall jdbcCall;
        RowMapper<ArrestDetentionInfoDTO> rm = new SingleColumnRowMapper<ArrestDetentionInfoDTO>() {
            @Override
            public ArrestDetentionInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                ArrestDetentionInfoDTO response = new ArrestDetentionInfoDTO();
                response.setStt(rs.getRow());
                response.setId(rs.getLong("N_ARREST_DETENTION_INFO_ID"));
                response.setCode(rs.getInt("N_ARREST_DETENTION_INFO_CODE"));
                response.setArrestingUnitName(rs.getString("S_ARRESTING_UNIT_NAME"));
                response.setArrestingUnitName(rs.getString("S_ARRESTING_UNIT_NAME"));
                response.setArresteeName(rs.getString("ARRESTEE_NAME"));
                response.setArrestContent(rs.getString("S_ARREST_CONTENT"));
                response.setCreateUser(rs.getString("S_CREATED_BY"));
                response.setProcuracyTakenOverDate(rs.getDate("D_PROCURACY_TAKEN_OVER_DATE"));
                response.setShareInfoLevel(rs.getInt("N_SHARE_INFO_LEVEL"));
                response.setSppId(rs.getString("SPP_ID"));
                return response;
            }
        };
        try {
            jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withCatalogName(SoThuLyConstant.PACKAGE.PKG_ARREST_DETENTION_INFO)
                    .withFunctionName(SoThuLyConstant.FUNCTION.FUN_SEARCH)
                    .returningResultSet("return", rm);
            SqlParameterSource paramMap = new MapSqlParameterSource()
                    .addValue("pi_arrestee_name", StringCommon.isNullOrBlank(request.getArresteeName()) ? null : request.getArresteeName().trim())
                    .addValue("pi_from_date", request.getFromDate())
                    .addValue("pi_to_date", request.getToDate())
                    .addValue("pi_sppid", request.getSppId())
                    .addValue("pi_code", StringCommon.isNullOrBlank(request.getCodeDetention()) ? null : request.getCodeDetention().trim())
                    .addValue("pi_decision_number", StringCommon.isNullOrBlank(request.getDecisionNumber()) ? null : StringCommon.addLikeRightAndLeft(request.getDecisionNumber()))
                    .addValue("pi_username", cacheService.getUsernameFromHeader());
            return jdbcCall.executeFunction((Class<List<ArrestDetentionInfoDTO>>) (Class) List.class, paramMap);
        } catch (Exception e) {
            LOGGER.error("[Exception][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] khi truy vấn dữ liệu ArrestDetentionInfoDAO.search", e);
            throw e;
        } finally {
            LOGGER.info("[E][" + startTime + "][Duration = " + (System.currentTimeMillis() - startTime) + "] ArrestDetentionInfoDAO.search");
        }
    }


    public Long getArrestDetentionInfoCode(int year) throws Exception {
        SimpleJdbcCall jdbcCall;
        RowMapper<LongResult> rm = new SingleColumnRowMapper() {
            @Override
            public LongResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                LongResult l = new LongResult();
                l.setValue(rs.getLong("INFOR_CODE"));
                return l;
            }
        };

        jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(SoThuLyConstant.PACKAGE.PKG_ARRESTEE_DETENTION_INFOR_PACKAGE)
                .withFunctionName(SoThuLyConstant.FUNCTION.FUN_GET_CODE_IN_YEAR)
                .returningResultSet("return", rm);
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("pi_year", year);
        List<LongResult> lr = jdbcCall.executeFunction((Class<List<LongResult>>) (Class) List.class, params);
        Long code = lr.get(0).getValue();
        if (code != null) {
            return code.longValue() + 1;
        } else {
            LOGGER.error("[Exception]: AT: [" + System.currentTimeMillis() + "] khi truy vấn dữ liệu ArrestDetentionInfoRepositoryImpl.getArrestDetentionInfoCodeInYear");
            return 1L;
        }
    }
}
