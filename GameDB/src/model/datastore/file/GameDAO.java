package model.datastore.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.Game;
import model.IGameDAO;

/**
 * @author Dylan Lozo
 * @version 20151023
 * 
 */
public class GameDAO implements IGameDAO {

	protected String fileName = null;
	protected final List<Game> myList;

	public GameDAO() {
		Properties props = new Properties();
		FileInputStream fis = null;

		// read the properties file
		try {
			fis = new FileInputStream("res/file/db.properties");
			props.load(fis);
			this.fileName = props.getProperty("DB_FILENAME");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.myList = new ArrayList<>();
		try {
			Files.createFile(Paths.get(fileName));
		} catch (FileAlreadyExistsException fae) {
			;
		} catch (IOException ioe) {
			System.out.println("Create file error with " + ioe.getMessage());
		}
		readList();
	}

	@Override
	public void createRecord(Game game) {
		myList.add(game);
		writeList();
	}

	@Override
	public Game retrieveRecordByName(String gameName) {
		for (Game game : myList) {
			if (game.getGameName().equalsIgnoreCase(gameName)) {
				return game;
			}
		}
		return null;
	}

	@Override
	public List<Game> retrieveAllRecords() {
		return myList;
	}

	@Override
	public void updateRecord(Game updatedGame) {
		for (Game game : myList) {
			if (game.getGameName() == updatedGame.getGameName()) {
				game.setGenre(updatedGame.getGenre());
				game.setDescription(updatedGame.getDescription());
				game.setPricePaid(updatedGame.getPricePaid());
				game.setStarRating(updatedGame.getStarRating());
				break;
			}
		}
		writeList();
	}

	@Override
	public void deleteRecord(String gameName) {
		for (Game game : myList) {
			if (game.getGameName() == gameName) {
				myList.remove(game);
				break;
			}
		}
		writeList();
	}

	@Override
	public void deleteRecord(Game game) {
		myList.remove(game);
		writeList();
	}

	protected void readList() {
		Path path = Paths.get(fileName);
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				String gameName = data[0];
				String genre = data[1];
				String description = data[2];
				double pricePaid = Double.parseDouble(data[3]);
				int starRating = Integer.parseInt(data[4]);
				Game game = new Game(gameName, genre, description, pricePaid, starRating);
				myList.add(game);
			}
		} catch (IOException ioe) {
			System.out.println("Read file error with " + ioe.getMessage());
		}
	}

	protected void writeList() {
		Path path = Paths.get(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			for (Game game : myList) {
				writer.write(String.format("%s,%s,%s,%.2f,%d\n", game.getGameName(), game.getGenre(),
						game.getDescription(), game.getPricePaid(), game.getStarRating()));
			}
		} catch (IOException ioe) {
			System.out.println("Write file error with " + ioe.getMessage());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Game game : myList) {
			sb.append(game.toString() + "\n");
		}

		return sb.toString();
	}

}
