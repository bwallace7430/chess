package serviceTests;

import chess.ChessGame;
import dataAccess.MemoryDataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.RegistrationService;
import service.AdminService;
import service.SessionService;
import service.GameService;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTests {
    MemoryDataAccess data = new MemoryDataAccess();
    RegistrationService registrationService = new RegistrationService(data);
    AdminService adminService = new AdminService(data);
    SessionService sessionService = new SessionService(data);
    GameService gameService = new GameService(data);

    @BeforeEach
    void clearData() {
        adminService.clearDatabase();
    }

    @Test
    void deleteAll() throws DataAccessException {
        registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        adminService.clearDatabase();
        assertNull(data.getUser("abe_the_babe"));
    }
    @Test
    void validRegister() throws DataAccessException {
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");

        assertNotSame("", userAuth.authToken());
        assertNotNull(userAuth.authToken());
    }

    @Test
    void invalidRegister() throws DataAccessException {
        registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");

        assertThrows(DataAccessException.class, () -> {
            registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        });
    }
    @Test
    void validLogin() throws DataAccessException {
        registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        var newAuth = sessionService.createSession("abe_the_babe", "lincolnR0ck$");

        assertSame(data.getAuthDataByUsername("abe_the_babe"), newAuth);
    }

    @Test
    void invalidLogin() throws DataAccessException {
        registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");

        assertThrows(DataAccessException.class, ()-> sessionService.createSession("honest_abe", "lincolnR0ck$"));
        assertThrows(DataAccessException.class, ()->sessionService.createSession("abe_the_babe", "l!nc0lnR0ck$"));
    }

    @Test
    void validLogout() throws DataAccessException{
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        sessionService.endSession(userAuth.authToken());

        assertNull(data.getAuthDataByUsername("abe_the_babe"));
        assertNull(data.getAuthDataByAuthToken(userAuth.authToken()));
    }

    @Test
    void invalidLogout() throws DataAccessException{
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        sessionService.endSession(userAuth.authToken());

        assertThrows(DataAccessException.class, ()-> sessionService.endSession(userAuth.authToken()));
    }

    @Test
    void validCreateGame() throws DataAccessException{
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        var testGame1 = gameService.createGame(userAuth.authToken(), "Abe and the Baberahams");
        var allGames = gameService.listGames(userAuth.authToken());
        assertTrue(allGames.contains(testGame1));

        var testGame2 = gameService.createGame(userAuth.authToken(), "Abe and the Baberahams");
        assertNotEquals(testGame1, testGame2);
    }

    @Test
    void invalidCreateGame() throws DataAccessException{
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");

        assertThrows(DataAccessException.class, ()-> gameService.createGame(userAuth.authToken(), ""));
        assertThrows(DataAccessException.class, ()-> gameService.createGame(userAuth.authToken(), null));

        sessionService.endSession(userAuth.authToken());
        assertThrows(DataAccessException.class, ()-> gameService.createGame(userAuth.authToken(), "Abe and the Baberahams"));
    }

    @Test
    void validListGames() throws DataAccessException{
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        var testGame1 = gameService.createGame(userAuth.authToken(), "Abe and the Baberahams");
        var testGame2 = gameService.createGame(userAuth.authToken(), "Four Score and Twenty Games Ago");
        assertNotEquals(testGame1, testGame2);

        var allGames = gameService.listGames(userAuth.authToken());
        assertTrue(allGames.contains(testGame1));
        assertTrue(allGames.contains(testGame2));
    }

    @Test
    void invalidListGames() throws DataAccessException{
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        sessionService.endSession(userAuth.authToken());
        assertThrows(DataAccessException.class, ()-> gameService.listGames(userAuth.authToken()));
    }

    @Test
    void validJoinGame() throws DataAccessException{
        var playerOneAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        var game = gameService.createGame(playerOneAuth.authToken(), "Abe and the Baberahams");
        game = gameService.joinGame(playerOneAuth.authToken(), game.gameID(), ChessGame.TeamColor.BLACK);
        assertEquals(game.blackUsername(), "abe_the_babe");

        var playerTwoAuth = registrationService.register("wilks_booth", "lincoln$uck$", "ilovetheaters@usa.com");
        game = gameService.joinGame(playerTwoAuth.authToken(), game.gameID(), ChessGame.TeamColor.WHITE);
        assertEquals(game.whiteUsername(), "wilks_booth");

        var observerAuth = registrationService.register("shocked_observer", "tr@um@tized4life", "ihatetheaters@usa.com");
        game = gameService.joinGame(observerAuth.authToken(), game.gameID());
        assertTrue(game.observers().contains("shocked_observer"));
    }

    @Test
    void invalidJoinGame() throws DataAccessException{
        var playerOneAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        var game = gameService.createGame(playerOneAuth.authToken(), "Abe and the Baberahams");
        game = gameService.joinGame(playerOneAuth.authToken(), game.gameID(), ChessGame.TeamColor.BLACK);
        var gameID = game.gameID();

        var playerTwoAuth = registrationService.register("wilks_booth", "lincoln$uck$", "ilovetheaters@usa.com");
        assertThrows(DataAccessException.class, ()->gameService.joinGame(playerTwoAuth.authToken(), gameID, ChessGame.TeamColor.BLACK));
        assertThrows(DataAccessException.class, ()->gameService.joinGame(playerTwoAuth.authToken(), gameID+7, ChessGame.TeamColor.BLACK));
        gameService.joinGame(playerTwoAuth.authToken(), gameID, ChessGame.TeamColor.WHITE);

        var observerAuth = registrationService.register("shocked_observer", "tr@um@tized4life", "ihatetheaters@usa.com");
        assertThrows(DataAccessException.class, ()->gameService.joinGame(observerAuth.authToken(), gameID, ChessGame.TeamColor.WHITE));
    }
}