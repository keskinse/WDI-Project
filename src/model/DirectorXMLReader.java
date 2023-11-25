package model;

import org.w3c.dom.Node;

import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

public class DirectorXMLReader extends XMLMatchableReader<Director, Attribute> {

    @Override
    public Director createModelFromElement(Node node, String provenanceInfo) {
        String id = getValueFromChildElement(node, "id");

        // create the object with id and provenance information
        Director director = new Director(id);

        // fill the attributes
        director.setDirector(getValueFromChildElement(node, "director"));

        return director;
    }

}