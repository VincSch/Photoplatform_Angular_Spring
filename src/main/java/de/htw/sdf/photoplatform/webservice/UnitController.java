package de.htw.sdf.photoplatform.webservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.htw.sdf.photoplatform.manager.UnitManager;
import de.htw.sdf.photoplatform.persistence.Unit;
import de.htw.sdf.photoplatform.webservice.common.BaseAPIController;

@Controller
public class UnitController extends BaseAPIController {

	@Autowired
	UnitManager unitManager;

	@RequestMapping(value = "/unit/all", method = RequestMethod.GET)
	@ResponseBody
	public List<Unit> retrieveAllUnits() throws Exception {
		List<Unit> unitList = unitManager.findAll();
		if (unitList == null)
			throw new Exception("No recipes found!");
		else
			return unitList;
	}
}
