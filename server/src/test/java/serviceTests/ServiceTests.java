package serviceTests;

import dataAccess.MemoryDataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.RegistrationService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTests {
    static final RegistrationService service = new RegistrationService(new MemoryDataAccess());

////    @BeforeEach
//    void clear() throws ResponseException {
//        service.deleteAllPets();
//    }

    @Test
    void registerUser() throws DataAccessException {
        var userAuth = service.register("abe_the_babe", "lincolnR0ck$", "alincoln@usa.com");

        assertFalse(userAuth.authToken()=="");
        assertFalse(userAuth.authToken()==null);
    }


    }