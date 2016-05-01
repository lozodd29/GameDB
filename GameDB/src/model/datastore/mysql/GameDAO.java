package model.datastore.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Game;
import model.IGameDAO;

/**
 * @author Dylan Lozo
 * @version 20151015
 *
 */
public class GameDAO implements IGameDAO {
	
	protected final static boolean DEBUG = true;

	@Override
	public void createRecord(Game game) {
		final String QUERY = "insert into game (gameName, genre, description, pricePaid, starRating) VALUES (?, ?, ?, ?, ?)";

		try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(QUERY);) {
			stmt.setString(1, game.getGameName());
			stmt.setString(2, game.getGenre());
			stmt.setString(3, game.getDescription());
			stmt.setDouble(4, game.getPricePaid());
			stmt.setInt(5, game.getStarRating());
			if(DEBUG) System.out.println(stmt.toString());
			stmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println("createRecord SQLException: " + ex.getMessage());
		}
	}

	@Override
	public Game retrieveRecordByName(String gameName) {
		final String QUERY = "select gameName, genre, description, pricePaid, starRating from game where gameName = '" + gameName + "'";
		// final String QUERY = "select gameName, genre, description, pricePaid, 
		//starRating from game where gameName = " + gameName;
		Game game = null;

		try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(QUERY)) {
			// stmt.setInt(1, id);
			//stmt.setString(1, gameName);
			if(DEBUG) System.out.println(stmt.toString());
			ResultSet rs = stmt.executeQuery(QUERY);

			if (rs.next()) {
				game = new Game(rs.getString("gameName"), rs.getString("genre"),
						rs.getString("description"), rs.getDouble("pricePaid"), rs.getInt("starRating"));
			}
		} catch (SQLException ex) {
			System.out.println("retrieveRecordByName SQLException: " + ex.getMessage());
		}

		return game;
	}

	@Override
	public List<Game> retrieveAllRecords() {
		final List<Game> myList = new ArrayList<>();
		final String QUERY = "select gameName, genre, description, pricePaid, starRating from game";

		try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(QUERY)) {
			if(DEBUG) System.out.println(stmt.toString());
			ResultSet rs = stmt.executeQuery(QUERY);
			
			while (rs.next()) {
				myList.add(new Game(rs.getString("gameName"), rs.getString("genre"),
						rs.getString("description"), rs.getDouble("pricePaid"), rs.getInt("starRating")));
			}
		} catch (SQLException ex) {
			System.out.println("retrieveAllRecords SQLException: " + ex.getMessage());
		}

		return myList;
	}

	@Override
	public void updateRecord(Game updatedGame) {
		final String QUERY = "update game set gameName=?, genre=?, description=?, pricePaid=?, starRating=? where gameName='" + updatedGame.getGameName() + "'";

		try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(QUERY)) {
			stmt.setString(1, updatedGame.getGameName());
			stmt.setString(2, updatedGame.getGenre());
			stmt.setString(3, updatedGame.getDescription());
			stmt.setDouble(4, updatedGame.getPricePaid());
			stmt.setInt(5, updatedGame.getStarRating());
			if(DEBUG) System.out.println(stmt.toString());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println("updateRecord SQLException: " + ex.getMessage());
		}
	}

	@Override
	public void deleteRecord(String gameName) {
		final String QUERY = "delete from game where gameName = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(QUERY)) {
			stmt.setString(1, gameName);
			if(DEBUG) System.out.println(stmt.toString());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println("deleteRecord SQLException: " + ex.getMessage());
		}
	}

	@Override
	public void deleteRecord(Game game) {
		final String QUERY = "delete from employee where empId = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement stmt = con.prepareStatement(QUERY)) {
			stmt.setString(1, game.getGameName());
			if(DEBUG) System.out.println(stmt.toString());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			System.out.println("deleteRecord SQLException: " + ex.getMessage());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Game employee : retrieveAllRecords()) {
			sb.append(employee.toString() + "\n");
		}

		return sb.toString();
	}
}
