package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.common.util.NumberCommon;
import com.bitsco.vks.common.util.StringCommon;
import com.bitsco.vks.sothuly.cache.CacheService;
import com.bitsco.vks.sothuly.entities.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * @Author phucnv
 * @create 4/9/2021 2:23 PM
 */
@Repository
@Slf4j
public class ArrestDetentionInfoRepositoryImpl implements ArrestDetentionInfoRepositoryCustom {
    @Autowired
    private ArrestDetentionInfoDAO infoDAO;

    @Autowired
    private CacheService cacheService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long getArrestDetentionInfoCodeInYear(int year) throws Exception {
        return infoDAO.getArrestDetentionInfoCode(year);
    }

    @Override
    public Optional<ArrestDetentionInfo> findByIdCustom(Long id) throws Exception {
        String queryStr = "SELECT a FROM ArrestDetentionInfo a where a.id=:id and a.status=1";
        TypedQuery<ArrestDetentionInfo> arrestDetentionInfoTypedQuery = this.entityManager.createQuery(queryStr, ArrestDetentionInfo.class);
        arrestDetentionInfoTypedQuery.setParameter("id", id);
        ArrestDetentionInfo arrestDetentionInfo = arrestDetentionInfoTypedQuery.getSingleResult();
        if (!StringCommon.isNullOrBlank(arrestDetentionInfo.getArrestEnactmentId()))
            arrestDetentionInfo.setLaw(cacheService.getLawFromCache(arrestDetentionInfo.getArrestEnactmentId()));
        try {
            if (!NumberCommon.isNullOrZero(arrestDetentionInfo.getId())) {

                String queryArrestee = "SELECT a FROM Arrestee a where a.arrestDetentionInfoId=:id and a.status=1";
                TypedQuery<Arrestee> arresteeTypedQuery = this.entityManager.createQuery(queryArrestee, Arrestee.class);
                arresteeTypedQuery.setParameter("id", arrestDetentionInfo.getId());
                List<Arrestee> lstArrestee = arresteeTypedQuery.getResultList();
                arrestDetentionInfo.setArrestees(lstArrestee);
                for (Arrestee arrestee : lstArrestee) {
                    String queryArrestSettlementDecision = "SELECT a FROM ArrestSettlementDecision a WHERE a.arresteeId=:id and a.status=1";
                    TypedQuery<ArrestSettlementDecision> arrestSettlementDecisionTypedQuery = this.entityManager.createQuery(queryArrestSettlementDecision, ArrestSettlementDecision.class);

                    arrestSettlementDecisionTypedQuery.setParameter("id", arrestee.getId());
                    arrestee.setSettlementDecisions(arrestSettlementDecisionTypedQuery.getResultList());

                    String queryLawOffense = "SELECT a from LawOffense a where a.arresteeId=:id and a.status=1";
                    TypedQuery<LawOffense> lawOffenseTypedQuery = this.entityManager.createQuery(queryLawOffense, LawOffense.class);
                    lawOffenseTypedQuery.setParameter("id", arrestee.getId());
                    arrestee.setLawOffenses(lawOffenseTypedQuery.getResultList());


                    String queryDisciplineViolation = "SELECT a from DisciplineViolation a where a.arresteeId=:id and a.status=1";
                    TypedQuery<DisciplineViolation> disciplineViolationTypedQuery = this.entityManager.createQuery(queryDisciplineViolation, DisciplineViolation.class);
                    disciplineViolationTypedQuery.setParameter("id", arrestee.getId());
                    arrestee.setDisciplineViolations(disciplineViolationTypedQuery.getResultList());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.of(arrestDetentionInfo);
    }
}
