package learn.goodgames.domain;

import learn.goodgames.data.LocationRepository;
import learn.goodgames.models.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LocationServiceTest {

    @Autowired
    LocationService service;

    @MockBean
    LocationRepository repository;

    @Test
    void shouldFindAll() {
        List<Location> expected = List.of(makeLocation1(), makeLocation2());
        when(repository.findAll()).thenReturn(expected);
        List<Location> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById() {
        Location expected = makeLocation1();
        when(repository.findById(1)).thenReturn(expected);
        Location actual = repository.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAddWhenValid() {
        Location expected = makeLocation1();
        Location addThisLocation = makeLocation0();

        when(repository.add(addThisLocation)).thenReturn(expected);
        Result<Location> result = service.add(addThisLocation);
        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddWhenInvalid() {
        // Should not add null
        Result<Location> result = service.add(null);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if id > 0
        Location location = makeLocation1();
        result = service.add(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if name is null
        location = makeLocation0();
        location.setName(null);
        result = service.add(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if name is empty
        location = makeLocation0();
        location.setName("");
        result = service.add(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if address is null
        location = makeLocation0();
        location.setAddress(null);
        result = service.add(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if address is empty
        location = makeLocation0();
        location.setAddress("");
        result = service.add(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if city is null
        location = makeLocation0();
        location.setCity(null);
        result = service.add(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if city is empty
        location = makeLocation0();
        location.setCity("");
        result = service.add(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if state is null
        location = makeLocation0();
        location.setState(null);
        result = service.add(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if state is empty
        location = makeLocation0();
        location.setState("");
        result = service.add(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if state more than 2 characters
        location = makeLocation0();
        location.setState("FAIL");
        result = service.add(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if state less than 2 characters
        location = makeLocation0();
        location.setState("F");
        result = service.add(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if postalCode is null
        location = makeLocation0();
        location.setPostalCode(null);
        result = service.add(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if postalCode is empty
        location = makeLocation0();
        location.setState("");
        result = service.add(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not add if duplicate name, address, city, state, postalCode
        List<Location> expected = List.of(makeLocation1(), makeLocation2());
        location = makeLocation0();
        when(repository.findAll()).thenReturn(expected);
        result = service.add(location);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldUpdateWhenValid() {
        Location location = makeLocation1();
        location.setName("Update Test");

        when(repository.update(location)).thenReturn(true);

        Result<Location> result = service.update(location);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotUpdateWhenInvalid() {
        // Should not update null
        Result<Location> result = service.update(null);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if id > 0
        Location location = makeLocation1();
        result = service.update(location);
        assertEquals(ResultType.NOT_FOUND, result.getType());

        // Should not update if name is null
        location = makeLocation1();
        location.setName(null);
        result = service.update(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if name is empty
        location = makeLocation1();
        location.setName("");
        result = service.update(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if address is null
        location = makeLocation1();
        location.setAddress(null);
        result = service.update(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if address is empty
        location = makeLocation1();
        location.setAddress("");
        result = service.update(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if city is null
        location = makeLocation1();
        location.setCity(null);
        result = service.update(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if city is empty
        location = makeLocation1();
        location.setCity("");
        result = service.update(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if state is null
        location = makeLocation1();
        location.setState(null);
        result = service.update(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if state is empty
        location = makeLocation1();
        location.setState("");
        result = service.update(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if state more than 2 characters
        location = makeLocation1();
        location.setState("FAIL");
        result = service.update(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if state less than 2 characters
        location = makeLocation1();
        location.setState("F");
        result = service.update(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if postalCode is null
        location = makeLocation1();
        location.setPostalCode(null);
        result = service.update(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if postalCode is empty
        location = makeLocation1();
        location.setState("");
        result = service.update(location);
        assertEquals(ResultType.INVALID, result.getType());

        // Should not update if duplicate name, address, city, state, postalCode
        List<Location> expected = List.of(makeLocation1(), makeLocation2());
        location = makeLocation1();
        when(repository.findAll()).thenReturn(expected);
        result = service.update(location);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldDeleteWhenNotInUse() {
        when(repository.getUsageCount(3)).thenReturn(0);
        when(repository.deleteById(2)).thenReturn(true);
        Result<Location> result = service.deleteById(2);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDeleteWhenInUse() {
        when(repository.getUsageCount(1)).thenReturn(1);
        Result<Location> result = service.deleteById(1);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldNotDeleteWhenNotFound() {
        when(repository.deleteById(3)).thenReturn(false);
        Result<Location> result = service.deleteById(3);
        assertEquals(ResultType.NOT_FOUND, result.getType());
    }

    Location makeLocation0() {
        Location location = new Location();
        location.setLocationId(0);
        location.setName("Test Location");
        location.setAddress("123 Test Ave.");
        location.setCity("Test City");
        location.setState("RI");
        location.setPostalCode("02914");

        return location;
    }


    Location makeLocation1() {
        Location location = new Location();
        location.setLocationId(1);
        location.setName("Test Location");
        location.setAddress("123 Test Ave.");
        location.setCity("Test City");
        location.setState("RI");
        location.setPostalCode("02914");

        return location;
    }

    Location makeLocation2() {
        Location location = new Location();
        location.setLocationId(2);
        location.setName("Test Spot");
        location.setAddress("123 Test Street");
        location.setCity("Test Town");
        location.setState("MA");
        location.setPostalCode("01114");

        return location;
    }
}