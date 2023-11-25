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
package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * A {@link AbstractRecord} representing a movie.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class Movie extends AbstractRecord<Attribute> implements Serializable {

    /*
     * example entry <movie> <id>academy_awards_2</id> <title>True Grit</title>
     * <director> <name>Joel Coen and Ethan Coen</name> </director> <actors> <actor>
     * <name>Jeff Bridges</name> </actor> <actor> <name>Hailee Steinfeld</name>
     * </actor> </actors> <date>2010-01-01</date> </movie>
     */

    private static final long serialVersionUID = 1L;

    public Movie(String identifier, String provenance) {
        super(identifier, provenance);
        actors = new LinkedList<>();
        producers = new LinkedList<>();
        directors = new LinkedList<>();
    }// producer, rating

    private String title;
    private LocalDateTime date;
    private List<Actor> actors;
    private List<Producer> producers;
    private List<Director> directors;
    private String studio;
    private String genre;
    private double budget;
    private double gross;
    private String summary;
    private double rating;
    private int year;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getGross() {
        return gross;
    }

    public void setGross(double gross) {
        this.gross = gross;
    }

    private Map<Attribute, Collection<String>> provenance = new HashMap<>();
    private Collection<String> recordProvenance;

    public void setRecordProvenance(Collection<String> provenance) {
        recordProvenance = provenance;
    }

    public Collection<String> getRecordProvenance() {
        return recordProvenance;
    }

    public void setAttributeProvenance(Attribute attribute, Collection<String> provenance) {
        this.provenance.put(attribute, provenance);
    }

    public Collection<String> getAttributeProvenance(String attribute) {
        return provenance.get(attribute);
    }

    public String getMergedAttributeProvenance(Attribute attribute) {
        Collection<String> prov = provenance.get(attribute);

        if (prov != null) {
            return StringUtils.join(prov, "+");
        } else {
            return "";
        }
    }

    public static final Attribute TITLE = new Attribute("Title");
    public static final Attribute DIRECTORS = new Attribute("Directors");
    public static final Attribute YEAR = new Attribute("Year");
    public static final Attribute ACTORS = new Attribute("Actors");
    public static final Attribute PRODUCERS = new Attribute("Producers");
    public static final Attribute RATING = new Attribute("Rating");

    @Override
    public boolean hasValue(Attribute attribute) {
        if (attribute == TITLE)
            return getTitle() != null && !getTitle().isEmpty();
        else if (attribute == DIRECTORS)
            return getDirectors() != null && getDirectors().size() > 0;
        else if (attribute == YEAR)
            return getYear() != 0;
        else if (attribute == ACTORS)
            return getActors() != null && getActors().size() > 0;
        else if (attribute == PRODUCERS)
            return getProducers() != null && getProducers().size() > 0;
        else if (attribute == RATING)
            return getRating() != 0.0;
        else
            return false;
    }

    // fix
    @Override
    public String toString() {
        System.out.println("hee");
        return String.format("[Movie %s: %s / %s / %s / %s / %s]", getIdentifier(), getTitle(), getDirectors(),
                getRating(), getProducers(), getDate().toString().charAt(year));
    }

    @Override
    public int hashCode() {
        return getIdentifier().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Movie) {
            return this.getIdentifier().equals(((Movie) obj).getIdentifier());
        } else
            return false;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
