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

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTests {
    MemoryDataAccess data = new MemoryDataAccess();
    RegistrationService registrationService = new RegistrationService(data);
    AdminService adminService = new AdminService(data);

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
    void registerUser() throws DataAccessException {
        var userAuth = registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");

        assertNotSame("", userAuth.authToken());
        assertNotNull(userAuth.authToken());
    }

    @Test
    void registerExistingUser() throws DataAccessException {
        registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");

        assertThrows(DataAccessException.class, () -> {
            registrationService.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");
        });
    }


    }