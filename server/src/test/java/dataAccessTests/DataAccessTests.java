package dataAccessTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import dataAccess.MySQLDataAccess;
import exception.ResponseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.AdminService;
import service.GameService;
import service.RegistrationService;
import service.SessionService;

import static org.junit.jupiter.api.Assertions.*;

class DataAccessTests {
    //MemoryDataAccess data = new MemoryDataAccess();
    MySQLDataAccess data = new MySQLDataAccess();
    RegistrationService registrationService = new RegistrationService(data);
    AdminService adminService = new AdminService(data);
    SessionService sessionService = new SessionService(data);
    GameService gameService = new GameService(data);

    DataAccessTests() throws DataAccessException {
    }

    @BeforeEach
    void clearData() throws ResponseException {
        adminService.clearDatabase();
    }

    @Test
    void deleteAll() throws ResponseException, DataAccessException {
        registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        adminService.clearDatabase();
        assertNull(data.getUser("abe_the_babe"));
    }
    @Test
    void validRegister() throws ResponseException {
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");

        assertNotSame("", userAuth.authToken());
        assertNotNull(userAuth.authToken());
    }

    @Test
    void invalidRegister() throws ResponseException {
        registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");

        assertThrows(ResponseException.class, () -> {
            registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        });
    }
    @Test
    void validLogin() throws ResponseException, DataAccessException {
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        sessionService.endSession(userAuth.authToken());
        var newAuth = sessionService.createSession("abe_the_babe", "lincolnR0ck$");

        assertEquals(data.getAuthDataByUsername("abe_the_babe"), newAuth);
    }

    @Test
    void invalidLogin() throws ResponseException {
        registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");

        assertThrows(ResponseException.class, ()-> sessionService.createSession("honest_abe", "lincolnR0ck$"));
        assertThrows(ResponseException.class, ()->sessionService.createSession("abe_the_babe", "l!nc0lnR0ck$"));
    }

    @Test
    void validLogout() throws ResponseException, DataAccessException {
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        sessionService.endSession(userAuth.authToken());

        assertNull(data.getAuthDataByUsername("abe_the_babe"));
        assertNull(data.getAuthDataByAuthToken(userAuth.authToken()));
    }

    @Test
    void invalidLogout() throws ResponseException {
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        sessionService.endSession(userAuth.authToken());

        assertThrows(ResponseException.class, ()-> sessionService.endSession(userAuth.authToken()));
    }

    @Test
    void validCreateGame() throws ResponseException{
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        var testGame1 = gameService.createGame(userAuth.authToken(), "Abe and the Baberahams");
        var allGames = gameService.listGames(userAuth.authToken());
        assertEquals(allGames.iterator().next().gameID(), (testGame1).gameID());

        var testGame2 = gameService.createGame(userAuth.authToken(), "Abe and the Baberahams");
        assertNotEquals(testGame1, testGame2);
    }

    @Test
    void invalidCreateGame() throws ResponseException{
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");

        assertThrows(ResponseException.class, ()-> gameService.createGame(userAuth.authToken(), ""));
        assertThrows(ResponseException.class, ()-> gameService.createGame(userAuth.authToken(), null));

        sessionService.endSession(userAuth.authToken());
        assertThrows(ResponseException.class, ()-> gameService.createGame(userAuth.authToken(), "Abe and the Baberahams"));
    }

    @Test
    void validListGames() throws ResponseException{
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        var testGame1 = gameService.createGame(userAuth.authToken(), "Abe and the Baberahams");
        var testGame2 = gameService.createGame(userAuth.authToken(), "Four Score and Twenty Games Ago");
        assertNotEquals(testGame1, testGame2);

        var allGames = gameService.listGames(userAuth.authToken());
        assertTrue(allGames.iterator().next().gameID() == testGame1.gameID() || allGames.iterator().next().gameID() == testGame2.gameID());
    }

    @Test
    void invalidListGames() throws ResponseException{
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        sessionService.endSession(userAuth.authToken());
        assertThrows(ResponseException.class, ()-> gameService.listGames(userAuth.authToken()));
    }

    @Test
    void validJoinGame() throws ResponseException{
        var playerOneAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        var game = gameService.createGame(playerOneAuth.authToken(), "Abe and the Baberahams");
        game = gameService.joinGame(playerOneAuth.authToken(), game.gameID(), ChessGame.TeamColor.BLACK);
        assertEquals(game.blackUsername(), "abe_the_babe");

        var playerTwoAuth = registrationService.register("wilks_booth", "lincoln$uck$", "ilovetheaters@usa.com");
        game = gameService.joinGame(playerTwoAuth.authToken(), game.gameID(), ChessGame.TeamColor.WHITE);
        assertEquals(game.whiteUsername(), "wilks_booth");

        var observerAuth = registrationService.register("shocked_observer", "tr@um@tized4life", "ihatetheaters@usa.com");
        var newGame = gameService.joinGame(observerAuth.authToken(), game.gameID());
        assertEquals(game.gameID(), newGame.gameID());
    }

    @Test
    void invalidJoinGame() throws ResponseException{
        var playerOneAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        var game = gameService.createGame(playerOneAuth.authToken(), "Abe and the Baberahams");
        game = gameService.joinGame(playerOneAuth.authToken(), game.gameID(), ChessGame.TeamColor.BLACK);
        var gameID = game.gameID();

        var playerTwoAuth = registrationService.register("wilks_booth", "lincoln$uck$", "ilovetheaters@usa.com");
        assertThrows(ResponseException.class, ()->gameService.joinGame(playerTwoAuth.authToken(), gameID, ChessGame.TeamColor.BLACK));
        assertThrows(ResponseException.class, ()->gameService.joinGame(playerTwoAuth.authToken(), gameID+7, ChessGame.TeamColor.BLACK));
        gameService.joinGame(playerTwoAuth.authToken(), gameID, ChessGame.TeamColor.WHITE);

        var observerAuth = registrationService.register("shocked_observer", "tr@um@tized4life", "ihatetheaters@usa.com");
        assertThrows(ResponseException.class, ()->gameService.joinGame(observerAuth.authToken(), gameID, ChessGame.TeamColor.WHITE));
    }
}