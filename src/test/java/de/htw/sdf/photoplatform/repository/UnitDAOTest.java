package de.htw.sdf.photoplatform.repository;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.htw.sdf.photoplatform.common.BaseTester;
import de.htw.sdf.photoplatform.persistence.Unit;
import de.htw.sdf.photoplatform.persistence.Unit.GermanUnitName;
import de.htw.sdf.photoplatform.repository.UnitDAO;

public class UnitDAOTest extends BaseTester {

	@Autowired
	private UnitDAO unitDAO;

	@Before
	public void setUp() throws Exception {
		insertDestData();
	}

	@After
	public void tearDown() throws Exception {
		clearTables();
	}

	@Test
	public void testGetByName() throws Exception {
		Unit unit = unitDAO.findByGermanName(GermanUnitName.EL);
		assertTrue(unit.getGermanUnitName().equals(GermanUnitName.EL));
	}

	//@Test
	public void testDelete() throws Exception {
		assertTrue(unitDAO.findAll().size() == 8);
		Unit unit = unitDAO.findByGermanName(GermanUnitName.t);
		unitDAO.delete(unit);
		assertTrue(unitDAO.findAll().size() == 7);
	}

	//@Test
	public void testfindOne() throws Exception {
		Unit unit = unitDAO.findByGermanName(GermanUnitName.EL);
		Unit unit2 = unitDAO.findOne(unit.getId());
		assertTrue(unit2.getGermanUnitName().equals(GermanUnitName.EL));
	}

	//@Test
	public void testfindAll() throws Exception {
		List<Unit> units = unitDAO.findAll();
		assertTrue(units.size() == 8);
	}

	//@Test
	public void testCreate() throws Exception {
		Unit unit = unitDAO.findByGermanName(GermanUnitName.t);
		unitDAO.delete(unit);
		Unit unit2 = new Unit();
		unit2.setGermanUnitName(GermanUnitName.t);
		unitDAO.create(unit2);
		assertTrue(unitDAO.findAll().size() == 8);
	}

	//@Test
	public void testDeleteById() throws Exception {
		assertTrue(unitDAO.findAll().size() == 8);
		Unit unit = unitDAO.findByGermanName(GermanUnitName.t);
		unitDAO.deleteById(unit.getId());
		assertTrue(unitDAO.findAll().size() == 7);
	}
}
