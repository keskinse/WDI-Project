/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Movie}s.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class MovieXMLFormatter extends XMLFormatter<Movie> {

    ActorXMLFormatter actorFormatter = new ActorXMLFormatter();
    DirectorXMLFormatter directorFormatter = new DirectorXMLFormatter();
    ProducerXMLFormatter producerFormatter = new ProducerXMLFormatter();

    @Override
    public Element createRootElement(Document doc) {
        return doc.createElement("movies");
    }

    @Override
    public Element createElementFromRecord(Movie record, Document doc) {
        Element movie = doc.createElement("movie");

        movie.appendChild(createTextElement("movie_id", record.getIdentifier(), doc));

        movie.appendChild(createTextElementWithProvenance("title", record.getTitle(),
                record.getMergedAttributeProvenance(Movie.TITLE), doc));
        movie.appendChild(createTextElementWithProvenance("date", record.getDate().toString(),
                record.getMergedAttributeProvenance(Movie.DATE), doc));
        movie.appendChild(createTextElementWithProvenance("rating", String.valueOf(record.getRating()),
                record.getMergedAttributeProvenance(Movie.RATING), doc));

        movie.appendChild(createActorsElement(record, doc));

        return movie;
    }

    protected Element createTextElementWithProvenance(String name, String value, String provenance, Document doc) {
        Element elem = createTextElement(name, value, doc);
        elem.setAttribute("provenance", provenance);
        return elem;
    }

    protected Element createActorsElement(Movie record, Document doc) {
        Element actorRoot = actorFormatter.createRootElement(doc);
        actorRoot.setAttribute("provenance", record.getMergedAttributeProvenance(Movie.ACTORS));

        for (Actor a : record.getActors()) {
            actorRoot.appendChild(actorFormatter.createElementFromRecord(a, doc));
        }

        return actorRoot;
    }

    protected Element createDirectorElement(Movie record, Document doc) {
        Element directorRoot = directorFormatter.createRootElement(doc);
        directorRoot.setAttribute("provenance", record.getMergedAttributeProvenance(Movie.DIRECTORS));

        for (Director d : record.getDirectors()) {
            directorRoot.appendChild(directorFormatter.createElementFromRecord(d, doc));
        }

        return directorRoot;
    }

    protected Element createProducerElement(Movie record, Document doc) {
        Element producerRoot = producerFormatter.createRootElement(doc);
        producerRoot.setAttribute("provenance", record.getMergedAttributeProvenance(Movie.PRODUCERS));

        for (Producer d : record.getProducers()) {
            producerRoot.appendChild(producerFormatter.createElementFromRecord(d, doc));
        }

        return producerRoot;
    }

}
