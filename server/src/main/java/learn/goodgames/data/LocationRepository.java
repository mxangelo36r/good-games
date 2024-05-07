package learn.goodgames.data;

import learn.goodgames.models.Location;

import java.util.List;

public interface LocationRepository {
    List<Location> findAll();

    Location findById(int locationId);

    Location add(Location location);

    boolean update(Location location);

    boolean deleteById(int locationId);
}
