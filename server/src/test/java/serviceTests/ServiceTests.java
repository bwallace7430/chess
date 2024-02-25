package serviceTests;

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

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTests {
    MemoryDataAccess data = new MemoryDataAccess();
    RegistrationService registrationService = new RegistrationService(data);
    AdminService adminService = new AdminService(data);
    SessionService sessionService = new SessionService(data);

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
    }