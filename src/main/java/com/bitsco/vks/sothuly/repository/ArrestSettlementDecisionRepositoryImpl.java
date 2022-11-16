package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.common.util.NumberCommon;
import com.bitsco.vks.sothuly.entities.ArrestSettlementDecision;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Author: LamPT
 * Company: Vissoft
 * Project: vis_project
 * Package: com.bitsco.vks.sothuly.repository
 * Create_date: 4/26/2021, 11:57 AM
 * Create with: Intellij IDEA
 */
@Repository
@Slf4j
public class ArrestSettlementDecisionRepositoryImpl implements ArrestSettlementDecisionRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long getMaxExecuteOrder(Long arresteeId) throws Exception {
        String queryStr="SELECT MAX(a.executeOrder) FROM ArrestSettlementDecision a WHERE a.arresteeId = :arresteeId AND a.status=1";
        TypedQuery<Long> settlementDecisionTypedQuery=this.entityManager.createQuery(queryStr,Long.class);
        settlementDecisionTypedQuery.setParameter("arresteeId",arresteeId);
        Long executeOrder=settlementDecisionTypedQuery.getSingleResult();
        if(!NumberCommon.isNullOrZero(executeOrder)){
            return executeOrder;
        }else{
            return 0L;
        }
    }
}
