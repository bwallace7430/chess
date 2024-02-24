import dataaccess.MemoryDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.RegistrationService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {
    static final RegistrationService registrationService = new RegistrationService(new MemoryDataAccess());

    @BeforeEach
    void clear() throws ResponseException {
        service.deleteAllPets();
    }

    @Test
    void addPet() throws ResponseException {
        var pet = new Pet(0, "joe", PetType.FISH);
        pet = service.addPet(pet);

        var pets = service.listPets();
        assertEquals(1, pets.size());
        assertTrue(pets.contains(pet));
    }