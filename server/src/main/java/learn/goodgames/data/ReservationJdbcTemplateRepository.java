package learn.goodgames.data;

import learn.goodgames.data.mappers.ReservationMapper;
import learn.goodgames.models.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationJdbcTemplateRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAllReservations() {
        final String sql = "select r.reservation_id, r.`date`, r.`time`, r.location_id, r.host_id, l.`name`, l.address, l.city, l.state, l.postal_code, u.`name` as host_name " +
                "from reservation r " +
                "inner join user u on u.user_id = r.host_id " +
                "inner join location l on l.location_id = r.location_id " +
                "where r.date >= curdate() " +
                "order by r.`date`;";
        return jdbcTemplate.query(sql, new ReservationMapper());
    }

    @Override
    public Reservation findReservationById(int reservationId) {
        final String sql = "select r.reservation_id, r.`date`, r.`time`, r.location_id, r.host_id, l.`name`, l.address, l.city, l.state, l.postal_code, u.`name` as host_name " +
                "from reservation r " +
                "inner join location l on l.location_id = r.location_id " +
                "inner join user u on u.user_id = r.host_id " +
                "where r.reservation_id = ? and r.`date` >= curdate();";

        return jdbcTemplate.query(sql, new ReservationMapper(), reservationId)
                .stream()
                .findFirst().orElse(null);
    }
}
