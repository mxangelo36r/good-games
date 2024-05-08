package learn.goodgames.data.mappers;

import learn.goodgames.models.Reservation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationMapper implements RowMapper<Reservation> {

    @Override
    public Reservation mapRow(ResultSet resultSet, int i) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(resultSet.getInt("reservation_id"));
        reservation.setDate(resultSet.getDate("date").toLocalDate());
        reservation.setTime(resultSet.getTime("time").toLocalTime());
        reservation.setLocationId(resultSet.getInt("location_id"));
        reservation.setHostId(resultSet.getInt("host_id"));
        reservation.setHostName(resultSet.getString("host_name"));

        LocationMapper locationMapper = new LocationMapper();
        reservation.setLocation(locationMapper.mapRow(resultSet, i));

        return reservation;
    }
}
