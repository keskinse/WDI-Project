package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Actor}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class DirectorXMLFormatter extends XMLFormatter<Director> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("directors");
	}

	@Override
	public Element createElementFromRecord(Director director, Document doc) {
		Element director1 = doc.createElement("director");

		director1.appendChild(createTextElement("producer", director.getDirector(), doc));

		return director1;
	}

}
