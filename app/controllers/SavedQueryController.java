package controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import helpers.JSONParser;
import models.User;
import models.facets.Facet;
import models.facets.SavedQuery;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephen Yingling on 4/7/14.
 */
public class SavedQueryController extends Controller {

	@Security.Authenticated(Secured.class)
	public static Result saveQuery() {
		JsonNode json = request().body().asJson();

		ObjectMapper om = new ObjectMapper();
		String facetJson = json.get("facets").asText();

		List<Facet> facets = new ArrayList<Facet>();
		try {
			facets = om.readValue(facetJson, new TypeReference<List<Facet>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
			return ok();
		}
		String name = json.get("name").asText();
		String eventTypes = json.get("eventTypes").asText();
		String username = request().username();

		ObjectNode result = Json.newObject();

		SavedQuery sq = new SavedQuery();

		for (Facet f : facets) {
			sq.addFacet(f);
		}

		if (eventTypes != null && eventTypes.length() > 0
				&& eventTypes.equals("[]")) {
			String[] evs = eventTypes.substring(1, eventTypes.length() - 1)
					.split(",");

			for (String s : evs) {
				sq.addEventType(s);
			}
		} else {
			return ok();
		}

		sq.setName(name);

		User user = User.findByName(username);
		
		List<SavedQuery> existing = user.getQuerySubscriptions();
		if(existing.contains(sq)){
			flash("A query with this name already exists.");
		}
		else{
			user.addSavedQuery(sq);
			user.save();
		}

		// user.setUserEntitySubscriptionStatus(false, user, entityId,
		// entityType);

		return ok(result);
	}

	@Security.Authenticated(Secured.class)
	public static Result deleteQuerySubscription() {
		// Get json information sent in
		JsonNode json = request().body().asJson();

		String queryName = json.get("name").asText();
		String username = request().username();
		User user = User.findByName(username);

		user.removeSavedQuery(queryName);
		user.save();

		ObjectNode result = Json.newObject();

		return ok(result);
	}

	@Security.Authenticated(Secured.class)
	public static Result updateQuerySubscription() {

		JsonNode json = request().body().asJson();

		ObjectMapper om = new ObjectMapper();
		String facetJson = json.get("facets").asText();

		List<Facet> facets = new ArrayList<Facet>();
		try {
			facets = om.readValue(facetJson, new TypeReference<List<Facet>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
			return ok();
		}
		String name = json.get("name").asText();
		String username = request().username();

		SavedQuery sq = new SavedQuery();

		for (Facet f : facets) {
			sq.addFacet(f);
		}
		sq.setName(name);

		User user = User.findByName(username);
		user.updateSavedQuery(name, sq);
		user.save();

		ObjectNode result = Json.newObject();

		return ok(result);

	}

}
