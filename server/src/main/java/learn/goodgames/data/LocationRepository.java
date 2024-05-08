package learn.goodgames.data;

import learn.goodgames.models.Location;

import java.util.List;

public interface LocationRepository {
    List<Location> findAllLocations();

    Location findLocationById(int locationId);

    Location addLocation(Location location);

    boolean updateLocation(Location location);

    boolean deleteLocationById(int locationId);

    int getUsageCount(int locationId);
}
