package learn.goodgames.data.mappers;

import learn.goodgames.models.Location;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class LocationMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet resultSet, int i) throws SQLException {
        Location location = new Location();
        location.setLocationId(resultSet.getInt("location_id"));
        location.setName(resultSet.getString("name"));
        location.setAddress(resultSet.getString("address"));
        location.setCity(resultSet.getString("city"));
        location.setState((resultSet.getString("state")));
        location.setPostalCode(resultSet.getString("postal_code"));
        return location;
    }
}