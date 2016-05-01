package model;

/**
 * The Game class represents a single game.
 * 
 * @author Dylan Lozo
 * @version 20151023
 *
 */
public class Game {

	private String gameName;
	private String genre;
	private String description;
	private double pricePaid;
	private int starRating;

	public Game() {
		gameName = "";
		genre = "";
		description = "";
		pricePaid = 0;
		starRating = 0;
	}

	public Game(String gameName, String genre, String description, double pricePaid, int starRating) {
		this.gameName = gameName;
		this.genre = genre;
		this.description = description;
		this.pricePaid = pricePaid;
		this.starRating = starRating;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPricePaid() {
		return pricePaid;
	}

	public void setPricePaid(double pricePaid) {
		this.pricePaid = pricePaid;
	}
	
	public int getStarRating() {
		return starRating;
	}

	public void setStarRating(int starRating) {
		this.starRating = starRating;
	}
	
	@Override
	public String toString() {
		return String.format("%s, %s, %s, %.2f, %d", this.getGameName(),
				this.getGenre(), this.getDescription(), this.getPricePaid(), this.getStarRating());
	}
}