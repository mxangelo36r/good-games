package learn.goodgames.data;

import learn.goodgames.data.mappers.LocationMapper;
import learn.goodgames.models.Location;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class LocationJdbcTemplateRepository implements LocationRepository {

    private final JdbcTemplate jdbcTemplate;

    public LocationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Location> findAll() {
        final String sql = "select location_id, `name`, address, city, state, postal_code " + "from location limit 1000;";
        return jdbcTemplate.query(sql, new LocationMapper());
    }

    @Override
    public Location findById(int locationId) {
        final String sql = "select location_id, `name`, address, city, state, postal_code " + "from location " + "where location_id = ?;";

        return jdbcTemplate.query(sql, new LocationMapper(), locationId).stream().findFirst().orElse(null);
    }

    @Override
    public Location add(Location location) {
        final String sql = "insert into location (`name`, address, city, state, postal_code)" +
                "values (?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, location.getName());
            ps.setString(2, location.getAddress());
            ps.setString(3, location.getCity());
            ps.setString(4, location.getState());
            ps.setString(5, location.getPostalCode());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        location.setLocationId(keyHolder.getKey().intValue());
        return location;
    }

    @Override
    public boolean update(Location location) {
        final String sql = "update location set "
                + "`name` = ?, "
                + "address = ?, "
                + "city = ?, "
                + "state = ?, "
                + "postal_code = ? "
                + "where location_id = ?;";

        return jdbcTemplate.update(sql,
                location.getName(),
                location.getAddress(),
                location.getCity(),
                location.getState(),
                location.getPostalCode(),
                location.getLocationId()) > 0;
    }

    @Override
    public boolean deleteById(int locationId) {
        jdbcTemplate.update("delete reservation_attendee from reservation_attendee " +
                "inner join reservation r on r.reservation_id = reservation_attendee.reservation_id " +
                "inner join location l on l.location_id = r.reservation_id " +
                "where l.location_id = ?;", locationId);
        jdbcTemplate.update("delete from reservation where location_id = ?;", locationId);
        return jdbcTemplate.update("delete from location where location_id = ?;", locationId) > 0;
    }
}