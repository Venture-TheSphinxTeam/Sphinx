package models;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.mongodb.*;

import play.Play;
import play.data.validation.Constraints.*;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import controllers.Ingester;

import org.jongo.*;

import uk.co.panaxiom.playjongo.PlayJongo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entity {

	public Entity() {
		this.allowedAccessUsers = new ArrayList<String>();
	}

	protected String entityId;
	protected String entityType;// TODO: Change from String to Enum
	protected Entity workBreakdownParent;
	protected List<Entity> otherParents;
	protected String key;
	protected String summary;
	protected String description;
	protected String priority;
	protected String status;
	protected long dueDate;
	protected String reporter;
	protected String assignee;
	protected List<String> watchers;
	protected List<String> labels;
	protected long created;
	protected long updated;
	protected List<String> allowedAccessUsers;
	private final String IMAGE_LOCATION_I = "images/icon-initiative.png";
	private final String IMAGE_LOCATION_M = "images/icon-milestone.png";
	private final String IMAGE_LOCATION_R = "images/icon-risk.png";

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public Entity getWorkBreakdownParent() {
		return workBreakdownParent;
	}

	public void setWorkBreakdownParent(Entity wbp) {

		if (wbp != null) {
			String entityType = wbp.getEntityType();
			Entity e = null;
			if (entityType.equals(Initiative.TYPE_STRING)) {
				e = Initiative.getFirstInitiativeById(wbp.getEntityId());
			} else if (entityType.equals(Milestone.TYPE_STRING)) {
				e = Milestone.getFirstWithId(wbp.getEntityId());
			} else if (entityType.equals(Risk.TYPE_STRING)) {
				e = Risk.getFirstWithId(wbp.getEntityId());
			}

			if (e != null) {
				this.workBreakdownParent = e;
			} else {
				String base = Play.application().configuration()
						.getString("external.json.source");
				Ingester i = new Ingester(base + "/entity/" + entityType + "/"
						+ wbp.getEntityId());
				String ent = i.getResponse();
				if (ent != null) {
					ObjectMapper om = new ObjectMapper();
					try {
						this.workBreakdownParent = om.readValue(ent, Entity.class);
					} catch (JsonParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (JsonMappingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}

	}

	public List<Entity> getOtherParents() {
		return otherParents;
	}

	public void setOtherParents(List<Entity> otherParents) {
		this.otherParents = otherParents;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getDueDate() {
		return dueDate;
	}

	public void setDueDate(long dueDate) {
		this.dueDate = dueDate;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public List<String> getWatchers() {
		return watchers;
	}

	public void setWatchers(List<String> watchers) {
		this.watchers = watchers;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public long getUpdated() {
		return updated;
	}

	public void setUpdated(long updated) {
		this.updated = updated;
	}

	public List<String> getAllowedAccessUsers() {
		return allowedAccessUsers;
	}

	public void setAllowedAccessUsers(List<String> allowedAccessUsers) {
		if (allowedAccessUsers == null) {
			this.allowedAccessUsers = new ArrayList<String>();
		} else {
			this.allowedAccessUsers = allowedAccessUsers;
		}
	}

	public String getFormattedDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd yyyy");
		String formattedDate = sdf.format(date);

		return formattedDate;
	}

	public String getImage() {
		if (this instanceof Initiative) {
			return IMAGE_LOCATION_I;
		}

		else if (this instanceof Milestone) {
			return IMAGE_LOCATION_M;
		}

		else {
			return IMAGE_LOCATION_R;
		}
	}
}
