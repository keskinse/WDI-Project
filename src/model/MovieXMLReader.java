package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

/**
 * A {@link XMLMatchableReader} for {@link Movie}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class MovieXMLReader extends XMLMatchableReader<Movie, Attribute> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uni_mannheim.informatik.wdi.model.io.XMLMatchableReader#initialiseataset(
	 * de.uni_mannheim.informatik.wdi.model.DataSet)
	 */
	@Override
	protected void initialiseDataset(DataSet<Movie, Attribute> dataset) {
		super.initialiseDataset(dataset);

	}

	@Override
	public Movie createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "id");

		// create the object with id and provenance information
		Movie movie = new Movie(id, provenanceInfo);

		// fill the attributes
		movie.setTitle(getValueFromChildElement(node, "title"));
		movie.setDirector(getValueFromChildElement(node, "director"));

		// convert the date string into a DateTime object
		try {
			String date = getValueFromChildElement(node, "date");
			if (date != null && !date.isEmpty()) {
				DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd")
						.parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
						.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0).parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
						.toFormatter(Locale.ENGLISH);
				LocalDateTime dt = LocalDateTime.parse(date, formatter);
				movie.setDate(dt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// load the list of actors
		List<Actor> actors = getObjectListFromChildElement(node, "actors", "actor", new ActorXMLReader(),
				provenanceInfo);
		movie.setActors(actors);

		return movie;
	}

}
