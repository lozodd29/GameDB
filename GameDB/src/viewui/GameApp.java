package viewui;

import java.util.Scanner;

import model.Game;
import model.IGameDAO;
import model.datastore.mysql.GameDAO;

/**
 * @author Dylan Lozo
 * @version 20151023
 * 
 */
public class GameApp {

	IGameDAO gameList = new GameDAO();
	Scanner sc = new Scanner(System.in);

	public GameApp() {
		menuLoop();
	}

	private void menuLoop() {
		int starRating;
		String gameName, genre, description;
		double pricePaid;
		String choice = "1";
		while (!choice.equals("0")) {
			System.out.println("\nGame Collection App");
			System.out.println("0 = Quit");
			System.out.println("1 = List All Records");
			System.out.println("2 = Create New Record");
			System.out.println("3 = Retrieve Record");
			System.out.println("4 = Update Record");
			System.out.println("5 = Delete Record");
			choice = Validator.getLine(sc, "Number of choice: ", "^[0-5]$");

			switch (choice) {
			case "1":
				System.out.println(gameList.toString());
				break;
			case "2":
				gameName = Validator.getLine(sc, "Game Name: ");
				genre = Validator.getLine(sc, "Genre: ");
				description = Validator.getLine(sc, "Description: ");
				pricePaid = Validator.getDouble(sc, "Price You Paid: ");
				starRating = Validator.getInt(sc, "Your Personal Rating (out of 5 stars): ");
				gameList.createRecord(new Game(gameName, genre, description, pricePaid, starRating));
				break;
			case "3":
				gameName = Validator.getLine(sc, "Game to retrieve: ");
				System.out.println(gameList.retrieveRecordByName(gameName));
				break;
			case "4":
				gameName = Validator.getLine(sc, "Game Name: ");
				genre = Validator.getLine(sc, "Genre: ");
				description = Validator.getLine(sc, "Description: ");
				pricePaid = Validator.getDouble(sc, "Price You Paid: ");
				starRating = Validator.getInt(sc, "Your Personal Rating (out of 5 stars): ");
				gameList.updateRecord(new Game(gameName, genre, description, pricePaid, starRating));
				break;
			case "5":
				gameName = Validator.getLine(sc, "Game to delete: ");
				System.out.println(gameList.retrieveRecordByName(gameName));
				String ok = Validator.getLine(sc, "Delete this record? (y/n) ", "^[yYnN]$");
				if (ok.equalsIgnoreCase("Y")) {
					gameList.deleteRecord(gameName);
				}
				break;
			}
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		new GameApp();
	}
}
