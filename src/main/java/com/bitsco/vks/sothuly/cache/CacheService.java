package com.bitsco.vks.sothuly.cache;

import com.bitsco.vks.common.constant.Constant;
import com.bitsco.vks.sothuly.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: truongnq
 * @date: 06-May-19 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CacheService {
    @Autowired
    RedisTemplate<String, User> userRedis;

    @Autowired
    RedisTemplate<String, Spp> sppRedis;

    @Autowired
    RedisTemplate<String, Law> lawRedis;

    @Autowired
    RedisTemplate<String, Case> caseRedis;

    @Autowired
    RedisTemplate<String, Accused> accusedRedis;

    @Autowired
    RedisTemplate<String, Decision> decisionRedis;

    @Autowired
    HttpServletRequest request;

    public void addLawRedisCache(Law law) {
        final ValueOperations<String, Law> opsForValue = lawRedis.opsForValue();
        String key = Constant.TABLE.LST_LAW + Constant.DASH + law.getLawCode().trim().toLowerCase();
        opsForValue.set(key, law, 365, TimeUnit.DAYS);
    }

    public Law getLawFromCache(String lawCode) {
        final ValueOperations<String, Law> opsForValue = lawRedis.opsForValue();
        String key = Constant.TABLE.LST_LAW + Constant.DASH + lawCode.trim().toLowerCase();
        if (lawRedis.hasKey(key))
            return opsForValue.get(key);
        else return null;
    }

    public void addSpp2RedisCache(Spp spp) {
        final ValueOperations<String, Spp> opsForValue = sppRedis.opsForValue();
        String key = Constant.TABLE.LST_SPP + Constant.DASH + spp.getSppId().trim().toLowerCase();
        opsForValue.set(key, spp, 365, TimeUnit.DAYS);
    }

    public Spp getSppFromCache(String sppCode) {
        final ValueOperations<String, Spp> opsForValue = sppRedis.opsForValue();
        String key = Constant.TABLE.LST_SPP + Constant.DASH + sppCode.trim().toLowerCase();
        if (sppRedis.hasKey(key))
            return opsForValue.get(key);
        else return null;
    }

    public void addCase2RedisCache(Case caseInput) {
        final ValueOperations<String, Case> opsForValue = caseRedis.opsForValue();
        String key = Constant.TABLE.SPP_CASE + Constant.DASH + caseInput.getCaseCode().trim().toLowerCase();
        opsForValue.set(key, caseInput, 365, TimeUnit.DAYS);
    }

    public Case getCaseFromCache(String caseCode) {
        final ValueOperations<String, Case> opsForValue = caseRedis.opsForValue();
        String key = Constant.TABLE.SPP_CASE + Constant.DASH + caseCode.trim().toLowerCase();
        if (caseRedis.hasKey(key))
            return opsForValue.get(key);
        else return null;
    }

    public void addAccused2RedisCache(Accused accused) {
        final ValueOperations<String, Accused> opsForValue = accusedRedis.opsForValue();
        String key = Constant.TABLE.SPP_ACCUSED + Constant.DASH + accused.getAccuCode().trim().toLowerCase();
        opsForValue.set(key, accused, 365, TimeUnit.DAYS);
    }

    public Accused getAccusedFromCache(String accusedCode) {
        final ValueOperations<String, Accused> opsForValue = accusedRedis.opsForValue();
        String key = Constant.TABLE.SPP_ACCUSED + Constant.DASH + accusedCode.trim().toLowerCase();
        if (accusedRedis.hasKey(key))
            return opsForValue.get(key);
        else return null;
    }

    public void addDecision2RedisCache(Decision decision) {
        final ValueOperations<String, Decision> opsForValue = decisionRedis.opsForValue();
        String key = Constant.TABLE.LST_DECISION + Constant.DASH + decision.getDeciId().trim().toLowerCase();
        opsForValue.set(key, decision, 365, TimeUnit.DAYS);
    }

    public Decision getDecisionFromCache(String decisionId) {
        final ValueOperations<String, Decision> opsForValue = decisionRedis.opsForValue();
        String key = Constant.TABLE.LST_DECISION + Constant.DASH + decisionId.trim().toLowerCase();
        if (decisionRedis.hasKey(key))
            return opsForValue.get(key);
        else return null;
    }

    public User getUserFromCache(String username) {
        final ValueOperations<String, User> opsForValue = userRedis.opsForValue();
        String key = Constant.TABLE.USERS + Constant.DASH + username.trim().toLowerCase();
        if (userRedis.hasKey(key))
            return opsForValue.get(key);
        else return null;
    }

    public String getHeaderValue(String headerName) {
        try {
            return request.getHeader(headerName);
        } catch (Exception e) {
            return null;
        }
    }

    public String getUsernameFromHeader() {
        return getHeaderValue(Constant.KEY.USERNAME);
    }

}
