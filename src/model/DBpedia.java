package model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;

public class DBpedia implements Matchable {
	protected String movie_id;
	protected String provenance;
	private String title;
	private List<Director> directors;
	private List<Producer> producers;
	private LocalDateTime date;
	private List<Actor> actors;
	private String summary;
	private double rating;
	private int year;

	public DBpedia(String identifier, String provenance) {
		movie_id = identifier;
		this.provenance = provenance;
		actors = new LinkedList<>();
		directors = new LinkedList<>();
		producers = new LinkedList<>();
	}

	@Override
	public String getIdentifier() {
	return movie_id;
	}

	@Override
	public String getProvenance() {
	return provenance;
	}

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
	
	public List<Producer> getProducers() {
	return producers;
	}

	public void setProducers(List<Producer> Producers) {
	this.producers = producers;
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

	@Override
	public String toString() {
	return String.format("[Movie %s: %s / %s / %s]", getIdentifier(), getTitle(),
	getDirectors(), getDate().toString());
	}

	@Override
	public int hashCode() {
	return getIdentifier().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
	if(obj instanceof DBpedia){
	   return this.getIdentifier().equals(((DBpedia) obj).getIdentifier());
	}else
	   return false;
	}

	public String getPoster_url() {
		// TODO Auto-generated method stub
		return null;
	}

}
