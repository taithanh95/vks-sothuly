package com.bitsco.vks.sothuly.repository;

import com.bitsco.vks.sothuly.entities.ArrestSettlementDecision;
import com.bitsco.vks.sothuly.entities.Arrestee;
import com.bitsco.vks.sothuly.entities.DisciplineViolation;
import com.bitsco.vks.sothuly.entities.LawOffense;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
@Slf4j
public class ArresteeRepositoryImpl implements ArresteeRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Arrestee> findByIdCustom(Long id) throws Exception {
        String queryArrestee = "SELECT a FROM Arrestee a where a.id=:id and a.status=1";
        TypedQuery<Arrestee> arresteeTypedQuery = this.entityManager.createQuery(queryArrestee, Arrestee.class);
        arresteeTypedQuery.setParameter("id", id);
        Arrestee arrestee = arresteeTypedQuery.getSingleResult();
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.of(arrestee);
    }
}
