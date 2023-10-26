package model;

import java.util.List;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class MovieXMLReader extends XMLMatchableReader<Movie, Attribute> {

	@Override
	protected void initialiseDataset(DataSet<Movie, Attribute> dataset) {
		super.initialiseDataset(dataset);

	}

	@Override
	public Movie createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "movie_id");

// create the object with id and provenance information
		Movie movie = new Movie(id, provenanceInfo);

// fill the attributes
		movie.setTitle(getValueFromChildElement(node, "title"));

		// movie.setDirectors(getValueFromChildElement(node, "directors"));

// convert the date string into a DateTime object
		/*
		 * try { String date = getValueFromChildElement(node, "date"); if (date != null
		 * && !date.isEmpty()) { DateTimeFormatter formatter = new
		 * DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd")
		 * .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
		 * .parseDefaulting(ChronoField.MINUTE_OF_HOUR,
		 * 0).parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
		 * .toFormatter(Locale.ENGLISH); LocalDateTime dt = LocalDateTime.parse(date,
		 * formatter); movie.setDate(dt); } } catch (Exception e) { e.printStackTrace();
		 * }
		 */

// load the list of actors
		/*
		 * List<Actor> actors = getObjectListFromChildElement(node, "actors", "actor",
		 * new ActorXMLReader(), provenanceInfo); movie.setActors(actors);
		 */
		List<Director> directors = getObjectListFromChildElement(node, "directors", "director", new DirectorXMLReader(),
				provenanceInfo);
		movie.setDirectors(directors);
		
		List<Actor> actors = getObjectListFromChildElement(node, "actors", "actor", new ActorXMLReader(),
				provenanceInfo);
		movie.setActors(actors);
		
		List<Producer> producers = getObjectListFromChildElement(node, "producers", "producer", new ProducerXMLReader(),
				provenanceInfo);
		movie.setProducers(producers);

		return movie;
	}

}
