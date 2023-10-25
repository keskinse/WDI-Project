package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

public class MovieXMLFormatter extends XMLFormatter<Movie> {

	ActorXMLFormatter actorFormatter = new ActorXMLFormatter();

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("movies");
	}

	@Override
	public Element createElementFromRecord(Movie record, Document doc) {
		Element movie = doc.createElement("movie");

		movie.appendChild(createTextElement("id", record.getIdentifier(), doc));

		movie.appendChild(createTextElement("title",
				record.getTitle(),
				doc));
		movie.appendChild(createTextElement("director",
				record.getDirector(),
				doc));
		movie.appendChild(createTextElement("date", record
				.getDate().toString(), doc));

		movie.appendChild(createActorsElement(record, doc));

		return movie;
	}

	protected Element createTextElementWithProvenance(String name,
			String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}

	protected Element createActorsElement(Movie record, Document doc) {
		Element actorRoot = actorFormatter.createRootElement(doc);

		for (Actor a : record.getActors()) {
			actorRoot.appendChild(actorFormatter
					.createElementFromRecord(a, doc));
		}

		return actorRoot;
	}

}
