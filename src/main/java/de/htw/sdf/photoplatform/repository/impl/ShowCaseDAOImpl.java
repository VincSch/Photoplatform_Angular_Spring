/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import de.htw.sdf.photoplatform.persistence.model.ShowCase;
import de.htw.sdf.photoplatform.repository.ShowCaseDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Repository methods for showcase.
 *
 * @author Sergej Meister.
 */
@Repository
@Transactional
public class ShowCaseDAOImpl extends GenericDAOImpl<ShowCase> implements
        ShowCaseDAO {
}
