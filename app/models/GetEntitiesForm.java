package models;

import java.util.Date;

import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.*;
public class GetEntitiesForm {

	public long id;
	
	@Required
	public Date date;
}
