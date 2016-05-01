package model;

import java.util.List;

/**
 * @author Dylan Lozo
 * @version 20151023
 *
 */
public interface IGameDAO {

	void createRecord(Game game);

	Game retrieveRecordByName(String gameName);

	List<Game> retrieveAllRecords();

	void updateRecord(Game updatedGame);

	void deleteRecord(String gameName);

	void deleteRecord(Game gameName);

	String toString();

}