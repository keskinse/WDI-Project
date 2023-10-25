package model;

import java.util.List;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class DBpediaXMLReader extends XMLMatchableReader<DBpedia, Attribute> {

	@Override
	protected void initialiseDataset(DataSet<DBpedia, Attribute> dataset) {
		super.initialiseDataset(dataset);

	}

	@Override
	public DBpedia createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "movie_id");

// create the object with id and provenance information
		DBpedia movie = new DBpedia(id, provenanceInfo);

// fill the attributes
		movie.setTitle(getValueFromChildElement(node, "title"));

		// movie.setDirectors(getValueFromChildElement(node, "directors"));


// load the list of actors, directors, producers
		
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
