package model;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class ProducerXMLReader extends XMLMatchableReader<Producer, Attribute> {

    @Override
    public Producer createModelFromElement(Node node, String provenanceInfo) {
    //    String id = getValueFromChildElement(node, "id");

        // create the object with id and provenance information
        Producer producer = new Producer("");

        // fill the attributes
        producer.setProducer(node.getTextContent());

        return producer;
    }

}