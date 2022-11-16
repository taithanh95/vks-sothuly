package com.bitsco.vks.sothuly.repository;

import java.util.Optional;

/**
 * Author: LamPT
 * Company: Vissoft
 * Project: vis_project
 * Package: com.bitsco.vks.sothuly.repository
 * Create_date: 4/26/2021, 11:53 AM
 * Create with: Intellij IDEA
 */
public interface ArrestSettlementDecisionRepositoryCustom {
    Long getMaxExecuteOrder(Long arresteeId) throws Exception;
}
