package model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;

public class Movie implements Matchable {


	protected String movie_id;
	protected String provenance;
	private String title;
	private String director;
	private String poster_url;
	private int year;
	private LocalDateTime date;
	private List<Actor> actors;
	private List<Producer> producers;
	private List<Director> directors;
	private String summary;
	private double rating;
	private String studio;
	private String genre;
	private double budget;
	private double gross;
	// 'title', 'poster_url', 'year', 'producers', 'summary', 'actors', 'movie_id',
	// 'rating', 'directors'

	public Movie(String identifier, String provenance) {
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

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
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

	@Override
	public String toString() {
		return String.format("[Movie %s: %s / %s / %s]", getIdentifier(), getTitle(), getDirector(),
				getDate().toString());
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public void setProducers(List<Producer> producers) {
		this.producers = producers;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getPoster_url() {
		return poster_url;
	}

	public void setPoster_url(String poster_url) {
		this.poster_url = poster_url;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

}
