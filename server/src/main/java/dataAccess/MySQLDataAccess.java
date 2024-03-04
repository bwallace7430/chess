package dataAccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class MySQLDataAccess implements DataAccess {
    public UserData getUser(String username){
        return null;
    }

    public void createUser(String username, String password, String email) throws DataAccessException{
    }

    public AuthData generateAuthToken(String username){
        return null;
    }

    public AuthData getAuthDataByUsername(String username){
        return null;
    }

    public AuthData getAuthDataByAuthToken(String authToken){
        return null;
    }

    public void removeAuthData(String authToken){
    }

    public GameData createGame(String gameName){
        return null;
    }

    public GameData getGameByID(int gameID){
        return null;
    }

    public GameData addPlayerToGame(GameData game, String username, ChessGame.TeamColor playerColor) throws DataAccessException{
        return null;
    }

    public GameData addObserverToGame(GameData game, String username){
        return null;
    }

    public Collection<GameData> getAllGames(){
        return null;
    }
    public void deleteGames(){

    }
    public void deleteUsers(){

    }
    public void deleteAuths(){

    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS Game(
              `gameID` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(300),
              `blackUsername` varchar(300),
              `gameName` varchar(300) NOT NULL,
              `game` text NOT NULL,
              PRIMARY KEY (`gameID`),
              INDEX(`gameName`)
              )
              DEFAULT CHARSET=utf8mb4
              """,
            """
              CREATE TABLE IF NOT EXISTS User(
              `userID` int NOT NULL AUTO_INCREMENT,
              `username` varchar(300),
              `email` varchar(400),
              `password` varchar(700)
              PRIMARY KEY (`userID`),
              INDEX(`username`)
              )
              DEFAULT CHARSET=utf8mb4
              """,
            """
              CREATE TABLE IF NOT EXISTS Auth(
              `authID` int NOT NULL AUTO_INCREMENT,
              `username` varchar(300),
              `authToken` varchar(700),
              PRIMARY KEY (`authID`),
              INDEX(`username`)
              INDEX(`authToken`)
              )
              DEFAULT CHARSET=utf8mb4
            """
    };
    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
}