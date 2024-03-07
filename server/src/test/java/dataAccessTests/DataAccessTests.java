package dataAccessTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.MySQLDataAccess;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class DataAccessTests {
    MySQLDataAccess data = new MySQLDataAccess();
    String username = "abe_the_babe";
    String password = "lincolnR0ck$";
    String email = "alincoln@usa.com";
    DataAccessTests() throws DataAccessException {
    }

    @BeforeEach
    void clearData() throws DataAccessException {
        data.deleteAuths();
        data.deleteGames();
        data.deleteUsers();
    }

    @Test
    void validCreateUser() throws ResponseException, DataAccessException{
        data.createUser(username, password, email);
        var retrievedUser = data.getUser(username);
        assertEquals(username, retrievedUser.username());
        assertEquals(email, retrievedUser.email());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches(password, retrievedUser.password()));
    }

    @Test
    void invalidCreateUser(){
        assertThrows(ResponseException.class, ()->data.createUser(null, password, email));
        assertThrows(ResponseException.class, ()->data.createUser(username, null, email));
        assertThrows(ResponseException.class, ()->data.createUser(username, password, null));
    }

    @Test
    void validGetUser() throws ResponseException, DataAccessException {
        data.createUser(username, password, email);
        data.createUser("username2", "password2", "email2");
        var retrievedUser = data.getUser(username);

        assertEquals(username, retrievedUser.username());
        assertEquals(email, retrievedUser.email());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches(password, retrievedUser.password()));
    }

    @Test
    void invalidGetUser() throws ResponseException, DataAccessException {
        data.createUser(username, password, email);
        assertNull(data.getUser("username2"));
    }

    @Test
    void validGenerateAuthToken() throws DataAccessException, ResponseException {
        data.createUser(username, password, email);
        var authData = data.generateAuthToken(username);
        assertEquals(authData.username(), username);
        assertEquals(authData.authToken(), data.getAuthDataByUsername(username).authToken());
    }

    @Test
    void invalidGenerateAuthToken() throws ResponseException, DataAccessException {
        data.createUser(username, password, email);
        assertThrows(DataAccessException.class, ()->data.generateAuthToken(null));
    }

    @Test
    void validGetAuthDataByUsername() throws ResponseException, DataAccessException {
        data.createUser(username, password, email);
        var authData = data.generateAuthToken(username);
        var testData = data.getAuthDataByUsername(username);
        assertEquals(authData.authToken(), testData.authToken());
        assertEquals(testData, data.getAuthDataByAuthToken(authData.authToken()));
    }

    @Test
    void invalidGetAuthDataByUsername() throws ResponseException, DataAccessException {
        data.createUser(username, password, email);
        data.generateAuthToken(username);
        assertNull(data.getAuthDataByUsername("username2"));
    }

    @Test
    void validGetAuthDataByAuthToken() throws ResponseException, DataAccessException {
        data.createUser(username, password, email);
        var authData = data.generateAuthToken(username);
        var token = authData.authToken();
        var testData = data.getAuthDataByAuthToken(token);
        assertEquals(token, testData.authToken());
        assertEquals(testData, data.getAuthDataByUsername(username));
    }

    @Test
    void invalidGetAuthDataByAuthToken() throws ResponseException, DataAccessException {
        data.createUser(username, password, email);
        data.generateAuthToken(username);
        assertNull(data.getAuthDataByAuthToken("fakeAuth23"));
    }

//    AuthData getAuthDataByAuthToken(String authToken) throws DataAccessException;
//    void removeAuthData(String authToken) throws DataAccessException;
//    GameData createGame(String gameName) throws DataAccessException;
//    GameData getGameByID(int gameID) throws DataAccessException;
//    GameData addPlayerToGame(GameData game, String username, ChessGame.TeamColor playerColor) throws DataAccessException, ResponseException;
//    GameData addObserverToGame(GameData game, String username);
//    Collection<GameData> getAllGames() throws ResponseException;
//    void deleteGames() throws DataAccessException;
//    void deleteUsers() throws DataAccessException;
//    void deleteAuths() throws DataAccessException;
}