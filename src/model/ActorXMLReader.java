package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class ActorXMLReader extends XMLMatchableReader<Actor, Attribute> {

	@Override
	public Actor createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");

		// create the object with id and provenance information
		Actor actor = new Actor(id);

		// fill the attributes
		actor.setActor(getValueFromChildElement(node, "actor"));

		return actor;
	}

}
