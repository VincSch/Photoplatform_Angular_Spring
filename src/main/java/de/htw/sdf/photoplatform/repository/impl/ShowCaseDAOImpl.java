/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.repository.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import de.htw.sdf.photoplatform.persistence.models.ShowCase;
import de.htw.sdf.photoplatform.repository.ShowCaseDAO;
import de.htw.sdf.photoplatform.repository.common.GenericDAOImpl;

/**
 * Repository methods for showcase.
 *
 * @author Sergej Meister.
 */
@Repository
@Transactional
public class ShowCaseDAOImpl extends GenericDAOImpl<ShowCase> implements ShowCaseDAO
{
}
