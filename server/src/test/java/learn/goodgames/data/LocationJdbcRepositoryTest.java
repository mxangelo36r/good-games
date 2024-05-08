package learn.goodgames.data;

import learn.goodgames.models.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LocationJdbcRepositoryTest {
    final static int NEXT_LOCATION_ID = 5;

    @Autowired
    LocationJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Location> all = repository.findAllLocations();
        assertNotNull(all);
        assert(all.size() > 3 && all.size() < 6);

        Location actual = all.get(0);
        assertEquals(1, actual.getLocationId());
        assertEquals("Magic & Mayhem", actual.getName());
        assertEquals("100 Main Place", actual.getAddress());
        assertEquals("Yonkers", actual.getCity());
        assertEquals("NY", actual.getState());
        assertEquals("00000", actual.getPostalCode());
    }

    @Test
    void shouldFindMagicAndMayhem() {
        Location actual = repository.findLocationById(1);
        assertNotNull(actual);
        assertEquals(1, actual.getLocationId());
        assertEquals("Magic & Mayhem", actual.getName());
        assertEquals("100 Main Place", actual.getAddress());
        assertEquals("Yonkers", actual.getCity());
        assertEquals("NY", actual.getState());
        assertEquals("00000", actual.getPostalCode());
    }

    @Test
    void shouldAddLocation() {
        Location location = makeLocation();
        Location actual = repository.addLocation(location);
        assertNotNull(actual);
        assertEquals(NEXT_LOCATION_ID, actual.getLocationId());
    }

    @Test
    void shouldUpdateLocation() {
        Location location = makeLocation();
        location.setLocationId(2);
        assertTrue(repository.updateLocation(location));
        Location actual = repository.findLocationById(2);
        assertEquals("Test Location", actual.getName());
        assertEquals("123 Test Ave.", actual.getAddress());
        assertEquals("Test City", actual.getCity());
        assertEquals("RI", actual.getState());
        assertEquals("02914", actual.getPostalCode());

        location.setLocationId(1988);
        assertFalse(repository.updateLocation(location));
    }

    @Test
    void shouldDeleteById() {
        assertTrue(repository.deleteLocationById(1));
        assertFalse(repository.deleteLocationById(1));
    }

    @Test
    void shouldGetUsageCount() {
        // Can't use locationId1 because it gets deleted and changed around
        assertEquals(1, repository.getLocationUsageCount(3));
        assertEquals(1, repository.getLocationUsageCount(2));
    }


    Location makeLocation() {
        Location location = new Location();
        location.setName("Test Location");
        location.setAddress("123 Test Ave.");
        location.setCity("Test City");
        location.setState("RI");
        location.setPostalCode("02914");

        return location;
    }
}