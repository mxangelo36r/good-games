package learn.goodgames.data.mappers;

import learn.goodgames.models.Location;
import learn.goodgames.models.ReservationAttendee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationAttendeeMapper implements RowMapper<ReservationAttendee> {

    @Override
    public ReservationAttendee mapRow(ResultSet resultSet, int i) throws SQLException {
        ReservationAttendee ra = new ReservationAttendee();
        ra.setReservationId(resultSet.getInt("reservation_attendee_id"));
        ra.setReservationId(resultSet.getInt("reservation_id"));
        ra.setUserId(resultSet.getInt("user_id"));
        return ra;
    }
}