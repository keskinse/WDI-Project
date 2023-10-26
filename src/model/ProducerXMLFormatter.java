package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Actor}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class ProducerXMLFormatter extends XMLFormatter<Producer> {

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("directors");
	}

	@Override
	public Element createElementFromRecord(Producer producer, Document doc) {
		Element producer1 = doc.createElement("producer");

		producer1.appendChild(createTextElement("producer", producer.getProducer(), doc));

		return producer1;
	}

}
