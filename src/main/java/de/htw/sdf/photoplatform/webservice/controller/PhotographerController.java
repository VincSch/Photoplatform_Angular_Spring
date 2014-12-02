/**
 *
 */
package de.htw.sdf.photoplatform.webservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.sdf.photoplatform.exception.BadRequestException;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.manager.PhotographerManager;
import de.htw.sdf.photoplatform.persistence.model.Collection;
import de.htw.sdf.photoplatform.persistence.model.User;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * This controller present the REST user services.
 * All photographer functionality.
 *
 * @author Daniil Tomilow
 */
@RestController
public class PhotographerController extends BaseAPIController {


    @Autowired
    private PhotographerManager photographerManager;

    /**
     * Create new collection
     */
    @RequestMapping(value = Endpoints.COLLECTIONS, method = RequestMethod.POST)
    @ResponseBody
    public Collection createCollection(@RequestBody String json,
                                       BindingResult result) throws IOException, AbstractBaseException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        String name = mapper.convertValue(node.get("name"),
                String.class);

        if (name.isEmpty()) {
            result.addError(new FieldError("collection", "name",
                    messages.getMessage("Name.empty")));
            throw new BadRequestException("collection", result);
        }

        String description = mapper.convertValue(node.get("description"),
                String.class);

        User user = getLoggedInUser();

        return photographerManager.createCollection(user.getId(), name, description);
    }

    /**
     * Create new collection
     */
    @RequestMapping(value = Endpoints.COLLECTIONS, method = RequestMethod.GET)
    @ResponseBody
    public List<Collection> getCollections(@RequestParam int start,
                                           @RequestParam int count) throws IOException, AbstractBaseException {
        User user = getLoggedInUser();

        return photographerManager.getCollectionByUser(user.getId(), start, count);
    }
}
