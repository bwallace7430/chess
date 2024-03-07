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

//    UserData getUser(String username) throws DataAccessException;
//    AuthData generateAuthToken(String username) throws DataAccessException;
//    AuthData getAuthDataByUsername(String username) throws DataAccessException;
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