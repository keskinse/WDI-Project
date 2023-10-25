package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Movie}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class DBpediaXMLFormatter extends XMLFormatter<Movie> {
	// 'title', 'poster_url', 'year', 'producers', 'summary', 'actors', 'movie_id',
	// 'rating', 'directors'

	ActorXMLFormatter actorFormatter = new ActorXMLFormatter();
	ProducerXMLFormatter producerFormatter = new ProducerXMLFormatter();
	DirectorXMLFormatter directorFormatter = new DirectorXMLFormatter();

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("movies");
	}

	@Override
	public Element createElementFromRecord(Movie record, Document doc) {
		Element movie = doc.createElement("movie");

		movie.appendChild(createTextElement("movie_id", record.getIdentifier(), doc));
		movie.appendChild(createTextElement("title", record.getTitle(), doc));
		movie.appendChild(createTextElement("poster_url", record.getPoster_url(), doc));
		movie.appendChild(createTextElement("year", Integer.toString(record.getYear()), doc));
		movie.appendChild(createTextElement("summary", record.getSummary(), doc));
		movie.appendChild(createTextElement("rating", Double.toString(record.getRating()), doc));
		movie.appendChild(createTextElement("date", record.getDate().toString(), doc));
		movie.appendChild(createActorsElement(record, doc));

		return movie;
	}

	protected Element createTextElementWithProvenance(String name, String value, String provenance, Document doc) { // to
																													// be
																													// checked
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}

	protected Element createProducersElement(Movie record, Document doc) {
		Element producerRoot = producerFormatter.createRootElement(doc);

		for (Producer a : record.getProducers()) {
			producerRoot.appendChild(producerFormatter.createElementFromRecord(a, doc));
		}

		return producerRoot;
	}

	protected Element createActorsElement(Movie record, Document doc) {
		Element actorRoot = actorFormatter.createRootElement(doc);

		for (Actor a : record.getActors()) {
			actorRoot.appendChild(actorFormatter.createElementFromRecord(a, doc));
		}

		return actorRoot;
	}

	protected Element createDirectorsElement(Movie record, Document doc) {
		Element directorRoot = directorFormatter.createRootElement(doc);

		for (Director a : record.getDirectors()) {
			directorRoot.appendChild(directorFormatter.createElementFromRecord(a, doc));
		}

		return directorRoot;
	}

}
