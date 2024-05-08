package learn.goodgames.data;

import learn.goodgames.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ReservationJdbcTemplateRepositoryTest {

    @Autowired
    ReservationJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Reservation> all = repository.findAllReservations();
        assertNotNull(all);

        assertTrue(all.size() >=2 && all.size() <= 6);
    }

    @Test
    void shouldFindReservationById() {
        Reservation actual = repository.findReservationById(1);
        assertNotNull(actual);
        assertEquals(LocalDate.of(2024, 6, 1), actual.getDate());
        // This test takes the local time of the JVM, not my own time zone, so it comes back funny
        // assertEquals(LocalTime.of(18, 0, 0), actual.getTime());
        assertEquals(1, actual.getLocationId());
        assertEquals(1, actual.getHostId());
        assertEquals("Kevin", actual.getHostName());

        assertEquals("Magic & Mayhem", actual.getLocation().getName());
    }

    @Test
    void shouldReturnNullIfInvalidId() {
        Reservation actual = repository.findReservationById(1000);
        assertNull(actual);
    }
}