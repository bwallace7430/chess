package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

import java.util.ArrayList;
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
        var hashedPassword = rs.getString("password");
        return new UserData(username, hashedPassword, email);
    }

    public AuthData readAuth(ResultSet rs) throws SQLException{
        var username = rs.getString("username");
        var authToken = rs.getString("authToken");
        return new AuthData(username, authToken);
    }

    public GameData readGame(ResultSet rs) throws SQLException{
        var gameID = rs.getInt("gameID");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var gameName = rs.getString("gameName");
        var gameJson = rs.getString("game");
        var game = new Gson().fromJson(gameJson, ChessGame.class);
        return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
    }

    public void createUser(String username, String password, String email) throws ResponseException, DataAccessException {
        if(username == null || username.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty()){
            throw new ResponseException(400, "Error: bad request");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        var statement = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
        runSQL(statement, username, hashedPassword, email);
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

    public void removeAuthData(String authToken) throws DataAccessException{
        var statement = "DELETE FROM Auth WHERE authToken=?";
        runSQL(statement, authToken);
    }

    public GameData createGame(String gameName) throws DataAccessException{
        var statement = "INSERT INTO Game (gameName, game) VALUES (?, ?)";
        var game = new ChessGame();
        var gameJson = new Gson().toJson(game);
        var  gameID = runSQL(statement, gameName, gameJson);
        return new GameData(gameID, null, null, gameName, game);
    }

    public GameData getGameByID(int gameID) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM Game WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    public GameData addPlayerToGame(GameData game, String username, ChessGame.TeamColor playerColor) throws DataAccessException, ResponseException{
        switch (playerColor){
            case WHITE -> {
                if (game.whiteUsername() != null){
                    throw new ResponseException(403, "Error: already taken");
                }
                else{
                    var statement = "UPDATE Game SET whiteUsername = ? WHERE gameID = ?";
                    runSQL(statement, username, game.gameID());
                    return getGameByID(game.gameID());
                }
            }
            case BLACK -> {
                if (game.blackUsername() != null){
                    throw new ResponseException(403, "Error: already taken");
                }
                else{
                    var statement = "UPDATE Game SET blackUsername = ? WHERE gameID = ?";
                    runSQL(statement, username, game.gameID());
                    return getGameByID(game.gameID());
                }
            }
        }
        return null;
    }

    public GameData addObserverToGame(GameData game, String username){
        return game;
    }

    public Collection<GameData> getAllGames() throws ResponseException{
        var result = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM Game";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, "Error: " + e.getMessage());
        }
        return result;
    }
    public void deleteGames() throws DataAccessException{
        var statement = "TRUNCATE Game";
        runSQL(statement);
    }
    public void deleteUsers() throws DataAccessException{
        var statement = "TRUNCATE User";
        runSQL(statement);
    }
    public void deleteAuths() throws DataAccessException{
        var statement = "TRUNCATE Auth";
        runSQL(statement);
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
              INDEX(gameName)
              )
              """,
            """
              CREATE TABLE IF NOT EXISTS User(
              `userID` int NOT NULL AUTO_INCREMENT,
              `username` varchar(300),
              `email` varchar(400),
              `password` varchar(700),
              PRIMARY KEY (`userID`),
              INDEX(`username`)
              )
              """,
            """
              CREATE TABLE IF NOT EXISTS Auth(
              `authID` int NOT NULL AUTO_INCREMENT,
              `username` varchar(300),
              `authToken` varchar(700),
              PRIMARY KEY (`authID`),
              INDEX(`username`),
              INDEX(`authToken`)
              )
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