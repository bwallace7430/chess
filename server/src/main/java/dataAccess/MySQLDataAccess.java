package dataAccess;

import chess.ChessGame;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.springframework.security.core.userdetails.User;

import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;
import java.util.Collection;
import java.util.UUID;

public class MySQLDataAccess implements DataAccess {
    public MySQLDataAccess() throws DataAccessException{
        configureDatabase();
    }
    public UserData getUser(String username) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, email, password FROM User WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUser(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    public UserData readUser(ResultSet rs) throws SQLException{
        var username = rs.getString("username");
        var email = rs.getString("email");
        var password = rs.getString("password");
        return new UserData(username, password, email);
    }

    public AuthData readAuth(ResultSet rs) throws SQLException{
        var username = rs.getString("username");
        var authToken = rs.getString("authToken");
        return new AuthData(username, authToken);
    }

    public void createUser(String username, String password, String email) throws ResponseException, DataAccessException {
        if(username == null || username.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty()){
            throw new ResponseException(400, "Error: bad request");
        }
        var statement = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
        runSQL(statement, username, password, email);
    }

    public AuthData generateAuthToken(String username) throws DataAccessException{
        var authToken = UUID.randomUUID().toString();
        var statement = "INSERT INTO Auth (username, authToken) VALUES (?, ?)";
        runSQL(statement, username, authToken);

        return new AuthData(username, authToken);
    }

    public AuthData getAuthDataByUsername(String username) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, authToken FROM Auth WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    public AuthData getAuthDataByAuthToken(String authToken) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, authToken FROM Auth WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
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

    private int runSQL(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    switch (param) {
                        case String p -> ps.setString(i + 1, p);
                        case Integer p -> ps.setInt(i + 1, p);
                        case null -> ps.setNull(i + 1, NULL);
                        default -> {
                        }
                    }
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